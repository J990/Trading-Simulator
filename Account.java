/*
** Defines the actions a user can have
** on their monetary balance
** and their portfolio
*/
public class Account
{
    private Bank bankAccount;
    private Portfolio portfolio;
    
    public Account()
    {
        bankAccount = new Bank();
        portfolio = new Portfolio();
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

    public void openTrade(Asset a, double price) throws SmallBalanceException
    {
        double units = price / a.getRawUnitValue();
        try {
            removeMoney(price);
            portfolio.addTrade(a.createTrade(units));
        } catch (SmallBalanceException err) {
            throw err;
        }
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
