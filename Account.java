/*
** Defines a user's monetary balance
** and the portfolios that they have
*/
public class Account
{
    private float balance;
    private final int MAX_PORTFOLIOS = 3;
    private Portfolio[] portfolios = new Portfolio[MAX_PORTFOLIOS];
    private int portfolioCount;
    
    public Account()
    {
        balance = 0.0f;
        portfolioCount = 0;
    }

    public void addPortfolio(Portfolio p)
    {
        if (portfolioCount < MAX_PORTFOLIOS)
            portfolios[portfolioCount++] = p;
    }

    public Portfolio[] getPortfolios()
    {
        return portfolios;
    }

    public float getRawBalance() {return balance;}  // Returns the balance
    public String getConvertedBalance() {return CurrencyConverter.convert(balance);}  // Formats balance to currency

    public void deposit(float amount)
    {
        balance += amount;
    }

    /*
    ** Deducts an amount from the balance
    ** Sets balance to 0 if amount is greater than the balance
    */
    public void withdraw(float amount)
    {
        if (amount <= this.balance) {this.balance -= amount;}  // Withdrawal succeeded
        else {this.balance = 0;}  // Withdrawal greater than balance
    }

}
