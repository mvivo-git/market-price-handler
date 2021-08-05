package fx.pricefeed.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MarginServiceTest {
    private MarginService uut;

    @Before
    public void setUp() {
        uut = new MarginService(10.0, 20.0);
    }

    @Test
    public void getBidWithMargin() {
        // Bid 100, margin -10%, expect bid with margin = 90
        String expected = "90.0000";
        String actual = uut.getBidWithMargin("100.0");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getAskWithMargin() {
        // Ask 100, margin +20%, expect ask with margin = 120
        String expected = "120.0000";
        String actual = uut.getAskWithMargin("100.0");

        assertThat(actual, equalTo(expected));
    }

}