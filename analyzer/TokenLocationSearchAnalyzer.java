package java112.analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Searches for a list of tokens specified in a file and outputs the position(s)
 * each can be found at. Case sensitive.
 *
 * @author Ryan Chua
 */
public class TokenLocationSearchAnalyzer implements TokenAnalyzer {

    /** The properties object to read from                                    */
    private Properties properties;

    /** A map of search tokens and their positions                            */
    private Map<String, List<Integer>> foundLocations;

    /** The current token position in the file                                */
    private int currentTokenLocation;

    /**
     * Creates a new {@code TokenLocationSearchAnalyzer}. Creates an instance of
     * a {@code TreeMap} and assigns it to {@code foundLocations}. Initializes
     * {@code currentTokenLocation} to zero.
     */
    public TokenLocationSearchAnalyzer() {
        foundLocations = new TreeMap<String, List<Integer>>();
        currentTokenLocation = 0;
    }

    /**
     * Creates a new {@code TokenLocationSearchAnalyzer}. Creates an instance of
     * a {@code TreeMap} and assigns it to {@code foundLocations}. Initializes
     * {@code currentTokenLocation} to zero. Assigns a properties object to the
     * instance variable. Reads a file containing the tokens to search for and
     * stores them in {@code foundLocations}.
     *
     * @param properties the properties object to read from
     */
    public TokenLocationSearchAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        loadSearchWords();
    }

    /**
     * Adds the current position to the list of found locations for a token
     * that matches one of the searched words.
     *
     * @param token the token to process
     */
    public void processToken(String token) {
        currentTokenLocation++;
        if (foundLocations.containsKey(token)) {
            foundLocations.get(token).add(currentTokenLocation);
        }
    }

    /**
     * Creates an output file with each searched word and the positions the
     * word was found.
     *
     * @param inputFilePath the filepath of the analyzed file
     */
    public void generateOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.token.search.locations");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(outputFilePath)))) {
            for (Map.Entry<String, List<Integer>> entry :
                    foundLocations.entrySet()) {
                writer.println(entry.getKey() + " =\n" +
                        writeLocations(entry.getValue()));
                writer.println();
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem writing "
                    + properties.getProperty(
                    "output.file.token.search.locations"));
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem writing "
                    + properties.getProperty(
                    "output.file.token.search.locations"));
            exception.printStackTrace();
        }
    }

    /**
     * Reads the text file of words to search for and adds each one as a key in
     * {@code foundLocations}. Instantiates an empty {@code ArrayList} as the
     * value.
     */
    public void loadSearchWords() {
        String fileName = properties.getProperty("classpath.search.tokens");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream(fileName)))) {
            while (reader.ready()) {
                String fileLine = reader.readLine();
                String searchToken = fileLine.trim();
                if (searchToken.length() != 0) {
                    foundLocations.put(searchToken, new ArrayList<Integer>());
                }
            }
        } catch (IOException iOException) {
            System.out.println("There was a problem loading the search words.");
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem loading the search words.");
            exception.printStackTrace();
        }
    }

    /**
     * Creates a string of the found locations. The resulting output will start
     * with [ and end with ]. Output lines do not exceed 80 characters, and
     * there are no trailing spaces at the end of a line.
     *
     * @param locations a list of found locations of the searched word
     * @return a string of the found locations
     */
    public String writeLocations(List<Integer> locations) {
        if (locations.size() == 0) {
            return "[]";
        }

        String output = "[";
        int lineLength = 1;
        for (Integer location : locations) {
            String position = Integer.toString(location) + ", ";
            if (position.length() - 1 + lineLength > 80) {
                output = output.trim();
                output += "\n";
                lineLength = 0;
            }
            output += position;
            lineLength += position.length();
        }
        output = output.substring(0, output.length() - 2);
        output += "]";
        return output;
    }

    // Get and Set Methods

    /**
     * Gets the map of found locations for each searched string.
     *
     * @return a map of search results
     */
    public Map<String, List<Integer>> getFoundLocations() {
        return foundLocations;
    }
}