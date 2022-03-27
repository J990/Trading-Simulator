public class AssetTypeException extends Exception {
    public AssetTypeException(Asset expected, Asset received) {
        super("Incorrect asset type.\nExpected: " + expected.toString() + "\nReceived: " + received.toString());
    }
}
