package com.mike.money;

import static com.mike.money.Scenario.*;

/**
 * Created by mike on 12/19/2015.
 */
public class Simulation {
    public static final int MikeBirthYear = 1948;
    static public final int NogaBirthYear = 1957;

    // if this is true then we run a test that should "go broke" in
    // exactly 10 years
    static public boolean doTest = false;

    static public int mStartYear = 2015;
    static public int mEndYear = 2057;
    public static int mCurrentYear;

    public static boolean mStop = true;

    private Scenario mScenario;

    public Scenario getScenario () {
        return mScenario;
    }

    public Simulation(String[] args) throws Exception {
        for (String s : args)
            if (s.contains("-test"))
                doTest = true;

        if (doTest) {
            mStartYear = 2000;
            mEndYear = mStartYear + 10 + 1;
        }
//        else {
//            for (int i = 0; i < args.length; ++i) {
//                String arg = args[i];
//                if (arg.equals("-noSSA"))
//                    mUseSSA = false;
//            }
//        }

        mScenario = new Scenario(args);
    }


    public static int getYear() {
        return mCurrentYear;
    }

    public void run() throws Exception {
        print();

        print(String.format("%4s %10s %10s %10s %s",
                "Year", "Expenses", "Tax Paid", "Assets", mScenario.showAccountNames()));

        mCurrentYear = mStartYear;
        mStop = false;
        while ((mCurrentYear < mEndYear) && ( !mStop)) {
            Account general = mScenario.getGeneralAccount();

            double startOfYearBalance = general.getBalance();

            mScenario.depositInvestmentGain();

            depositWorkIncome(general, People.Mike);
            depositIRAIncome(general, People.Mike);

            depositWorkIncome(general, People.Noga);
            depositIRAIncome(general, People.Noga);

            depositSSAIncome(general, People.Mike);
            depositSSAIncome(general, People.Noga);

//            double fakeExpenses = 0.0;
//            if (doTest) {
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.Trading);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.RothIRA);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//            }

            double taxableIncome = general.getBalance() - startOfYearBalance;
            double tax = getTax(taxableIncome);
            double taxPaid = taxableIncome * tax;
            general.withdraw(taxPaid);

            double expenses = mScenario.getExpenses(mCurrentYear) + taxPaid;

            if (doTest) {
                double d = (mScenario.getAssets() - general.getBalance()) / (mEndYear - mCurrentYear);
                expenses += d;
            }

            if (general.getBalance() >= expenses) {
                general.withdraw(expenses);
            }
            else {
                double x = mScenario.liquidate(general, expenses - general.getBalance());
                general.deposit(x);
            }

            String s = mScenario.showAccountBalances();
            print(String.format("%4d %10.0f %10.0f %10.0f %s", //%10.0f %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f",
                    mCurrentYear, expenses, taxPaid, mScenario.getAssets(), s));//, investmentGain, ssaIncome, mikeIncome, nogaIncome, netIncome, expenses, gainLoss));

            mCurrentYear++;
        }
    }

    private double getTax(double taxableIncome) {

        if (doTest)
            return 0.1;

        /*
        as of 2015, married filing jointly

        tax rate    income
        10%	        $0 - $18,450
        15%	        $18,450 - $74,900
        25%	        $74,900 - $151,200
        28%	        $151,200 - $230,450
        */

        double tax = 0;

        if (taxableIncome < 18450)
            tax = 0.10;
        if (taxableIncome < 74900)
            tax = 0.15;
        if (taxableIncome < 151200)
            tax = 0.25;
        else
            tax = 0.28;

        return tax;
    }

    /**
     * @param who
     * @return work income
     */
    private void depositWorkIncome(Account general, People who) throws Exception {
        double income = 0.0;

        if (doTest) {
            income = 10.0;
        }
        else
            if (who.equals(People.Noga)) {
                if (mCurrentYear <= 2019)
                    income += 110000.00;
            }

        general.deposit(income);
    }

    /**
     * @param who
     * @return minimum distribution income from IRA
     */
    private void depositIRAIncome(Account general, People who) throws Exception {
        mScenario.depositMRD(general, mCurrentYear, who);
    }

    private void depositSSAIncome(Account general, People who) throws Exception {
        SSA.getIncome(general, mCurrentYear, who);
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void print() {
        mScenario.print();
    }

}

