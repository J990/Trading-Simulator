public class AssetCategoryException extends RuntimeException {
    public AssetCategoryException(Asset expected, Asset received) {
        super("Incorrect asset category.\nExpected: " + expected.toString() + "\nReceived: " + received.toString());
    }

    public AssetCategoryException(String invalidCategory) {
        super("'" + invalidCategory + "' is an invalid asset category.");
    }
}
