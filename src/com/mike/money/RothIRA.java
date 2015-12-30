package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class RothIRA extends IRA {
    protected RothIRA(Scenario s, String name, Scenario.People owner, double balance) {
        super(s, name, owner, balance);
    }

    @Override
    public double depositMRD(Account general, Scenario.People who, int age) throws Exception {
        // no MRD
        return 0;
    }
}
