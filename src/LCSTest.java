import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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


        //Using a 2D array to do a similar process to the code provided in class
        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                //Can avoid using third for loop with 2D array
                if (i == 0 || j == 0)
                    LCSArray[i][j] = 0;
                else if (S1[i - 1] == S2[j - 1]) {
                    LCSArray[i][j] = LCSArray[i - 1][j - 1] + 1;
                    if (LCSArray[i][j] > lcsLength){
                        //Much like in the original code this will set the length of the longest substring
                        lcsLength = LCSArray[i][j];
                    }

                } else
                    LCSArray[i][j] = 0;
            }
        }
        return lcsLength;
    }
//Used to get random string for full tests
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
//Used for only specific tests where large strings of only A are needed
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
//Used for only specific tests where only large strings of B are needed
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
        String S1 = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAA";
        String S2 = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

        int l1 = S1.length();
        int l2 = S2.length();
//Used for only testing purposes
        System.out.println("String 1: " + S1);
        System.out.println("String 2: " + S2);
        System.out.println("Length of Longest Common Substring is "
                + LCSubStr(S1.toCharArray(), S2.toCharArray(), l1, l2));
/*      //Uncomment for full experiments
        runFullExperiment("LCSTest-English2-Exp1.txt");
        runFullExperiment("LCSTest-English2-Exp2.txt");
        runFullExperiment("LCSTest-English2-Exp3.txt");
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
/*              //Used for tests not using the book files
                String S1 = getSaltString2(count);
                String S2 = getSaltString3(count);
                String S3 = "A";
                S2 = S2 + S3;

                int l1 = S1.length();
                int l2 = S2.length();
   */

//Code used to get random strings from files containing plain text of books
                String bookString = "";
                try{
                    bookString = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book3.txt")));

                } catch (IOException e){
                    e.printStackTrace();
                }
//Same as above code but used to make the second string
                String bookString2 = "";
                try{
                    bookString2 = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book4.txt")));

                } catch (IOException e){
                    e.printStackTrace();
                }

//Code used to pick a random area to grab the string in the book from
                Random rn = new Random();
                int answer = rn.nextInt(bookString.length() - count);
//Same as above but used to make the second string
                Random rn2 = new Random();
                int answer2 = rn2.nextInt(bookString2.length() - count);

                String S1 = bookString.substring(answer, answer + count);
                String S2 = bookString2.substring(answer2, answer2 + count);

                int l1 = S1.length();
                int l2 = S2.length();


                TrialStopwatch.start(); // *** uncomment this line if timing trials individually

//Timed tests individually so as not to get extra time from needing to randomize everything
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

