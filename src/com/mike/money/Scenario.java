package com.mike.money;

/**
 * Created by mike on 12/20/2015.
 */
public class Scenario {
    public int mStartYear;
    public int mEndYear;
    public double mInterestIncomeRate;

    private double mGeneralInvestmentValue;
    public double mTraditionalIRAValueMike;
    public double mRothIRAValueMike;
    public double mTraditionalIRAValueNoga;
    public double mRothIRAValueNoga = 0;

    public Scenario(String[] args) {
        mGeneralInvestmentValue =
                  150000   // kids inheritance
                +  48000   // eTrade
                +  49000   // Ameritrade trading acct
        ;
        mTraditionalIRAValueMike =
                   72000    // Schwab Traditional IRA
                +   6500    // Ameritrade Traditional IRA
                +  16000    // Ameritrade Roth IRA
                + 137000    // Securion
                +  68000    // TIAA Creff
        ;
        mRothIRAValueMike =
                   16000    // Ameritrade Roth IRA
        ;
        mTraditionalIRAValueNoga =
                   16000    // ?
                +   6400    // Ameritrade Traditional IRA
        ;

        mStartYear = 2015;
        mEndYear = 2047;
        mInterestIncomeRate = 0.010;
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
                print(String.format("xfer %.0f from Mike Roth IRA to general", d)));
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

    public double takeMRD(int year, String who) {
        double lifeExp = MRDTable.getIRAMRDLifeExpectancy(year, who);
        if (lifeExp >= 0.0) {
            double mrd = 0.0;
            if (who.equals("mike")) {
                mrd = mTraditionalIRAValueMike / lifeExp;
                mTraditionalIRAValueMike -= mrd;
                return mrd;
            }
            if (who.equals("noga")) {
                mrd = mTraditionalIRAValueNoga / lifeExp;
                mTraditionalIRAValueNoga -= mrd;
                return mrd;
            }

            assert false;
        }

        // too young, inherited IRA MRD
        // @TODO
        return 2100 * 4.0;
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
}
