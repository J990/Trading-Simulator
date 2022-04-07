public class AssetTypeException extends RuntimeException {
    public AssetTypeException(Asset expected, Asset received) {
        super("Incorrect asset type.\nExpected: " + expected.toString() + "\nReceived: " + received.toString());
    }

    public AssetTypeException(String invalidType) {
        super("'" + invalidType + "' is an invalid asset type.");
    }
}
