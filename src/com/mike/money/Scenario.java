package com.mike.money;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {
    public int mStartYear = 2015;
    public int mEndYear = 2047;
    public double mInterestIncomeRate = 0.010;

    // setup default data for run
    private double mGeneralInvestmentValue =
              150000   // kids inheritance
            +  48000   // eTrade
            +  49000   // Ameritrade trading acct
    ;
    public double mTraditionalIRAValueMike =
               72000    // Schwab Traditional IRA
            +   6500    // Ameritrade Traditional IRA
            +  16000    // Ameritrade Roth IRA
            + 137000    // Securion
            +  68000    // TIAA Creff
    ;
    public double mRothIRAValueMike =
               16000    // Ameritrade Roth IRA
    ;
    public double mTraditionalIRAValueNoga =
               16000    // ?
            +   6400    // Ameritrade Traditional IRA
    ;

    public double mRothIRAValueNoga = 0;

    private Expenses mExpenses;


    public Scenario(String[] args) {

        mExpenses = new Expenses(args);

        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-investment"))
                mGeneralInvestmentValue = Double.parseDouble(args[++i]);
            if (arg.equals("-MikeTraditionalIRA"))
                mTraditionalIRAValueMike = Double.parseDouble(args[++i]);
            if (arg.equals("-MikeRothIRA"))
                mRothIRAValueMike = Double.parseDouble(args[++i]);
            if (arg.equals("-NogaTraditionalIRA"))
                mTraditionalIRAValueNoga = Double.parseDouble(args[++i]);
            if (arg.equals("-NogaRothIRA"))

            if (arg.equals("-startYear"))
                mStartYear = 2015;
            if (arg.equals("-endYear"))
                mEndYear = 2047;
            if (arg.equals("-roi"))
                mInterestIncomeRate = Double.parseDouble(args[++i]);
        }
    }


    public double getInvestmentGain() {
        double gain = getInvestments() * mInterestIncomeRate;
        return gain;
    }

    /**
     * given the gain/loss of the expenses vs income update
     * the investments
     *
     * any MRD has already been deducted from the IRA
     *
     * any gain simply goes in a general investment fund
     * any loss is assumed to be coming from the general investment
     * fund, hence deducted from that.
     *
     * @param gainLoss
     */
    public void update(double gainLoss) {
        mGeneralInvestmentValue += gainLoss;

        if (mGeneralInvestmentValue < 20000) {
            // move 20K to investment acct from somewhere

            double xfer = 20000 - mGeneralInvestmentValue;

            // xfer $ from Mike Roth then Mike Traditional
            // then from Noga Roth then Traditional

            if (mRothIRAValueMike > 0) {
                double d = Math.min(xfer, mRothIRAValueMike);
                print(String.format("xfer %.0f from Mike Roth IRA to general", d));
                mRothIRAValueMike -= d;
                mGeneralInvestmentValue += d;
                xfer -= d;
            }

            if (xfer > 0) {
                if (mRothIRAValueNoga > 0) {
                    double d = Math.min(xfer, mRothIRAValueNoga);
                    mRothIRAValueNoga -= d;
                    mGeneralInvestmentValue += d;
                    xfer -= d;
                }
            }

            if (xfer > 0) {
                if (mTraditionalIRAValueMike > 0) {
                    double d = Math.min(xfer, mTraditionalIRAValueMike);
                    mTraditionalIRAValueMike -= d;
                    mGeneralInvestmentValue += d;
                    xfer -= d;
                }
            }

            if (xfer > 0) {
                if (mTraditionalIRAValueNoga > 0) {
                    double d = Math.min(xfer, mTraditionalIRAValueNoga);
                    mTraditionalIRAValueNoga -= d;
                    mGeneralInvestmentValue += d;
                    xfer -= d;
                }
            }

            if (xfer > 0) {
                Main.simulation.print("Gone broke!");
            }
        }
    }

    public double getInvestments() {
        return    mGeneralInvestmentValue
                + mTraditionalIRAValueMike
                + mTraditionalIRAValueNoga
                + mRothIRAValueMike
                + mRothIRAValueNoga
                ;
    }

    public void print() {
        Main.simulation.print(String.format("Initial investment $%.0f", getInvestments()));
        Main.simulation.print(String.format("Annual investment income rate %.4f", mInterestIncomeRate));
        Main.simulation.print(String.format("Start-end years: %d-%d", mStartYear, mEndYear));
    }
    public void print(String s) {
        Main.simulation.print(s);
    }

    public double getExpenses(int year) {
        return mExpenses.getExpenses(year);
    }

    public int getAge(int year, String who) {
        int birthYear = 0;
        if (who.equals("mike"))
            birthYear = Simulation.MikeBirthYear;
        if (who.equals("noga"))
            birthYear = Simulation.NogaBirthYear;
        return year - birthYear;
    }

    public double takeMRD(int year, String who) {
        int age = getAge(year, who);
        double mrd = 0.0;
        if (who.equals("mike")) {
            mrd = MRDTable.getMRD (age, mTraditionalIRAValueMike);
            mTraditionalIRAValueMike -= mrd;
        }
        if (who.equals("noga")) {
            mrd = MRDTable.getMRD (age, mTraditionalIRAValueNoga);
            mTraditionalIRAValueNoga -= mrd;
        }
        return mrd;
    }
}
