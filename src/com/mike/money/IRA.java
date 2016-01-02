package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
abstract public class IRA extends Account {

    protected IRA(Scenario s, String name, Scenario.People owner, double balance) {
        super(s, name, owner, balance);
    }

    @Override
    public void yearlyUpdate() throws Exception {
        double d = getBalance() * mScenario.mInterestIncomeRate;
        deposit(d);
        mChange += d;
    }

}
