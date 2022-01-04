package java112.analyzer;

import java.util.Properties;
import java.util.Set;
import java.util.HashSet;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Calculates the lexical density of the analyzed file and outputs the results
 * to a file.
 * 
 * Lexical density is calculated as the percentage of total words which are 
 * "lexical words", or words with content.
 * 
 * @author Ryan Chua
 */
public class LexicalDensityAnalyzer implements TokenAnalyzer {

    /** A count of all lexical tokens in the file                             */
    private int lexicalTokens;

    /** A count of all the tokens in the file                                 */
    private int totalTokens;

    /** The properties file to read from                                      */
    private Properties properties;

    /** A set of non-lexical words to check tokens against                    */
    private Set<String> nonLexicalWords;

    /**
     * Creates a new {@code LexicalDensityAnalyzer}. Initializes {@code
     * lexicalTokens} and {@code totalTokens} to 0. Creates an instance of a
     * {@code HashSet} and assigns it to {@code nonLexicalWords}.
     */
    public LexicalDensityAnalyzer() {
        lexicalTokens = 0;
        totalTokens = 0;
        nonLexicalWords = new HashSet<String>();
    }

    /**
     * Creates a new {@code LexicalDensityAnalyzer}. Initializes {@code
     * lexicalTokens} and {@code totalTokens} to 0. Assigns a properties object
     * to the instance variable. Loads the list of non-lexical words.
     * 
     * @param properties the properties object to read from
     */
    public LexicalDensityAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        loadNonLexicalWords();
    }

    /**
     * Increments {@code lexicalTokens} and {@code totalTokens} according to
     * the token evaluated. {@code lexicalTokens} is only incremented when the
     * parameter token is not in the set of non-lexical words.
     * 
     * @param token the token to evaluate lexical status
     */
    public void processToken(String token) {
        if (!nonLexicalWords.contains(token.toLowerCase())) {
            lexicalTokens++;
        }
        totalTokens++;
    }

    /**
     * Creates an output file detailing the file analyzed, the number of
     * lexical tokens in the file, the total number of tokens in the file and
     * the lexical density of the file.
     * 
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.lexical.density");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            File inputFile = new File(inputFilePath);
            DecimalFormat formatter = new DecimalFormat("###.00%");
            writer.println("File: " + inputFile.getAbsolutePath());
            writer.println("Lexical Tokens:  " + lexicalTokens);
            writer.println("Total Tokens:    " + totalTokens);
            writer.println("Lexical Density: "
                    + formatter.format(calculateLexicalDensity()));
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.lexical.density"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty("output.file.lexical.density"));
            exception.printStackTrace();
        }
    }

    /**
     * Reads the text file of non-lexical words and adds each word to
     * {@code nonLexicalWords}.
     */
    public void loadNonLexicalWords() {
        String fileName = properties.getProperty("non.lexical.words.file");

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {
            while (reader.ready()) {
                nonLexicalWords.add(reader.readLine());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file \"" + fileName + "\" was not found.");
            fileNotFoundException.printStackTrace();
        } catch (IOException iOException) {
            System.out.println("There was a problem loading the non-lexical "
                    + "words");
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem loading the non-lexical "
                    + "words");
            exception.printStackTrace();
        }
    }

    /**
     * Calculates the lexical density of the analyzed file.
     * 
     * @return the lexical density in decimal format
     */
    public float calculateLexicalDensity() {
        return (float) lexicalTokens / totalTokens;
    }

    // Getters and Setters - For testing only
    
    /**
     * Gets the set of non-lexical words loaded from a text file.
     * 
     * @return the non-lexical words used to calculate lexical density
     */
    public Set<String> getNonLexicalWords() {
        return nonLexicalWords;
    }

    /**
     * Sets the number of lexical tokens in the file.
     * 
     * @param lexicalTokens the number of lexical tokens in the file
     */
    public void setLexicalTokens(int lexicalTokens) {
        this.lexicalTokens = lexicalTokens;
    }

    /**
     * Gets the number of lexical tokens in the file.
     * 
     * @return the number of lexical tokens in the file
     */
    public int getLexicalTokens() {
        return lexicalTokens;
    }

    /**
     * Sets the total number of tokens in the file.
     * 
     * @param totalTokens the total number of tokens in the file
     */
    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    /**
     * Gets the total number of tokens in the file.
     * 
     * @return the total number of tokens in the file
     */
    public int getTotalTokens() {
        return totalTokens;
    }
}