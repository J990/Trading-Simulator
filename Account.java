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
        return CurrencyConverter.convert(bankAccount.getBalance());
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
    public boolean removeMoney(double amount)
    {
        try {
            bankAccount.withdraw(amount);
            return true;
        } catch (SmallWithdrawalException err) {
            System.err.println(err);
            return false;
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void buyAsset(Asset a) {}
    public void sellAsset(Asset a) {}

}
