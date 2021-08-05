package fx.pricefeed.model;

public class Price {
    private String id;
    private String instrumentName;
    private String bid;
    private String bidWithMargin;
    private String ask;
    private String askWithMargin;
    private String timestamp;

    public Price(String id, String instrumentName, String bid, String ask, String timestamp) {
        this.id = id;
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBidWithMargin() {
        return bidWithMargin;
    }

    public void setBidWithMargin(String bidWithMargin) {
        this.bidWithMargin = bidWithMargin;
    }

    public String getAskWithMargin() {
        return askWithMargin;
    }

    public void setAskWithMargin(String askWithMargin) {
        this.askWithMargin = askWithMargin;
    }
}
