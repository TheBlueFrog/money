package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class InheritedTraditionalIRA extends TraditionalIRA {
    protected InheritedTraditionalIRA(Scenario s, String name, Scenario.People owner, double balance) {
        super(s, name, owner, balance);
    }

    @Override
    public double getLifeExpectancy(int age) throws Exception {
        return MRDTable.getIRAMRDLifeExpectancy(age < 70 ? 70 : age);
    }

}
