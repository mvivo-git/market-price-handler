package fx.pricefeed.listener;

import fx.pricefeed.dao.PriceDAO;
import fx.pricefeed.exception.ParseException;
import fx.pricefeed.messaging.SomeMessagingSystem;
import fx.pricefeed.model.Price;
import fx.pricefeed.service.MarginService;
import fx.pricefeed.service.PriceFeedParserService;


public class PriceFeedListener implements SomeMessagingSystem {
    private final PriceDAO priceDAO;
    private final PriceFeedParserService priceFeedParser;
    private final MarginService marginService;

    public PriceFeedListener(PriceDAO priceDAO, PriceFeedParserService priceFeedParserService, MarginService marginService) {
        this.priceDAO = priceDAO;
        this.priceFeedParser    = priceFeedParserService;
        this.marginService      = marginService;
    }

    @Override
    public void onMessage(String message) {
        try {
            Price price = priceFeedParser.parse(message);
            enrichPriceWithMargins(price);
            priceDAO.addOrUpdatePrice(getRestFriendlyInstrumentName(price.getInstrumentName()), price);
        } catch (ParseException e) {
            handleError(e);
        }
    }

    private String getRestFriendlyInstrumentName(String instrumentName) {
        // We don't want slashes on something that will form part of the REST URI
        return instrumentName.replaceAll("/","");
    }

    private void enrichPriceWithMargins(Price price) {
        price.setBidWithMargin(marginService.getBidWithMargin(price.getBid()));
        price.setAskWithMargin(marginService.getAskWithMargin(price.getAsk()));
    }

    private void handleError(ParseException e) {
        // Will leave error handling out of this exercise, but this could be passed on to some Error handler service...
        e.printStackTrace();
    }
}
