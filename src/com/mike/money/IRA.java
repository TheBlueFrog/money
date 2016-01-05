package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
abstract public class IRA extends Account {

    protected IRA(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public void yearlyUpdate() throws Exception {
        double d = getBalance() * mScenario.mInvestmentIncomeRate;
        deposit(d);
        mChange += d;
    }

}
