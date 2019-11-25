import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class LCSBrute {

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public static void main(String[] args) {
        String A = "abcdexabcabcxabcdef";
        String B = "abcdeabcabcdfabcdefg";
        int count = 18;
        System.out.println("String 1: " + A);
        System.out.println("String 2: " + B);
        System.out.println(lcsBrute(A,B));
/*      //Used for testing purposes

        String C = getSaltString(count);
        String D = getSaltString(count);
        String E = getSaltString(count) + C;

        System.out.println("String 1: " + C);
        System.out.println("String 2: " + E);
        System.out.println(lcsBrute(C,E));
*/



/*      //Used to run full experiments
        runFullExperiment("LCSBrute-English2-Exp1.txt");
        runFullExperiment("LCSBrute-English2-Exp2.txt");
        runFullExperiment("LCSBrute-English2-Exp3.txt");
*/


    }

    //Used as main randomizer for all tests to make random strings of a given input size
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
//Used to make string for large amounts of only A to test code
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

    //Used to make string for large amounts of only B to test code
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

        //Setting ints for lengths of string
        int l1 = S1.length();
        int l2 = S2.length();
        int lcsLength = 0;
        int lcsStart1 = 0;
        int lcsStart2 = 0;
        int k;

        //Made from pseudocode provided in class
        for (int i = 0; i < l1; i++){
            for (int j = 0; j < l2; j++){
                for (k = 0; k < l1 - i && k < l2 -j; k++){
                    if (S1.charAt(i + k) != S2.charAt(j + k)){
                        break;  //If characters are not the same break out of loop

                    }

                }
                //Determine if the current substring length is the longest found
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
                String C = getSaltString(count);
                String D = getSaltString(count);
                //String E = "A";
                //D = D + E;
*/

//Used when getting random substrings from full books
                String bookString = "";
        try{
            bookString = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book3.txt")));

        } catch (IOException e){
            e.printStackTrace();
        }
//Used to get second random substring from book
        String bookString2 = "";
        try{
            bookString2 = new String(Files.readAllBytes(Paths.get("/home/marie/Results/book4.txt")));

        } catch (IOException e){
            e.printStackTrace();
        }

//Randomize the area of the book the string is taken from
        Random rn = new Random();
        int answer = rn.nextInt(bookString.length() - count);
//Randomize the area of the book the string is taken from
        Random rn2 = new Random();
        int answer2 = rn2.nextInt(bookString2.length() - count);





                TrialStopwatch.start(); // *** uncomment this line if timing trials individually


                // lcsBrute(C,D);

                lcsBrute(bookString.substring(answer, answer + count), bookString2.substring(answer2, answer2 + count));


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
