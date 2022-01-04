package java112.analyzer;

import java.util.Properties;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Performs analysis and creates a text file containing each unique token and
 * the number of times they appear in the input file.
 *
 * @author Ryan Chua
 */
public class DistinctTokenCountsAnalyzer implements TokenAnalyzer {

    /** The properties file to read from                                      */
    private Properties properties;

    /** A map of the distinct tokens in the file and their frequency          */
    private Map<String, Integer> distinctTokenCounts;

    /**
     * Creates a new {@code DistinctTokenCountsAnalyzer}. Creates an instance
     * of a {@code TreeMap} and assigns it to {@code distinctTokenCounts}.
     */
    public DistinctTokenCountsAnalyzer() {
        distinctTokenCounts = new TreeMap<String, Integer>();
    }

    /**
     * Creates a new {@code DistinctTokenCountsAnalyzer}. Creates an instance
     * of a {@code TreeMap} and assigns it to {@code distinctTokenCounts}.
     * Assigns a properties object to the instance variable.
     *
     * @param properties the properties object to read from
     */
    public DistinctTokenCountsAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Adds the token to the map or increments its counter by one.
     *
     * @param token the token to add to the map of unique tokens
     */
    public void processToken(String token) {
        Integer count = distinctTokenCounts.get(token);
        distinctTokenCounts.put(token, (count == null) ? 1 : count + 1);
    }

    /**
     * Creates an output file with each distinct word and its frequency
     * displayed on a line. The word and the number of times the word appears
     * in the file are separated by a tab.
     *
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.distinct.counts");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            for (Map.Entry<String, Integer> entry :
                    distinctTokenCounts.entrySet()) {
                writer.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.distinct.counts"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.distinct.counts"));
            exception.printStackTrace();
        }
    }

    // Getters and Setters

    /**
     * Gets the map of unique tokens in the analyzed file and the number of
     * times they appear.
     *
     * @return the unique tokens in the input file and number of times they
     *         appear
     */
    public Map<String, Integer> getDistinctTokenCounts() {
        return distinctTokenCounts;
    }
}