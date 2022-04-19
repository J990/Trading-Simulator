import java.awt.*;
import java.awt.event.*;

import javax.swing.JComboBox;

public class AssetFilter extends JComboBox<String>
{
    private static final String all = "All";
    private static final String commodities = "Commodities";
    private static final String currencies = "Currencies";
    private static final String cryptocurrencies = "Cryptocurrencies";
    private static final String stocks = "Stocks";
    private static final String[] filterOptions = { all, commodities, currencies, cryptocurrencies, stocks };

    private Label assetCategory;

    public AssetFilter(Label l)
    {
        super(filterOptions);
        assetCategory = l;
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        switch ((String)getSelectedItem()) {
            case all:
                TradingApp.shownAssets = TradingApp.assets;
                assetCategory.setText("All Assets"); break;
            case commodities:
                TradingApp.shownAssets = TradingApp.assets.getAssetsOfCategory(Asset.COMMODITY);
                assetCategory.setText("Commodities"); break;
            case currencies:
                TradingApp.shownAssets = TradingApp.assets.getAssetsOfCategory(Asset.CURRENCY);
                assetCategory.setText("Currencies"); break;
            case cryptocurrencies:
                TradingApp.shownAssets = TradingApp.assets.getAssetsOfCategory(Asset.CRYPTOCURRENCY);
                assetCategory.setText("Cryptocurrencies"); break;
            case stocks:
                TradingApp.shownAssets = TradingApp.assets.getAssetsOfCategory(Asset.STOCK);
                assetCategory.setText("Stocks"); break;
            default: break;
        }
        TradingApp.showMarket();  // Refresh screen
    }
}
