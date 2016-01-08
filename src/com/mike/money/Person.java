package com.mike.money;

import javafx.beans.binding.Bindings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mike on 1/2/2016.
 */
public class Person {
    private String mName;
    private int mBirthYear;
    private int mRetireYear;
    private int mDeathYear;
    private List<Account> mAccounts;

    Map<Integer, Double> mSalary = new HashMap<Integer, Double>();

    public Person(String name, int birthYear, List<Account> accounts) {
        mName = name;
        mBirthYear = birthYear;
        mAccounts = accounts;

        // these should be set by the SSA scenario
        mRetireYear = mBirthYear + 65;
        mDeathYear = mBirthYear + 1000; // close to forever

        if (name.equals("Noga")) {
            // these may not all be used but put down a scenario,
            // the Scenario controls the simulation years and
            // the SSA data control retirement
            for (int i = 2015; i < mBirthYear + 70; ++i)
                mSalary.put(i, 100000.0 + ((i - 2015) * 1000.0));
        }
    }

    public double getTaxableIncome() {
        double d = 0.0;
        for (Account a : mAccounts)
            d += a.mYearsTaxableGain;
        return d;
    }

    public String getName() {
        return mName;
    }

    public void addToIRA(double amount) throws Exception {
        for(Account a : mAccounts) {
            if (a instanceof TraditionalIRA) {
                    a.deposit(amount);
                }
            }
    }

    public void addToStock(double amount) throws Exception {
        for(Account a : mAccounts) {
            if (a.getName().startsWith("Intel stock")) {
                    a.deposit(amount);
                }
            }
    }

    public double depositeWorkIncome(Simulation simulation, Account general) throws Exception {
        double d = 0.0;
        if (simulation.mCurrentYear <= mRetireYear) {
            // base salary
            d = mSalary.get(simulation.mCurrentYear);

            // fringies
            addToIRA(0.04 * d);
            addToStock(0.04 * d);
    }

        general.deposit(d);
        return d;
    }

    public void updateAccounts() throws Exception {
        for (Account a : mAccounts) {
            a.mYearsTaxableGain = 0.0;
            a.yearlyUpdate();
        }
    }

    public double getAssets() {
        double d = 0.0;
        for (Account a : mAccounts) {
            d += a.getBalance();
        }

        return d;
    }

    public int getAge(int year) {
        return year - mBirthYear;
    }

    public double depositMRD(Account general, int year) throws Exception {
        int age = getAge(year);
        double d = 0.0;
        for (Account a : mAccounts)
            d += a.depositMRD(general, age);

        return d;
    }

    public double liquidate(Account general, double amount) throws Exception {
        double withdrawn = 0.0;

        for (Account a : mAccounts)
            if ( ! (a.getID() == general.getID())) {
                // skip the account we are depositing into...
                double d = Math.min(amount, a.getBalance());
                if (d > 0.0) {
                    a.withdraw(d);
//                  print(String.format("Liquidate %10.0f from %s", d, s));
                    withdrawn += d;
                    if (withdrawn >= amount)
                        // enough, return
                        return withdrawn;
                }
            }

        return withdrawn;
    }

    public void showAccountNames(StringBuilder sb) {
        for (Account a : mAccounts) {
            String s = a.getName();
            sb.append(String.format("%9s", s.length() > 9 ? s.substring(0, 7) : s));
        }
    }

    public void showAccountBalances(StringBuilder sb) {
        for (Account a : mAccounts) {
            sb.append(String.format("%9.0f", (a.getBalance() / 1000.0)));
        }
    }

    public void endOfYear() {
        for (Account a : mAccounts)
            a.endOfYearReset();
    }

    public List<Account> takeAccounts () {
        List<Account> x = mAccounts;
        mAccounts = new ArrayList<Account>();
        return x;
    }

    public double depositSSAIncome(Simulation simulation, Account general) throws Exception {
        return SSA.getIncome(general, simulation.mCurrentYear, mName);
    }

    public boolean hasAccount(String s) throws Exception {
        return hasAccount(s, false);
    }

    public boolean hasAccount(String s, boolean loose) throws Exception {
        for (Account a : mAccounts)
            if (a.matches(s, loose))
                return true;

        return false;
    }


    public Account getAccount(String s) throws Exception {
        return getMatchingAccount(s, false);
    }
    public Account getMatchingAccount(String s, boolean loose) throws Exception {
        for (Account a : mAccounts)
            if (a.matches (s, loose))
                return a;

        throw new Exception("No such account");
    }

    public void setRetireAge(int age) {
        mRetireYear = mBirthYear + age;
        Main.print(String.format("%s retires at age %d in %d", mName, age, mRetireYear));

    }

    public void setDeathYear(int year) {
        if (year < mDeathYear)
            mDeathYear = year;
    }

    public double getHealthInsurancePremiums(int year) {
        // add health insurance between retirement and
        // medicare
        if ((year > mRetireYear) && ((year - mBirthYear) < 65))
            return 12 * 1000.0;

        return 0.0;
    }

    public void addAccounts(List<Account> x) {
        int j = 0;
        for (Account a : x) {
            mAccounts.add(j++, a);
        }
    }

    public int getDeathYear() {
        return mDeathYear;
    }

    public boolean isWorking(int year) {
        return year < mRetireYear;
    }
}
