package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class TradingAccount extends GeneralAccount {
    public TradingAccount(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public void yearlyUpdate() throws Exception {
        double d = getBalance() * mScenario.mInterestIncomeRate;
        deposit(d);

        mChange += d;

        // if we are sitting on the stock there is no gain, if sold
        // there is, we tax all liquidations...so call this zero
        mYearsTaxableGain = 0.0;
    }
}
