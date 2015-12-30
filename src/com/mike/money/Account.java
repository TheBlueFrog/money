package com.mike.money;

/**
 * Created by mike on 12/22/2015.
 */
public abstract class Account {

    private final String mName;
    protected final Scenario mScenario;

    private int mID;
    private static int mNextID = 1;

    private double mBalance;    // current balance in the account

    protected Scenario.People mOwner;

    protected Account(Scenario s, String name, Scenario.People owner, double balance) {

        mID = mNextID++;
        mScenario = s;

        mName = name;
        mBalance = balance;
        mOwner = owner;
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
    public Scenario.People getOwner() {
        return mOwner;
    }

    public void withdraw (double amount) throws Exception {
        if (amount < 0.0)
            throw new Exception ("Cannot withdraw negative amounts");

        if (amount > mBalance)
            throw new Exception ("Insuffient funds for withdrawl");

        mBalance -= amount;
    }
    public void deposit (double amount) throws Exception {
        if (amount < 0.0)
            throw new Exception("Cannot deposit negative amounts");

        mBalance += amount;
    }

    abstract public void depositInvestmentGain() throws Exception;

    abstract public double depositMRD(Account general, Scenario.People who, int age) throws Exception;

}
