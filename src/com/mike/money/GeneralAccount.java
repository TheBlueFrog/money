package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class GeneralAccount extends Account{

    public GeneralAccount(Scenario s, String name, Scenario.People owner, double balance) {
        super(s, name, owner, balance);
    }

    @Override
    public void yearlyUpdate() throws Exception {
        double d = getBalance() * 0.002; // nothing
        deposit(d);
        mChange += d;
    }

    @Override
    public double depositMRD(Account general, Scenario.People who, int age) throws Exception {
        // doesn't apply
        return 0;
    }

}
