package fx.pricefeed.dao;

import fx.pricefeed.model.Price;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


/**
 * Simple in-memory database singleton to simulate some sort of storage within the tests.
 */
public enum InMemoryDb implements PriceDAO {
    SINGLETON;

    /* instrument string in REST-friendly formatting eg. "GBPUSD" mapped to current Price */
    private Map<String, Price> currentPrices;

    InMemoryDb() {
        currentPrices = new ConcurrentHashMap<>();
    }

    @Override
    public void addOrUpdatePrice(String instrument, Price price) {
        currentPrices.put(instrument, price);
    }

    @Override
    public Optional<Price> getPrice(String instrument) {
        return Optional.ofNullable(currentPrices.get(instrument));
    }

    @Override
    public Stream<Price> getAllPrices() {
        return currentPrices.values().stream();
    }
}
