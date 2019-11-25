import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class LCSBetter {

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    static void printLCSubStr(String X, String Y, int m, int n)
    {
        // Create a table to store lengths of longest common
        // suffixes of substrings.   Note that LCSuff[i][j]
        // contains length of longest common suffix of X[0..i-1]
        // and Y[0..j-1]. The first row and first column entries
        // have no logical meaning, they are used only for
        // simplicity of program
        int[][] LCSuff = new int[m + 1][n + 1];

        // To store length of the longest common substring
        int len = 0;

        // To store the index of the cell which contains the
        // maximum value. This cell's index helps in building
        // up the longest common substring from right to left.
        int row = 0, col = 0;

        /* Following steps build LCSuff[m+1][n+1] in bottom
           up fashion. */
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    LCSuff[i][j] = 0;

                else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    LCSuff[i][j] = LCSuff[i - 1][j - 1] + 1;
                    if (len < LCSuff[i][j]) {
                        len = LCSuff[i][j];
                        row = i;
                        col = j;
                    }
                }
                else
                    LCSuff[i][j] = 0;
            }
        }

        // if true, then no common substring exists
        if (len == 0) {
            System.out.println("String 1: " + X);
            System.out.println("String 2: " + Y);
            System.out.println("No Common Substring");
            return;
        }

        // allocate space for the longest common substring
        String resultStr = "";

        // traverse up diagonally form the (row, col) cell
        // until LCSuff[row][col] != 0
        while (LCSuff[row][col] != 0) {
            resultStr = X.charAt(row - 1) + resultStr; // or Y[col-1]
            --len;

            // move diagonally up to previous cell
            row--;
            col--;
        }

        // required longest common substring
        //System.out.println(resultStr);
        System.out.println("String 1: " + X);
        System.out.println("String 2: " + Y);
        System.out.println("Length of Longest Common Substring is " + resultStr.length());
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

    // Driver code
    public static void main(String[] args) {
        String X = getSaltString(10);
        String Y = "Z";

        int m = X.length();
        int n = Y.length();

        printLCSubStr(X, Y, m, n);
/*
        runFullExperiment("LCSBetter-English2-Exp1.txt");
        runFullExperiment("LCSBetter-English2-Exp2.txt");
        runFullExperiment("LCSBetter-English2-Exp3.txt");
  */
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

        for(int inputNumber=1;inputNumber<=1024; inputNumber*=2) {

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
/*
                String X = getSaltString2(count);
                String Y = getSaltString3(count);
                String Z = "A";
                Y = Y + Z;

                int m = X.length();
                int n = Y.length();
*/

                String bookString = "";
                try{
                    bookString = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book3.txt")));

                } catch (IOException e){
                    e.printStackTrace();
                }

                String bookString2 = "";
                try{
                    bookString2 = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book4.txt")));

                } catch (IOException e){
                    e.printStackTrace();
                }


                Random rn = new Random();
                int answer = rn.nextInt(bookString.length() - count);

                Random rn2 = new Random();
                int answer2 = rn2.nextInt(bookString2.length() - count);

                String X = bookString.substring(answer, answer + count);
                String Y = bookString2.substring(answer2, answer2 + count);


                int m = X.length();
                int n = Y.length();

                TrialStopwatch.start(); // *** uncomment this line if timing trials individually


                printLCSubStr(X, Y, m, n);



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
