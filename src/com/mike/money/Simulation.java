package com.mike.money;

/**
 * Created by mike on 12/19/2015.
 */
public class Simulation {
    public static final int MikeBirthYear = 1948;
    static public final int NogaBirthYear = 1957;

    private final Scenario mScenario;
    public Scenario getmScenario () {
        return mScenario;
    }

    public Simulation(String[] args) {
        mScenario = new Scenario(args);
    }

    public void run() {
        print();

        print(String.format("%4s %10s %10s %10s %10s %10s %10s %10s",
                "Year", "Inv", "Inv Inc", "SSA Inc", "Gross Inc", "Net Inc", "Expenses", "Net"));

        for (int year = mScenario.mStartYear; year < mScenario.mEndYear; ++year) {
            double investmentGain = mScenario.getInvestmentGain();

            double grossIncome = getIncome (year, "mike") + getIncome(year, "noga");

            double ssaIncome = getSSAIncome(year, "mike") + getSSAIncome(year, "noga");

            double netIncome = getNetIncome (investmentGain, ssaIncome, grossIncome);

            double expenses = getExpenses (year);

            double gainLoss = netIncome - expenses;

            print(String.format("%4d %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f %10.0f",
                    year, mScenario.getInvestments(), investmentGain, ssaIncome, grossIncome, netIncome, expenses, gainLoss));

            mScenario.update(gainLoss);
        }
    }

    /**
     * mostly compute something like taxes
     * @TODO other taxes, state,
     * @param investmentGain
     * @param ssaIncome
     * @param income
     * @return
     */
    private double getNetIncome(double investmentGain, double ssaIncome, double income) {
        /*
        as of 2015, married filing jointly

        tax rate    income
        10%	        $0 - $18,450
        15%	        $18,450 - $74,900
        25%	        $74,900 - $151,200
        28%	        $151,200 - $230,450
        */

        double i = investmentGain + ssaIncome + income;
        double tax = 0;
        if (i < 18450)
            tax = 0.10;
        if (i < 74900)
            tax = 0.15;
        if (i < 151200)
            tax = 0.25;
        else
            tax = 0.28;

        return i - (i * tax);
    }

    private double getExpenses(int year) {
        int i = 0;
        while(ExpenseRecs[i].year != year)
            ++i;
        return ExpenseRecs[i].expense;
    }

    /**
     * income includes work and IRA MRDs
     * @param year
     * @param who
     * @return
     */
    private double getIncome(int year, String who) {
        if (who.equals("mike")) {

            double mrd = mScenario.takeMRD(year, who);
            return mrd;
        }

        if (who.equals("noga")) {
            if (year <= 2019)
                return 110000.00;
            if (year <= 2027)
                return 0;

            double mrd = mScenario.takeMRD(year, who);
            return mrd;
        }

        assert false;
        return 0.0;
    }

    private double getSSAIncome(int year, String who) {
        if (year < SSARecs[0].year)
            return 0.0;

        int i = 0;
        while(SSARecs[i].year != year)
            ++i;
        if (who.equals("mike"))
            return SSARecs[i].incomeMike;
        if (who.equals("noga"))
            return SSARecs[i].incomeNoga;

        return 0.0;
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void print() {
        mScenario.print();
    }

    static private class ExpenseRec {

        private final int year;
        private final double expense;

        public ExpenseRec (int year, double expense) {
            this.year = year;
            this.expense = expense;
        }
    }
    static private ExpenseRec[] ExpenseRecs = {
        new ExpenseRec (2015, (6000 * 12) + 15000),     // kids plus Tal in school sophmore
        new ExpenseRec (2016, (6000 * 12) + 15000),     // kids plus Tal in school junior
        new ExpenseRec (2017, (6000 * 12) + 15000),     // kids plus Tal in school senior
        new ExpenseRec (2018, (8000 * 12) + 30000),     // no kids guys in school freshmen
        new ExpenseRec (2019, (8000 * 12) + 30000),     // no kids guys in school soph
        new ExpenseRec (2020, (8000 * 12) + 30000),     // no kids guys in school junior
        new ExpenseRec (2021, (8000 * 12) + 30000),     // no kids guys in school senior

        new ExpenseRec (2022, (6000 * 12) + 6000),     // no kids bit of help
        new ExpenseRec (2023, (6000 * 12) + 6000),     // no kids bit of help
        new ExpenseRec (2024, (6000 * 12) + 6000),     // no kids bit of help
        new ExpenseRec (2025, (6000 * 12) + 6000),     // no kids bit of help
        new ExpenseRec (2026, (6000 * 12) + 6000),     // no kids bit of help
        new ExpenseRec (2027, (6000 * 12) + 3000),     // no kids bit of help
        new ExpenseRec (2028, (6000 * 12) + 3000),     // no kids bit of help
        new ExpenseRec (2029, (6000 * 12) + 3000),     // no kids bit of help
        new ExpenseRec (2030, (6000 * 12) + 3000),     // no kids bit of help

        new ExpenseRec (2031, (6000 * 12) + 0),     // just us
        new ExpenseRec (2032, (6000 * 12) + 0),
        new ExpenseRec (2033, (6000 * 12) + 0),
        new ExpenseRec (2034, (3000 * 12) + 0),
        new ExpenseRec (2035, (3000 * 12) + 0),
        new ExpenseRec (2036, (3000 * 12) + 0),
        new ExpenseRec (2037, (3000 * 12) + 0),
        new ExpenseRec (2038, (3000 * 12) + 0),
        new ExpenseRec (2039, (3000 * 12) + 0),

        new ExpenseRec (2040, (3000 * 12) + 0),
        new ExpenseRec (2041, (3000 * 12) + 0),     // just noga
        new ExpenseRec (2042, (3000 * 12) + 0),
        new ExpenseRec (2043, (3000 * 12) + 0),
        new ExpenseRec (2044, (3000 * 12) + 0),
        new ExpenseRec (2045, (3000 * 12) + 0),
        new ExpenseRec (2046, (3000 * 12) + 0),
        new ExpenseRec (2047, (3000 * 12) + 0),
    };

    /* from http://www.bedrockcapital.com/ssanalyze/
       their revommended simulation with COLA set to zero
     */
    static private SSARec[] SSARecs = {
        new SSARec ("2018", "69 yr., 2 mo.", "Own-70",  "$6,974", "60 yr., 4 mo.", "",       "$6,974"),
        new SSARec ("2019", "70 yr., 2 mo.",       "", "$41,848", "61 yr., 4 mo.", "Own-62", "$7,192"),//,,"$49,040"
        new SSARec ("2020", "71 yr., 2 mo.",       "", "$41,848", "62 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2021", "72 yr., 2 mo.",       "", "$41,848", "63 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2022", "73 yr., 2 mo.",       "", "$41,848", "64 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2023", "74 yr., 2 mo.",       "", "$41,848", "65 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2024", "75 yr., 2 mo.",       "", "$41,848", "66 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2025", "76 yr., 2 mo.",       "", "$41,848", "67 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2026", "77 yr., 2 mo.",       "", "$41,848", "68 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2027", "78 yr., 2 mo.",       "", "$41,848", "69 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2028", "79 yr., 2 mo.",       "", "$41,848", "70 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2029", "80 yr., 2 mo.",       "", "$41,848", "71 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2030", "81 yr., 2 mo.",       "", "$41,848", "72 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2031", "82 yr., 2 mo.",       "", "$41,848", "73 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2032", "83 yr., 2 mo.",       "", "$41,848", "74 yr., 4 mo.",       "", "$21,576"),//,,"$63,424"
        new SSARec ("2033", "84 yr., 2 mo.",       "", "$34,874", "75 yr., 4 mo.", "+ Srvr-70-FRA", "$17,980"),//, "$6,974"),//,"$59,828"
        new SSARec ("2034",              "",       "",        "", "76 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2035",              "",       "",        "", "77 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2036",              "",       "",        "", "78 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2037",              "",       "",        "", "79 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2038",              "",       "",        "", "80 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2039",              "",       "",        "", "81 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2040",              "",       "",        "", "82 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2041",              "",       "",        "", "83 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2042",              "",       "",        "", "84 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2043",              "",       "",        "", "85 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2044",              "",       "",        "", "86 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2045",              "",       "",        "", "87 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2046",              "",       "",        "", "88 yr., 4 mo.",       "", "$41,848"),//,"$41,848"
        new SSARec ("2047",              "",       "",        "", "89 yr., 4 mo.",       "", "$27,899"),//,"$27,899"
    };

    private static class SSARec {
        public int year;
        public int ageMike;
        public String note;
        public double incomeMike;
        public int ageNoga;
        public double incomeNoga;

        public SSARec(String s, String s1, String s2, String s3, String s4, String s5, String s6) {
            year = Integer.parseInt(s);
            incomeMike = s3.length() > 0 ? Double.parseDouble(s3.replace("$", "").replace(",", "")) : 0.0;
            incomeNoga = s6.length() > 0 ? Double.parseDouble(s6.replace("$", "").replace(",", "")) : 0.0;
        }
    }

/*
            "SUM OF BENEFITS",,,"$627,720",,,,"$884,557","$1,512,277"


            ------,------,------,------,------
            "COLA (%)"," 0"
            "Name"," mike"
            "Date of Birth"," 10/27/1948"
            "Gender"," M"
            "Full Retirement Age Benefit"," 2642"
            "Age You Plan to Stop Working"," 67"
            "Monthly Government Pension (if applicable)"," 0"
            "Expected Age at Death"," 85"
            "Marital Status"," Married"
            "Discount Rate (%)"," 2"
            "Spouse's Name"," noga"
            "Spouse's Date of Birth"," 8/15/1957"
            "Spouse's Gender"," F"
            "Spouse's Full Retirement Age Benefit"," 2480"
            "Spouse's Age You Plan to Stop Working"," 60"
            "Spouse's Monthly Government Pension (if applicable)"," 0"
            "Spouse's Expected Age at Death"," 90"

            ------,------,------,------,------
    Recommended Solution:
            "mike will file for his own benefit to start on 11/1/2018, the month after he turns 70 and receive 132% of his full retirement age benefit."
            "noga will file for her own benefit to start on 9/1/2019, the month after she turns 62 and receive 72.5% of her full retirement age benefit."
            "After she is widowed noga will receive her survivor's benefit which will be 132% of mike's full retirement age benefit."

            ------,------,------,------,------
            "The optimal filing solution results in a net-present-value of $1,084,340"
            "In contrast, if the client filed at 67 and the spouse filed at 62 the net-present-value of their combined benefit would be $1,028,059"
            ""
*/
}

