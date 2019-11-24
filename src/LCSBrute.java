import java.io.*;
import java.util.Random;

public class LCSBrute {

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public static void main(String[] args) {
        String A = "abcdefghi";
        String B = "?abcdefghi";
        int count = 18;
        lcsBrute(A,B);

        String C = getSaltString(count);
        String D = getSaltString(count);
        String E = getSaltString(count) + C;

        System.out.println(C);
        System.out.println(E);
        lcsBrute(C,E);

        runFullExperiment("LCSBrute-Worst-Exp1.txt");
        runFullExperiment("LCSBrute-Worst-Exp2.txt");
        runFullExperiment("LCSBrute-Worst-Exp3.txt");

    }

    static String getSaltString(int count) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < count) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    static String getSaltString2(int count) {
        String SALTCHARS = "A";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < count) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    static String getSaltString3(int count) {
        String SALTCHARS = "B";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < count) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static int lcsBrute(String S1, String S2){

        int l1 = S1.length();
        int l2 = S2.length();
        int lcsLength = 0;
        int lcsStart1 = 0;
        int lcsStart2 = 0;
        int k;


        for (int i = 0; i < l1; i++){
            for (int j = 0; j < l2; j++){
                for (k = 0; k < l1 - i && k < l2 -j; k++){
                    if (S1.charAt(i + k) != S2.charAt(j + k)){
                        break;
                    }

                }
                if (k > lcsLength){
                    lcsLength = k;
                    lcsStart1 = i;
                    lcsStart2 = j;
                }
            }
        }
        return lcsLength;
    }

    static void runFullExperiment(String resultsFileName){

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file...

        }


        long numberOfTrials = 1000;
        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial



        resultsWriter.println("#InputNumber       AverageTime     Number of Trials"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        for(int inputNumber=1;inputNumber<=4096; inputNumber*=2) {

            // progress message...

            System.out.println("Running test for digit size "+inputNumber+" ... ");



            /* repeat for desired number of trials (for a specific size of input)... */

            long batchElapsedTime = 0;

            // generate a list of randomly spaced integers in ascending sorted order to use as test input

            // In this case we're generating one list to use for the entire set of trials (of a given input size)

            // but we will randomly generate the search key for each trial






            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)

            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the

            // stopwatch methods themselves

            //BatchStopwatch.start(); // comment this line if timing trials individually



            // run the tirals

            for (long trial = 0; trial < numberOfTrials; trial++) {

                int count = inputNumber;

                String C = getSaltString2(count);
                String D = getSaltString3(count);
                String E = "A";
                D = D + E;



                TrialStopwatch.start(); // *** uncomment this line if timing trials individually


                lcsBrute(C,D);




                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

            resultsWriter.printf("%12d  %15.2f  %12d \n",inputNumber, averageTimePerTrialInBatch, numberOfTrials); // might as well make the columns look nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }}
}
