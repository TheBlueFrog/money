package com.mike.money;

/**
 * Created by mike on 12/22/2015.
 */
public abstract class Account {

    private final String mName;
    protected final Scenario mScenario;

    private int mID;
    private static int mNextID = 1;

    protected double mChange = 0.0;
    private double mBalance;    // current balance in the account

    protected Account(Scenario s, String name, double balance) {

        mID = mNextID++;
        mScenario = s;

        mName = name;
        mBalance = balance;
    }

    public int getID () {
        return mID;
    }
    public String getName () {
        return mName;
    }
    public double getBalance() {
        return mBalance;
    }

    public void endOfYearReset () {
        mChange = 0.0;
    }
    public void withdraw (double amount) throws Exception {
        if (amount < 0.0)
            throw new Exception ("Cannot withdraw negative amounts");

        if (amount > mBalance)
            throw new Exception ("Insuffient funds for withdrawl");

        mChange -= amount;
        mBalance -= amount;
    }
    public void deposit (double amount) throws Exception {
        if (amount < 0.0)
            throw new Exception("Cannot deposit negative amounts");

        mChange += amount;
        mBalance += amount;
    }

    // some accts gain is taxable some not, that will be computed by
    // the updateInvestmentGain method
    public double mYearsTaxableGain = 0.0;
    abstract public void yearlyUpdate() throws Exception;

    // MRDs are taxable
    abstract public double depositMRD(Account general, int age) throws Exception;

}
