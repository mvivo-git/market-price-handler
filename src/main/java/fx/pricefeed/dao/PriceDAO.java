package fx.pricefeed.dao;

import fx.pricefeed.model.Price;

import java.util.Optional;
import java.util.stream.Stream;

public interface PriceDAO {
    void addOrUpdatePrice(String instrument, Price price) ;
    Optional<Price> getPrice(String instrument);
    Stream<Price> getAllPrices();
}
