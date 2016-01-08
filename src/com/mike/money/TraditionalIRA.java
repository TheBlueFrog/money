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

        double mrd = 0.0;

        {
            double lifeExp = getLifeExpectancy(age);
            if (lifeExp > 0)
                mrd = getBalance() / lifeExp;
        }

        if (mrd > getBalance())
            mrd = getBalance();

        withdraw(mrd);
        general.deposit(mrd);

        return mrd;
    }

    protected double getLifeExpectancy(int age) throws Exception {
        if (age >= 70)
            return MRDTable.getIRAMRDLifeExpectancy(age);

        return -1.0;
    }
}
