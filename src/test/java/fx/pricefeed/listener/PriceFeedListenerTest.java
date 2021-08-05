package fx.pricefeed.listener;

import fx.pricefeed.dao.InMemoryDb;
import fx.pricefeed.model.Price;
import fx.pricefeed.service.MarginService;
import fx.pricefeed.service.PriceFeedParserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PriceFeedListenerTest {

    private PriceFeedListener uut;

    @Mock
    private MarginService marginServiceMock;

    @Before
    public void setUp() {
        given(marginServiceMock.getBidWithMargin(anyString())).willReturn("BidWithMargin");
        given(marginServiceMock.getAskWithMargin(anyString())).willReturn("AskWithMargin");
        uut = new PriceFeedListener(InMemoryDb.SINGLETON, new PriceFeedParserService(), marginServiceMock);
    }

    @Test
    public void onMessage_PriceAddedToDb() {
        uut.onMessage("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
        uut.onMessage("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        uut.onMessage("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");
        uut.onMessage("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100");
        uut.onMessage("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");

        // Since there are 2 duplicate fx pairs, total amount of entries in DB should be 3
        assertThat(InMemoryDb.SINGLETON.getAllPrices().count(), is(3L));

        Price latestEURJPY = InMemoryDb.SINGLETON.getPrice("EURJPY").get();
        assertThat(latestEURJPY.getId(), equalTo("110"));
        assertThat(latestEURJPY.getInstrumentName(), equalTo("EUR/JPY"));
        assertThat(latestEURJPY.getBid(), equalTo("119.61"));
        assertThat(latestEURJPY.getAsk(), equalTo("119.91"));
        assertThat(latestEURJPY.getTimestamp(), equalTo("01-06-2020 12:01:02:110"));
        assertThat(latestEURJPY.getBidWithMargin(), equalTo("BidWithMargin"));
        assertThat(latestEURJPY.getAskWithMargin(), equalTo("AskWithMargin"));
    }
}