package fx.pricefeed.service;

import fx.pricefeed.exception.ParseException;
import fx.pricefeed.model.Price;

public class PriceFeedParserService {

    public Price parse(String csvMessage) throws ParseException {
        try {
            String[] messageParts = csvMessage.split(",");
            String id = messageParts[MessageStructure.ID.getIndex()].trim();
            String instrument = messageParts[MessageStructure.INSTRUMENT.getIndex()].trim().toUpperCase();
            String bid = messageParts[MessageStructure.BID.getIndex()].trim();
            String ask = messageParts[MessageStructure.ASK.getIndex()].trim();
            String timestamp = messageParts[MessageStructure.TIMESTAMP.getIndex()].trim();

            return new Price(id, instrument, bid, ask, timestamp);
        } catch (Exception e) {
            throw new ParseException("Error parsing inbound message", csvMessage, e);
        }
    }

    private enum MessageStructure {
        ID(0),
        INSTRUMENT(1),
        BID(2),
        ASK(3),
        TIMESTAMP(4);

        private final int index;

        MessageStructure(int index){
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}
