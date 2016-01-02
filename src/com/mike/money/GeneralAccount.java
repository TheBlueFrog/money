package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class GeneralAccount extends Account{

    public GeneralAccount(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public void yearlyUpdate() throws Exception {
        double d = getBalance() * 0.002; // nothing
        deposit(d);
        mChange += d;
    }

    @Override
    public double depositMRD(Account general, int age) throws Exception {
        // doesn't apply
        return 0;
    }

}
