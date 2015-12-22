package com.mike.money;

/**
 * Created by mike on 12/22/2015.
 */
public class Account {

    static public enum AccountType { General, InheritedTraditionalIRA, TraditionalIRA, RothIRA };

    private double mBalance;    // current balance in the account

    private AccountType mType;

    private Scenario.People mOwner;

    public Account(AccountType type, Scenario.People owner, double balance) {
        mBalance = balance;
        mType = type;
        mOwner = owner;
    }

    public double getBalance() {
        return mBalance;
    }
    public AccountType getType () {
        return mType;
    }
    public Scenario.People getOwner() {
        return mOwner;
    }

    public boolean matches(AccountType type, Scenario.People owner) {
        return mType.equals(type) && mOwner.equals(owner);
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

    /**
     * should only be used during initialization time...
     * @param amount
     */
    public void setBalance(double amount) {
        mBalance = amount;
    }

}