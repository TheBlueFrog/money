package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class GeneralAccount extends Account{

    public GeneralAccount(Scenario s, String name, Scenario.People owner, double balance) {
        super(s, name, owner, balance);
    }

    @Override
    public void depositInvestmentGain() throws Exception {
        double d = getBalance() * 0.002; // nothing
        deposit(d);
    }

    @Override
    public void depositMRD(Account general, int age) throws Exception {
        // doesn't apply
    }

}
