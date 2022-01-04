package java112.analyzer;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Performs analysis and creates a text file containing each token that is
 * longer than a character length specified in the properties file.
 * 
 * @author Ryan Chua
 */
public class LargestTokensAnalyzer implements TokenAnalyzer {

    /** The properties file to read from                                      */
    private Properties properties;

    /** A set of all tokens longer than the minimum character length          */
    private Set<String> largestTokens;

    /** The minimum character length for a token to be considered large       */
    private int minimumTokenLength;

    /**
     * Creates a new {@code LargestTokensAnalyzer}. Creates an instance of a
     * {@code TreeSet} and assigns it to {@code largestTokens}.
     */
    public LargestTokensAnalyzer() {
        largestTokens = new TreeSet<String>();
    }

    /**
     * Creates a new {@code LargestTokensAnalyzer}. Creates an instance of a
     * {@code TreeSet} and assigns it to {@code largestTokens}. Assigns a
     * properties object to the instance variable. Reads the properties file to
     * set the value of {@code minimumTokenLength}.
     * 
     * @param properties the properties object to read from
     */
    public LargestTokensAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        this.minimumTokenLength = Integer.valueOf(
                properties.getProperty("largest.words.minimum.length"));
    }

    /**
     * Adds the token to {@code largestTokens} if it is greater than
     * or equal to {@code minimumTokenLength}.
     * 
     * @param token the token to analyze
     */
    public void processToken(String token) {
        if (token.length() >= minimumTokenLength) {
            largestTokens.add(token);
        }
        
    }

    /**
     * Creates a file containing the tokens that are longer than the
     * {@code minimumTokenLength}, each displayed on their own line.
     * 
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.largest.words");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            for (String token : largestTokens) {
                writer.println(token);
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.largest.words"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.largest.words"));
            exception.printStackTrace();
        }
        
    }

    // Getters and Setters

    /**
     * Gets the set of unique tokens above a specified length.
     * 
     * @return the longest tokens in the input file
     */
    public Set<String> getLargestTokens() {
        return largestTokens;
    }
}
