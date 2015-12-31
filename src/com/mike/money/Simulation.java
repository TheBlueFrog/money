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
    static public int mEndYear = 2047;
    public static int mCurrentYear;

    public static boolean mStop = true;
    public static int mNogaRetireYear = 0;
    public static String[] mSSASummary = null;

    private Scenario mScenario;

    // show all accounts
    private boolean mShowAccounts = false;

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
            mShowAccounts = true;
        }
        else {
            for (int i = 0; i < args.length; ++i) {
                String arg = args[i];
                if (arg.equals("-showAccounts"))
                    mShowAccounts = true;
//                if (arg.equals("-nogaRetire"))
//                    mNogaRetireYear = Integer.parseInt(args[++i]);
            }
        }

        mScenario = new Scenario(args);
    }

/*         Mike age   Noga age    sell house
  2015        66        57
    16        67        58
    17        68        59
    18        69        60          300k
    19        70        61
    20        71        62
    21        72        63
    22        73        64
    23        74        65
    24        75        66
    25        76        67
    26        77        68
    27        78        69
    28        79        70
 */
    public static int getYear() {
        return mCurrentYear;
    }

    public void run() throws Exception {
        mScenario.print();

//        for (int i = 0; i < mSSASummary.length; ++i)
//            Main.print(mSSASummary[i]);

        String fieldWidth = "%8s";
        String format = String.format("%%4s %s%s%s%s%s%s%s%%s", fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth);
        Main.print(String.format(format,
                "Year", "Work", "SSA", "MRD", "Taxes", "Expenses", "Liquid", "Assets",
                mShowAccounts ? mScenario.showAccountNames() : ""));
        fieldWidth = "%8.0f";
        format = String.format("%%4s %s%s%s%s%s%s%s%%s", fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth, fieldWidth);

        mCurrentYear = mStartYear;
        mStop = false;
        while ((mCurrentYear < mEndYear) && ( !mStop)) {
            Account general = mScenario.getGeneralAccount();

            double startOfYearBalance = general.getBalance();

            mScenario.depositInvestmentGain();

            double workIncome =   mScenario.depositWorkIncome(general, People.Mike, this)
                                + mScenario.depositWorkIncome(general, People.Noga, this);

            double mrd =  depositIRAIncome(general, People.Mike)
                        + depositIRAIncome(general, People.Noga);

            double ssa =   depositSSAIncome(general, People.Mike)
                         + depositSSAIncome(general, People.Noga);

//            double fakeExpenses = 0.0;
//            if (doTest) {
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.Trading);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.RothIRA);
//                fakeExpenses += mScenario.testDrawDown(general, Account.AccountType.General);
//            }

            double taxableIncome =   general.getBalance()
                                   - startOfYearBalance
                                   - (ssa * 0.15);         // up to 85% of SSA is taxable, apparently
            double tax = getTax(taxableIncome);
            double taxPaid = taxableIncome * tax;

            // pay taxes
            general.withdraw(taxPaid);

            mScenario.depositAfterTaxSpecials (general, mCurrentYear);

            // pay expenses

            double expenses = mScenario.getExpenses(mCurrentYear) + taxPaid;

            if (doTest) {
                double d = (mScenario.getAssets() - general.getBalance()) / (mEndYear - mCurrentYear);
                expenses += d;
            }

            double liquidated = 0.0;
            if (general.getBalance() < expenses) {
                liquidated = mScenario.liquidate(general, expenses - general.getBalance());
                general.deposit(liquidated);
            }
            general.withdraw(expenses);

            //                 "Year", "Work", "SSA", "MRD", "Taxes", "Expenses", "Liquid", "Assets",

            Main.print(String.format(format,
                    mCurrentYear,
                    workIncome, ssa, mrd, taxPaid, expenses, liquidated, mScenario.getAssets(),
                    mShowAccounts ? mScenario.showAccountBalances() : ""));

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
     * @return minimum distribution income from IRA
     */
    private double depositIRAIncome(Account general, People who) throws Exception {
        return mScenario.depositMRD(general, mCurrentYear, who);
    }

    private double depositSSAIncome(Account general, People who) throws Exception {
        return SSA.getIncome(general, mCurrentYear, who);
    }


}

