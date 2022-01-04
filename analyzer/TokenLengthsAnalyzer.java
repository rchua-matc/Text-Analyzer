package java112.analyzer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Tracks the frequency that each token length appears in the file and writes
 * a file containing the results in numeric and visual formats.
 *
 * @author Ryan Chua
 */
public class TokenLengthsAnalyzer implements TokenAnalyzer {

    /** The properties file to read from                                      */
    private Properties properties;

    /** A map of the token lengths and their frequency                        */
    private Map<Integer, Integer> tokenLengths;

    /**
     * Creates a new {@code TokenLengthsAnalyzer}. Creates an instance of a
     * {@code TreeMap} and assigns it to {@code tokenLengths}.
     */
    public TokenLengthsAnalyzer() {
        tokenLengths = new TreeMap<Integer, Integer>();
    }

    /**
     * Creates a new {@code TokenLengthsAnalyzer}. Creates an instance of a
     * {@code TreeMap} and assigns it to {@code tokenLengths}. Assigns a
     * properties object to the instance variable.
     *
     * @param properties the properties object to read from
     */
    public TokenLengthsAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Adds the token length to the map or increments its counter by one.
     *
     * @param token the token to process
     */
    public void processToken(String token) {
        int length = token.length();
        Integer count = tokenLengths.get(length);
        tokenLengths.put(length, (count == null) ? 1 : count + 1);
    }

    /**
     * Creates an output file with each token length and its frequency
     * displayed on a line. The file also includes a histogram of the token
     * lengths and their frequencies.
     *
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.token.lengths");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            // Numeric output
            for (Map.Entry<Integer, Integer> entry : tokenLengths.entrySet()) {
                writer.println(entry.getKey() + "\t" + entry.getValue());
            }

            // Histogram output
            int maxCount = Collections.max(tokenLengths.values());
            for (Map.Entry<Integer, Integer> entry : tokenLengths.entrySet()) {
                writer.println(entry.getKey() + "\t"
                        + writeAsterisks(entry.getValue(), maxCount));
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.token.lengths"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.token.lengths"));
            exception.printStackTrace();
        }
    }

    /**
     * Creates the bar of the histogram with asterisks. The highest frequency
     * will go to a total output length of 80 characters. Lower frequencies
     * will have a number of asterisks relative to their percentage of the max
     * frequency. Token lengths will always have at least one asterisk.
     *
     * @param count the frequency a token length appears in the file
     * @param maxCount the highest frequency of a token length in the file
     * @return a string of asterisks proportional to other frequencies
     */
    public String writeAsterisks(int count, int maxCount) {
        int asteriskCount = Math.round(((float) count / maxCount) * 76);
        asteriskCount = (asteriskCount > 0) ? asteriskCount : 1;

        return "*".repeat(asteriskCount);
    }

    // Getters and Setters

    /**
     * Gets the map of token lengths in the analyzed file and the number of
     * times they appear.
     *
     * @return the token lengths and the number of times they appear
     */
    public Map<Integer, Integer> getTokenLengths() {
        return tokenLengths;
    }
}