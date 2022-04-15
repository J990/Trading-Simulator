public class DestinationError extends RuntimeException {
    public DestinationError(String dest) {
        super("'" + dest + "' is not a valid panel destination");
    }
}
