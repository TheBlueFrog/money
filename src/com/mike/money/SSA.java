package com.mike.money;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by mike on 12/21/2015.
 *
 * MIKE SSA $ 2352
 */
public class SSA {

    static private SSARec[] SSARecs2 = {

            new SSARec("2018", "69 yr., 2 mo.", "Own-70", "$6,974", "60 yr., 4 mo.", "", "$6,974"),
            new SSARec("2019", "70 yr., 2 mo.", "", "$41,848", "61 yr., 4 mo.", "Own-62", "$7,192"),//,,"$49,040"
            new SSARec("2020", "71 yr., 2 mo.", "", "$41,848", "62 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2019", "70 yr., 2 mo.", "", "$41,848", "61 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2020", "71 yr., 2 mo.", "", "$41,848", "62 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2021", "72 yr., 2 mo.", "", "$41,848", "63 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2022", "73 yr., 2 mo.", "", "$41,848", "64 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2023", "74 yr., 2 mo.", "", "$41,848", "65 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2024", "75 yr., 2 mo.", "", "$41,848", "66 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2025", "76 yr., 2 mo.", "", "$41,848", "67 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2026", "77 yr., 2 mo.", "", "$41,848", "68 yr., 4 mo.", "", "$41,848"),//
            new SSARec("2027", "78 yr., 2 mo.", "", "$41,848", "69 yr., 4 mo.", "Own-70", "$12,697"),//,,"$54,545"
            new SSARec("2028", "79 yr., 2 mo.", "", "$41,848", "70 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2028", "79 yr., 2 mo.", "", "$41,848", "70 yr., 4 mo.", "", "$38,092"),//,,"$79,940"
            new SSARec("2029", "80 yr., 2 mo.", "", "$41,848", "71 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2030", "81 yr., 2 mo.", "", "$41,848", "72 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2031", "82 yr., 2 mo.", "", "$41,848", "73 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2032", "83 yr., 2 mo.", "", "$41,848", "74 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2033", "84 yr., 2 mo.", "", "$41,848", "75 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2034", "85 yr., 2 mo.", "", "$41,848", "76 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2035", "86 yr., 2 mo.", "", "$41,848", "77 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2036", "87 yr., 2 mo.", "", "$41,848", "78 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2037", "88 yr., 2 mo.", "", "$41,848", "79 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2038", "89 yr., 2 mo.", "", "$41,848", "80 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2039", "90 yr., 2 mo.", "", "$41,848", "81 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2040", "91 yr., 2 mo.", "", "$41,848", "82 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2041", "92 yr., 2 mo.", "", "$41,848", "83 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2042", "93 yr., 2 mo.", "", "$41,848", "84 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2043", "94 yr., 2 mo.", "", "$41,848", "85 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2044", "95 yr., 2 mo.", "", "$41,848", "86 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2045", "96 yr., 2 mo.", "", "$41,848", "87 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2046", "97 yr., 2 mo.", "", "$41,848", "88 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2047", "98 yr., 2 mo.", "", "$41,848", "89 yr., 4 mo.", "", "$38,092"),//,"$79,940"
            new SSARec("2048", "99 yr., 2 mo.", "", "$34,874", "90 yr., 4 mo.", "Own-70 > Srvr-70-FRA", "$31,744"),//,"$6,974","$73,592"
            new SSARec("2049", "", "", "", "91 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2050", "", "", "", "92 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2051", "", "", "", "93 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2052", "", "", "", "94 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2053", "", "", "", "95 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2054", "", "", "", "96 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2055", "", "", "", "97 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2056", "", "", "", "98 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2057", "", "", "", "99 yr., 4 mo.", "", "$27,899") //,"$27,899"
    };


    /* from http://www.bedrockcapital.com/ssanalyze/
       their recommended simulation with COLA set to zero
     */
    static private SSARec[] SSARecs1 = {
            new SSARec("2018", "69 yr., 2 mo.", "Own-70", "$6,974", "60 yr., 4 mo.", "", "$6,974"),
            new SSARec("2019", "70 yr., 2 mo.", "", "$41,848", "61 yr., 4 mo.", "Own-62", "$7,192"),//,,"$49,040"
            new SSARec("2020", "71 yr., 2 mo.", "", "$41,848", "62 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2021", "72 yr., 2 mo.", "", "$41,848", "63 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2022", "73 yr., 2 mo.", "", "$41,848", "64 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2023", "74 yr., 2 mo.", "", "$41,848", "65 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2024", "75 yr., 2 mo.", "", "$41,848", "66 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2025", "76 yr., 2 mo.", "", "$41,848", "67 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2026", "77 yr., 2 mo.", "", "$41,848", "68 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2027", "78 yr., 2 mo.", "", "$41,848", "69 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2028", "79 yr., 2 mo.", "", "$41,848", "70 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2029", "80 yr., 2 mo.", "", "$41,848", "71 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2030", "81 yr., 2 mo.", "", "$41,848", "72 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2031", "82 yr., 2 mo.", "", "$41,848", "73 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2032", "83 yr., 2 mo.", "", "$41,848", "74 yr., 4 mo.", "", "$21,576"),//,,"$63,424"
            new SSARec("2033", "84 yr., 2 mo.", "", "$34,874", "75 yr., 4 mo.", "+ Srvr-70-FRA", "$17,980"),//, "$6,974"),//,"$59,828"
            new SSARec("2034", "", "", "", "76 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2035", "", "", "", "77 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2036", "", "", "", "78 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2037", "", "", "", "79 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2038", "", "", "", "80 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2039", "", "", "", "81 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2040", "", "", "", "82 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2041", "", "", "", "83 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2042", "", "", "", "84 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2043", "", "", "", "85 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2044", "", "", "", "86 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2045", "", "", "", "87 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2046", "", "", "", "88 yr., 4 mo.", "", "$41,848"),//,"$41,848"
            new SSARec("2047", "", "", "", "89 yr., 4 mo.", "", "$27,899"),//,"$27,899"
    };

    private static class SSARec {
        public int year;
        public int ageMike;
        public String note;
        public double incomeMike = 0.0;
        public int ageNoga;
        public double incomeNoga = 0.0;

        public SSARec(String s, String s1, String s2, String s3, String s4, String s5, String s6) {
            year = Integer.parseInt(s);
            incomeMike = s3.length() > 0 ? Double.parseDouble(s3.replace("$", "").replace(",", "")) : 0.0;
            incomeNoga = s6.length() > 0 ? Double.parseDouble(s6.replace("$", "").replace(",", "")) : 0.0;
        }

        public SSARec(String s, String s1, String s2, String s3) {
            String ss = s.replace("\"", "");
            year = Integer.parseInt(ss);
            ss = s1.replace("\"", "");
            incomeMike = ss.length() > 0 ? Double.parseDouble(ss.replace("$", "").replace(",", "")) : 0.0;
            ss = s2.replace("\"", "");
            incomeNoga = ss.length() > 0 ? Double.parseDouble(ss.replace("$", "").replace(",", "")) : 0.0;
            ss = s3.replace("\"", "");
            incomeNoga += ss.length() > 0 ? Double.parseDouble(ss.replace("$", "").replace(",", "")) : 0.0;
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

    static SSARec[] SSARecs = null;

    static public void init(Scenario scenario, String[] args) throws Exception {
        for(int i = 0; i < args.length; ++i) {
            String s = args[i];
            if (s.equals("-loadSSA"))
                load(scenario, args[i+1]);
        }
    }

    private static boolean load(Scenario scenario, String fname) throws Exception {
        File file = new File(fname);
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        scenario.print(fname);

        {
            // fname looks like "...-rnn-fnn..."
            // the -r is the retire age for Noga
            int i = fname.indexOf("-r");
            String s = fname.substring(i+2, i+4);
            int age = Integer.parseInt(s);
            Simulation.mNogaRetireYear = Simulation.NogaBirthYear + age;
            Main.print(String.format("Noga retires at age %d in %d", age, Simulation.mNogaRetireYear));
        }

        Simulation.mSSASummary = collectSummary(lines);

        int num = countDataTableLines(lines);

        SSARecs = new SSARec[num-1];

        for (int i = 1; i < num; ++i) {
            String s = lines.get(i);

//            String s = "Sachin,,M,\"Maths,Science,English\",Need to improve in these subjects.";
            String[] split = s.split(",(?=([^\"]|\"[^\"]*\")*$)");

//            boolean outside = true;
//            int j = 0;
//            while (outside && (s[j] != '"'))
//            String[] a = lines.get(i).split("\",\"");

            /*
            "Year",,"Type(Mike)","Own(Mike)","Age on Jan 1(Noga)","Type(Noga)","Own(Noga)","Survivor's(Noga)","Combined Benefits"
            "2018","69 yr., 2 mo.","Own-70","$6,974","60 yr., 4 mo.",,,,"$6,974"
            [0]       [1]              [2]    [3]      [4]                [8]
            "2027","78 yr., 2 mo.",       ,"$41,848","69 yr., 4 mo.","Own-70","$12,697",,"$54,545"
            [0]       [1]              [2]    [3]      [4]              [5]      [6]       [8]
             */

            SSARec r = new SSARec(split[0], split[3], split[6], split[7]);
            SSARecs[i-1] = r;
        }

//        checkSpouseAgeToRetire(lines);

        return true;
    }

    private static int countDataTableLines(List<String> lines) {
        int i = 0;
        for (String line : lines) {
            String[] a = line.split(",");
            String aa = a[0].replace("\"", "");
            if (aa.equals("Year")){
                // skip
            }
            else if (aa.equals("SUM OF BENEFITS"))
                return i;
            else
                ++i;
        }
        return 0;
    }

    private static void checkSpouseAgeToRetire (List<String> lines) throws Exception {
        for (String line : lines) {
            if (line.contains("Spouse's Age You Plan to Stop Working")) {
                String[] a = line.split(",");
                int i = Integer.parseInt(a[1].replace("\"", "").replace(" ", ""));
                if (i != (Simulation.mNogaRetireYear - Simulation.NogaBirthYear))
                    throw new Exception("Wrong year for Noga to retire");
            }
        }
    }
    private static String[] collectSummary (List<String> lines) throws Exception {
        String[] sum = new String[3];
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.contains("Recommended Solution:")) {
                sum[0] = lines.get(i+1);
                sum[1] = lines.get(i+2);
                sum[2] = lines.get(i+3);
                return sum;
            }
        }
        return null;
    }

    public static double getIncome(Account general, int year, Scenario.People who) throws Exception {
        if (year < SSARecs[0].year)
            return 0.0;

        double income = 0.0;

        int i = 0;
        if (year > SSARecs[SSARecs.length - 1].year) {
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
