/*
** Defines the actions a user can have
** on their monetary balance
** and their portfolio
*/
public class Account
{
    private Bank bankAccount;
    private Portfolio portfolio;
    private Trade lastTrade;
    
    public Account()
    {
        bankAccount = new Bank();
        portfolio = new Portfolio();
        lastTrade = new Trade(new Asset("default", "DEFAULT", 0, Asset.STOCK), 0, 0);
    }

    public double getRawBalance() {  // Returns the balance
        return bankAccount.getBalance();
    }

    public String getConvertedBalance() {  // Formats balance to currency
        return Converter.convert(bankAccount.getBalance());
    }

    // Deposits money into bank
    // True = success. False = deposit is too small
    public boolean addMoney(double amount)
    {
        try {
            bankAccount.deposit(amount);
            return true;
        } catch (SmallDepositException err) {
            return false;
        }
    }

    // Withdraws money from bank
    // True = success. False = balance is smaller than withdrawal requested
    public void removeMoney(double amount) throws SmallBalanceException
    {
        try {
            bankAccount.withdraw(amount);
        } catch (SmallBalanceException err) {
            throw err;
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public boolean openTrade(Asset a, double price) throws SmallBalanceException
    {
        if (price < 0 || price > 10000000) return false;
        double units = price / a.getRawUnitValue();
        try {
            removeMoney(price);
            lastTrade = a.createTrade(units);
            portfolio.addTrade(lastTrade);
            return true;
        } catch (SmallBalanceException err) {
            throw err;
        }
    }

    public double getRecentTradeUnits()
    {
        return lastTrade.getUnits();
    }

    public void closeTrade(Trade t) throws UnknownTradeException
    {
        try {
            portfolio.removeTrade(t);
            addMoney(t.currentValue());
        } catch (UnknownTradeException err) {
            throw err;
        }
    }

}
