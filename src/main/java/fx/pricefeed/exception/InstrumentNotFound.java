package fx.pricefeed.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InstrumentNotFound extends RuntimeException {
    private final String instrumentName;

    public InstrumentNotFound(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentName() {
        return instrumentName;
    }
}
