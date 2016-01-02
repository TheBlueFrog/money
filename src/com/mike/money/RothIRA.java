package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class RothIRA extends IRA {
    protected RothIRA(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public double depositMRD(Account general, int age) throws Exception {
        // no MRD
        return 0;
    }
}
