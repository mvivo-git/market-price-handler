package fx.pricefeed.service;

import fx.pricefeed.exception.ParseException;
import fx.pricefeed.model.Price;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class PriceFeedParserServiceTest {
    private PriceFeedParserService uut;

    @Before
    public void setUp() {
        uut = new PriceFeedParserService();
    }

    @Test
    public void priceObjectCorrectlyPopulated() throws ParseException {
        String givenMessage = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";

        Price actualPrice = uut.parse(givenMessage);

        assertThat(actualPrice.getAsk(), equalTo("1.2000"));
        assertThat(actualPrice.getBid(), equalTo("1.1000"));
        assertThat(actualPrice.getTimestamp(), equalTo("01-06-2020 12:01:01:001"));
        assertThat(actualPrice.getId(), equalTo("106"));
        assertThat(actualPrice.getInstrumentName(), equalTo("EUR/USD"));
    }

    @Test
    public void corruptedInputThrowsException() {
        String givenMessage = "106##EUR/USD##1.1000##1.2000##01-06-2020 12:01:01:001";

        try {
            uut.parse(givenMessage);
            fail("Should have thrown ParseException");
        } catch (ParseException e) {
            assertThat(e.getOriginalMessage(), equalTo(givenMessage));
        }
    }
}