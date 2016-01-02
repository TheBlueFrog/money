package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class InheritedTraditionalIRA extends TraditionalIRA {
    protected InheritedTraditionalIRA(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public double getLifeExpectancy(int age) throws Exception {
        return MRDTable.getIRAMRDLifeExpectancy(age < 70 ? 70 : age);
    }

}
