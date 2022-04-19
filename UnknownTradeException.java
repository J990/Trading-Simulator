public class UnknownTradeException extends Exception {
    public UnknownTradeException(Trade t) {
        super("No trade with the ID '" + t.getID() + "' found.");
    }
}
