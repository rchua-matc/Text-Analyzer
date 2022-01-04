package java112.analyzer;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Performs analysis and creates a text file containing each unique token in 
 * the input file.
 * 
 * @author Ryan Chua
 */
public class DistinctTokensAnalyzer implements TokenAnalyzer {

    /** A set of all unique tokens in the file                                */
    private Set<String> distinctTokens;

    /** The properties file to read from                                      */
    private Properties properties;

    /**
     * Creates a new {@code DistinctTokensAnalyzer}. Creates an instance of a
     * {@code TreeSet} and assigns it to {@code distinctTokens}.
     */
    public DistinctTokensAnalyzer() {
        distinctTokens = new TreeSet<String>();
    }

    /**
     * Creates a new {@code DistinctTokensAnalyzer}. Creates an instance of a
     * {@code TreeSet} and assigns it to {@code distinctTokens}. Assigns a
     * properties object to the instance variable.
     * 
     * @param properties the properties object to read from
     */
    public DistinctTokensAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Attempts to add the token to {@code distinctTokens}. The token will
     * only be added if it is not already contained in the set.
     * 
     * @param token the token to add to the set of unique tokens
     */
    public void processToken(String token) {
        distinctTokens.add(token);
    }

    /**
     * Creates a file containing each unique token on its own line.
     * 
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.distinct");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            for (String token : distinctTokens) {
                writer.println(token);
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.distinct"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.distinct"));
            exception.printStackTrace();
        }
    }

    // Getters and Setters

    /**
     * Gets the set of unique tokens in the analyzed file.
     * 
     * @return the unique tokens in the input file
     */
    public Set<String> getDistinctTokens() {
        return distinctTokens;
    }
}
