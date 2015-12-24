package com.mike.money;

/**
 * Created by mike on 12/21/2015.
 */
public class SSA {

    /* from http://www.bedrockcapital.com/ssanalyze/
       their recommended simulation with COLA set to zero
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


    public static double getIncome(Account general, int year, Scenario.People who) throws Exception {
        if (year < SSARecs[0].year)
            return 0.0;

        double income = 0.0;

        int i = 0;
        if (year > 2047) {
            // extend last row of table
            i = SSARecs.length - 1;
        }
        else {
            while(SSARecs[i].year != year)
                ++i;
        }

        if (who.equals(Scenario.People.Mike))
            income = SSARecs[i].incomeMike;
        if (who.equals(Scenario.People.Noga))
            income = SSARecs[i].incomeNoga;

        general.deposit(income);
        return income;
    }


}
