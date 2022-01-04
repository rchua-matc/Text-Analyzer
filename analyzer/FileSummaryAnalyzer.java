package java112.analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Creates a summary of the analyzed text file.
 * 
 * @author Ryan Chua
 */
public class FileSummaryAnalyzer implements TokenAnalyzer {

    /** The overall number of tokens in the analyzed file                     */
    private int totalTokensCount;

    /** The properties file to read from                                      */
    private Properties properties;

    /**
     * Creates a new {@code FileSummaryAnalyzer}. Initializes the
     * {@code totalTokensCount} to 0.
     */
    public FileSummaryAnalyzer() {
        totalTokensCount = 0;
    }

    /**
     * Creates a new {@code FileSummaryAnalyzer}. Initializes the
     * {@code totalTokensCount} to 0. Assigns a properties object to the
     * instance variable.
     * 
     * @param properties the properties object to read from
     */
    public FileSummaryAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Increments the {@code totalTokensCount}.
     * 
     * @param token the token to analyze (not used)
     */
    public void processToken(String token) {
        totalTokensCount++;
    }

    /**
     * Writes information about the analyzed file to the specified output file.
     * The summary file contains:
     * <ol>
     *     <li>The Analyzer program's name</li>
     *     <li>The program's author</li>
     *     <li>The author's email</li>
     *     <li>The absolute path of the analyzed file</li>
     *     <li>The date and time of analysis</li>
     *     <li>The last modified date of the analyzed file</li>
     *     <li>The size of the analyzed file in bytes</li>
     *     <li>The file URI of the analyzed file</li>
     *     <li>The total number of tokens in the file</li>
     * </ol>
     *
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.summary");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            File inputFile = new File(inputFilePath);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "E MMM d k:m:s z yyyy");

            writer.println("Application: "
                    + properties.getProperty("application.name"));
            writer.println("Author: " + properties.getProperty("author"));
            writer.println("Author email: "
                    + properties.getProperty("author.email.address"));
            writer.println("File: " + inputFile.getAbsolutePath());
            writer.println("Date of analysis: " + formatter.format(
                    calendar.getTime()));
            writer.println("Last Modified:    " + formatter.format(new Date(
                    inputFile.lastModified())));
            writer.println("File Size: " + inputFile.length());
            writer.println("File URI: " + inputFile.toURI());
            writer.println("Total Tokens: " + totalTokensCount);
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.summary"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.summary"));
            exception.printStackTrace();
        }
    }
    
    // Getters and Setters

    /**
     * Gets the total number of tokens.
     * 
     * @return the total number of tokens
     */
    public int getTotalTokensCount() {
        return totalTokensCount;
    }

}
