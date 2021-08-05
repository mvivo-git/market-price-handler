package fx.pricefeed.rest;


import fx.pricefeed.dao.InMemoryDb;
import fx.pricefeed.exception.InstrumentNotFound;
import fx.pricefeed.listener.PriceFeedListener;
import fx.pricefeed.messaging.SomeMessagingSystem;
import fx.pricefeed.model.Price;
import fx.pricefeed.service.MarginService;
import fx.pricefeed.service.PriceFeedParserService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class PriceCheckControllerIntegrationTest {

    private PriceCheckController restController;
    private SomeMessagingSystem feedLoader;

    @Before
    public void setUp() {
        PriceFeedParserService priceFeedParser = new PriceFeedParserService();
        MarginService marginService = new MarginService(0.1, 0.1);
        feedLoader = new PriceFeedListener(InMemoryDb.SINGLETON, priceFeedParser, marginService);

        restController = new PriceCheckController(InMemoryDb.SINGLETON);
    }

    @Test
    public void userRequestsLatestEURJPY() {
        // first load up the test database
        feedLoader.onMessage("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
        feedLoader.onMessage("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        feedLoader.onMessage("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");
        feedLoader.onMessage("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100");
        feedLoader.onMessage("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");

        // now query the Rest endpoint
        Price latestEURJPYPrice = restController.getLatestPrice("EURJPY");

        // expected bid with margin = 119.61 - 0.1% = 119.4903
        String expectedBidWithMargin = "119.4903";

        // expected ask with margin = 119.91 + 0.1% = 120.0300
        String expectedAskWithMargin = "120.0300";

        assertThat(latestEURJPYPrice.getId(), equalTo("110"));
        assertThat(latestEURJPYPrice.getInstrumentName(), equalTo("EUR/JPY"));
        assertThat(latestEURJPYPrice.getBid(), equalTo("119.61"));
        assertThat(latestEURJPYPrice.getAsk(), equalTo("119.91"));
        assertThat(latestEURJPYPrice.getTimestamp(), equalTo("01-06-2020 12:01:02:110"));
        assertThat(latestEURJPYPrice.getBidWithMargin(), equalTo(expectedBidWithMargin));
        assertThat(latestEURJPYPrice.getAskWithMargin(), equalTo(expectedAskWithMargin));
    }

    @Test
    public void userRequestsNonExistingCurrencyPair() {
        try {
            restController.getLatestPrice("EUREUR");
            fail("Should have thrown Exception");
        } catch (InstrumentNotFound inf) {
            assertThat(inf.getInstrumentName(), equalTo("EUREUR"));
        }
    }
}