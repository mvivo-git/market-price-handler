package fx.pricefeed.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Given a configurable bidMargin / askMargin %
 *
 * This class is responsible for calculating the bid/ask price on the provided original price.
 */
public class MarginService {
    private final double bidMargin, askMargin;

    public MarginService(double bidMargin, double askMargin) {
        this.bidMargin = bidMargin;
        this.askMargin = askMargin;
    }

    public String getBidWithMargin(String originalBid) {
        BigDecimal original = new BigDecimal(originalBid);
        BigDecimal margin = new Percent(bidMargin).of(original);
        return original.subtract(margin).toString();
    }

    public String getAskWithMargin(String originalAsk) {
        BigDecimal original = new BigDecimal(originalAsk);
        BigDecimal margin = new Percent(askMargin).of(original);
        return original.add(margin).toString();
    }

    static class Percent {
        private final BigDecimal ONE_HUNDRED = new BigDecimal(100);
        private double percent;

        Percent(double percent) {
            this.percent = percent;
        }

        BigDecimal of(BigDecimal original) {
            return original.multiply(new BigDecimal(percent)).divide(ONE_HUNDRED,4, RoundingMode.CEILING);
        }
    }
}
