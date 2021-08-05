package fx.pricefeed.rest;

import fx.pricefeed.dao.PriceDAO;
import fx.pricefeed.exception.InstrumentNotFound;
import fx.pricefeed.model.Price;

//@RestController
public class PriceCheckController {
    private final PriceDAO priceDAO;

    public PriceCheckController(PriceDAO priceDAO) {
        this.priceDAO = priceDAO;
    }

    //@GetMapping ("/fxPrices/{instrumentName}")
    public Price getLatestPrice(/*@PathVariable*/String instrumentName) {
        return priceDAO.getPrice(instrumentName)
                .orElseThrow(()-> new InstrumentNotFound(instrumentName));
    }
}
