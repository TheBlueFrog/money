package com.mike.money;

/**
 * Created by mike on 12/26/2015.
 */
public class TraditionalIRA extends IRA {
    protected TraditionalIRA(Scenario s, String name, double balance) {
        super(s, name, balance);
    }

    @Override
    public double depositMRD(Account general, int age) throws Exception {

        double mrd = MRDTable.getMRD(age, this);

        if (mrd > getBalance())
            mrd = getBalance();

        withdraw(mrd);
        general.deposit(mrd);

        return mrd;
    }

    public double getLifeExpectancy(int age) throws Exception {
        if (age >= 70)
            return MRDTable.getIRAMRDLifeExpectancy(age);

        return -1.0;
    }
}
