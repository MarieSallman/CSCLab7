import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;


public class LCSTest {

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    static int LCSubStr(char S1[], char S2[], int l1, int l2) {

        //Creating a 2D array for the strings
        int LCSArray[][] = new int[l1 + 1][l2 + 1];
        //lcsLength to store the length of the longest substring found
        int lcsLength = 0;


        //
        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                if (i == 0 || j == 0)
                    LCSArray[i][j] = 0;
                else if (S1[i - 1] == S2[j - 1]) {
                    LCSArray[i][j] = LCSArray[i - 1][j - 1] + 1;
                    if (LCSArray[i][j] > lcsLength){
                        lcsLength = LCSArray[i][j];
                    }

                } else
                    LCSArray[i][j] = 0;
            }
        }
        return lcsLength;
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

    // Driver Program to test above function
    public static void main(String[] args) {
        String S1 = "OldSite:GeeksforGeeks.org";
        String S2 = "NewSite:GeeksQuiz.com";

        int l1 = S1.length();
        int l2 = S2.length();

        System.out.println("Length of Longest Common Substring is "
                + LCSubStr(S1.toCharArray(), S2.toCharArray(), l1, l2));

        runFullExperiment("LCSTest-Worst-Exp1.txt");
        runFullExperiment("LCSTest-Worst-Exp2.txt");
        runFullExperiment("LCSTest-Worst-Exp3.txt");
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

                String S1 = getSaltString2(count);
                String S2 = getSaltString3(count);
                String S3 = "A";
                S2 = S2 + S3;

                int l1 = S1.length();
                int l2 = S2.length();


                TrialStopwatch.start(); // *** uncomment this line if timing trials individually


                LCSubStr(S1.toCharArray(), S2.toCharArray(), l1, l2);



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

