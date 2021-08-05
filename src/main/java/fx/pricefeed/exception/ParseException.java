package fx.pricefeed.exception;

public class ParseException extends Exception {
    private final String originalMessage;

    public ParseException(String message, String originalMessage, Throwable cause) {
        super(message, cause);
        this.originalMessage = originalMessage;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }
}
