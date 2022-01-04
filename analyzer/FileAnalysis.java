package java112.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java112.utilities.PropertiesLoader;

/**
 * Reads a file and writes files describing the input text. The input file
 * is split into tokens which are counted or stored by {@link TokenAnalyzer}
 * instances.
 *
 * The program requires one command line argument: the location of a text file
 * to analyze.
 *
 * @author Ryan Chua
 */
public class FileAnalysis implements PropertiesLoader {

    /** The required number of command line arguments to run the Analyzer.    */
    private final static int ARGUMENTS_NEEDED = 2;

    /** A collection of analyzers to process the input file.                  */
    private List<TokenAnalyzer> analyzers;

    /**
     * Reads the input file and calls the appropriate methods to process and
     * output the descriptive files. This is the main processing file of the
     * application.
     *
     * @param args the command line arguments: the input file to analyze
     *             and the location of the properties file (passed by script)
     */
    public void analyze(String[] args) {

        // Check command line arguments
        if (args.length != ARGUMENTS_NEEDED) {
            System.out.println("Please enter one argument on the command line, "
                    + "the text file to analyze");
            return;
        }

        // Setup analyzers
        Properties properties = loadProperties(args[1]);

        analyzers = new ArrayList<TokenAnalyzer>();
        createAnalyzers(properties);

        // Analyze text file
        openInputFile(args[0]);
        writeOutputFiles(args[0]);
    }

    /**
     * Creates the {@code TokenAnalyzer} objects used for file analysis.
     *
     * @param properties the properties object to read from
     */
    public void createAnalyzers(Properties properties) {
        analyzers.add(new FileSummaryAnalyzer(properties));
        analyzers.add(new DistinctTokensAnalyzer(properties));
        analyzers.add(new DistinctTokenCountsAnalyzer(properties));
        analyzers.add(new LargestTokensAnalyzer(properties));
        analyzers.add(new LexicalDensityAnalyzer(properties));
        analyzers.add(new TokenLengthsAnalyzer(properties));
        analyzers.add(new TokenLocationSearchAnalyzer(properties));
    }

    /**
     * Reads the input file and processes its contents. Calls the appropriate
     * methods to split each line of text into individual tokens and process
     * each one.
     *
     * @param fileName the name of the input file
     */
    public void openInputFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {
            while (reader.ready()) {
                String fileLine = reader.readLine();
                String[] fileTokens = parseFileLine(fileLine);
                analyzeTokens(fileTokens);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file \"" + fileName + "\" was not found.");
            fileNotFoundException.printStackTrace();
        } catch (IOException iOException) {
            System.out.println("There was a problem reading the file");
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem reading the file");
            exception.printStackTrace();
        }
    }

    /**
     * Splits a line of a text file into individual tokens. The delimiter
     * splits the string at each non-word character.
     *
     * @param line a single line of a text file to split into tokens
     * @return an array of tokens in the specified line
     */
    public String[] parseFileLine(String line) {
        return line.split("\\W");
    }

    /**
     * Passes each non-empty token to the {@code evaluateToken} method for
     * processing by each analyzer.
     *
     * @param tokens an array of tokens from a line of text
     */
    public void analyzeTokens(String[] tokens) {
        for (String token : tokens) {
            if (token.length() != 0) {
                evaluateToken(token);
            }
        }
    }

    /**
     * Passes the token to each analyzer's {@code processToken} method.
     *
     * @param token the token for each analyzer to process
     */
    public void evaluateToken(String token) {
        for (TokenAnalyzer analyzer : analyzers) {
            analyzer.processToken(token);
        }
    }

    /**
     * Calls the {@code generateOutputFile} method of each analyzer to write
     * the output files.
     *
     * @param inputFilePath the filepath of the analyzed file
     */
    public void writeOutputFiles(String inputFilePath) {
        for (TokenAnalyzer analyzer : analyzers) {
            analyzer.generateOutputFile(inputFilePath);
        }
    }

}