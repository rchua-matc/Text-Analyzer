package java112.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java112.utilities.PropertiesLoader;

/**
 * Unit testing for {@link LexicalDensityAnalyzer}.
 * 
 * @author Ryan Chua
 */
public class LexicalDensityAnalyzerTest implements PropertiesLoader {

    /** The location of the properties file to read                           */
    private final static String PROPERTIES_FILE = "/analyzer.properties";

    /** The properties file to read from                                      */
    private Properties properties;

    /**
     * Creates a {@code LexicalDensityAnalyzerTest} object and loads the
     * analyzer's properties file.
     */
    public LexicalDensityAnalyzerTest() {
        properties = loadProperties(PROPERTIES_FILE);
    }

    /**
     * Runs tests on methods used in {@code LexicalDensityAnalyzer}.
     * 
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        LexicalDensityAnalyzerTest test = new LexicalDensityAnalyzerTest();

        boolean loadTest = test.testLoadNonLexicalWords();
        boolean calculateTest = test.testCalculateLexicalDensity();
        boolean processTest = test.testProcessToken();
        boolean outputTestFailed = test.testGenerateOutputFile();
        boolean johnSmithFailed = test.testJohnSmith();
        System.out.println();

        if (loadTest) {
            System.out.println("loadNonLexicalWords failed testing");
        }

        if (calculateTest) {
            System.out.println("calculateLexicalDensity failed testing");
        }

        if (processTest) {
            System.out.println("processToken failed testing");
        }

        if (outputTestFailed) {
            System.out.println("generateOutputFile failed testing");
        }

        if (johnSmithFailed) {
            System.out.println("The John Smith test failed");
        }

    }

    /**
     * Tests the {@code loadNonLexicalWords} method.
     * 
     * @return true if the test fails, else false
     */
    public boolean testLoadNonLexicalWords() {
        int successes = 0;
        int failures = 0;

        // Create test objects
        LexicalDensityAnalyzer analyzer = new LexicalDensityAnalyzer(
                this.properties);

        Set<String> words = analyzer.getNonLexicalWords();

        // Create expected results
        int expected = 277;

        // Generate actual results
        int actual = words.size();

        // Compare expected v. actual and display results
        System.out.println();
        System.out.println("Testing loadNonLexicalWords:");

        if (expected == actual) {
            System.out.println("Test 1: Passed");
            successes++;
        } else {
            System.out.println("Test 1: Failed");
            System.out.println("\tActual: " + actual);
            System.out.println("\tExpected: " + expected);
            failures++;
        }

        System.out.println();
        System.out.println("Successful tests: " + successes + "/" 
                + (successes + failures));
        System.out.println("Failed tests: " + failures + "/" 
                + (successes + failures));

        if (failures > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Tests the {@code calculateLexicalDensity} method.
     * 
     * @return true if any test fails, else false
     */
    public boolean testCalculateLexicalDensity() {
        int successes = 0;
        int failures = 0;

        // Create test objects
        LexicalDensityAnalyzer[] analyzers = new LexicalDensityAnalyzer[5];
        int[] lexicalValues = {5, 5, 40, 35, 9};
        int[] totalValues = {10, 23, 43, 54, 9};

        for (int i = 0; i < 5; i++) {
            analyzers[i] = new LexicalDensityAnalyzer(this.properties);
            analyzers[i].setLexicalTokens(lexicalValues[i]);
            analyzers[i].setTotalTokens(totalValues[i]);
        }

        // Create expected results
        float[] expected = {0.5f, 0.2173913f, 0.9302326f, 0.6481481f, 1.0f};

        // Generate actual results
        float[] actual = new float[5];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = analyzers[i].calculateLexicalDensity();
        }

        // Compare expected v. actual and display results
        System.out.println();
        System.out.println("Testing calculateLexicalDensity");

        for (int i = 0; i < expected.length; i++) {
            if (expected[i] == actual[i]) {
                System.out.println("Test " + (i + 1) + ": Success");
                successes++;
            } else {
                System.out.println("Test " + (i + 1) + ": Failed");
                System.out.println("    Expected: \"" + expected[i] + "\"");
                System.out.println("    Actual: \"" + actual[i] + "\"");
                failures++;
            }
        }

        System.out.println();
        System.out.println("Successful tests: " + successes + "/" 
                + (successes + failures));
        System.out.println("Failed tests: " + failures + "/" 
                + (successes + failures));

        if (failures > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests the {@code processToken} method.
     * 
     * @return true if any test fails, else false
     */
    public boolean testProcessToken() {
        int successes = 0;
        int failures = 0;

        // Create test objects
        LexicalDensityAnalyzer[] analyzers = new LexicalDensityAnalyzer[5];

        for (int i = 0; i < 5; i++) {
            analyzers[i] = new LexicalDensityAnalyzer(this.properties);
        }

        String[] testWords = {"dog", "cat", "apple", "tHis", "the"};

        // Create expected results
        int[] expectedLexical = {1, 1, 1, 0, 0};
        int[] expectedTotal = {1, 1, 1, 1, 1};

        // Generate actual results
        int[] actualLexical = new int[5];
        int[] actualTotal = new int[5];

        for (int i = 0; i < actualLexical.length; i++) {
            analyzers[i].processToken(testWords[i]);
            actualLexical[i] = analyzers[i].getLexicalTokens();
            actualTotal[i] = analyzers[i].getTotalTokens();
        }

        // Compare expected v. actual and display results
        System.out.println();
        System.out.println("Testing processToken");

        for (int i = 0; i < expectedLexical.length; i++) {
            if (expectedLexical[i] == actualLexical[i] &&
                    expectedTotal[i] == actualTotal[i]) {
                System.out.println("Test " + (i + 1) + ": Success");
                successes++;
            } else {
                System.out.println("Test " + (i + 1) + ": Failed");
                System.out.println("    Expected Lexical: \"" 
                        + expectedLexical[i] + "\"");
                System.out.println("    Actual Lexical: \"" 
                        + actualLexical[i] + "\"");
                System.out.println("    Expected Total: \"" 
                        + expectedTotal[i] + "\"");
                System.out.println("    Actual Total: \"" 
                        + actualTotal[i] + "\"");
                failures++;
            }
        }

        System.out.println();
        System.out.println("Successful tests: " + successes + "/" 
                + (successes + failures));
        System.out.println("Failed tests: " + failures + "/" 
                + (successes + failures));

        if (failures > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests the {@code generateOutputFile} method.
     * 
     * @return true if any test fails, else false
     */
    public boolean testGenerateOutputFile() {
        int successes = 0;
        int failures = 0;

        // Create test objects
        LexicalDensityAnalyzer analyzer = new LexicalDensityAnalyzer(
                this.properties);
        analyzer.setLexicalTokens(50);
        analyzer.setTotalTokens(76);
        analyzer.generateOutputFile("test.txt");

        String outputFilePath = properties.getProperty("output.directory")
                + properties.getProperty("output.file.lexical.density");

        // Create expected results
        String[] expected = new String[4];
        expected[0] = "File: /home/student/GitHubRepos/projects/test.txt";
        expected[1] = "Lexical Tokens:  50";
        expected[2] = "Total Tokens:    76";
        expected[3] = "Lexical Density: 65.79%";

        // Gather actual results
        String[] actual = new String[4];

        try (BufferedReader reader = new BufferedReader(
                new FileReader(outputFilePath))) {
            while (reader.ready()) {
                for (int i = 0; i < actual.length; i++) {
                    actual[i] = reader.readLine();
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file \"" + outputFilePath 
                    + "\" was not found.");
            fileNotFoundException.printStackTrace();
        } catch (IOException iOException) {
            System.out.println("There was a problem reading the file");
            iOException.printStackTrace();
        } catch (Exception exception) {
            System.out.println("There was a problem reading the file");
            exception.printStackTrace();
        }

        // Test if file exists
        System.out.println();
        System.out.println("Testing generateOutputFile");
        File outputFile = new File(outputFilePath);

        if (outputFile.exists()) {
            System.out.println("Output file exists: Passed");
            successes++;
        } else {
            System.out.println("Output file exists: Failed");
            failures++;
            return false;
        }

        // Compare expected v. actual and display results
        for (int i = 0; i < expected.length; i++) {
            if (expected[i].equals(actual[i])) {
                System.out.println("Line " + (i + 1) + ": Success");
                successes++;
            } else {
                System.out.println("Line " + (i + 1) + ": Failed");
                System.out.println("    Expected: \"" + expected[i] + "\"");
                System.out.println("    Actual:   \"" + actual[i] + "\"");
                failures++;
            }
        }

        System.out.println();
        System.out.println("Successful tests: " + successes + "/" 
                + (successes + failures));
        System.out.println("Failed tests: " + failures + "/" 
                + (successes + failures));

        if (failures > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Tests if the phrase "john smith intensely loves going to the huge cinema
     * everyday" returns an 80% lexical density.
     * 
     * @return true if the test fails, else false
     */
    public boolean testJohnSmith() {
        int failures = 0;

        // Create test objects
        LexicalDensityAnalyzer analyzer = new LexicalDensityAnalyzer(
                this.properties);
        
        String[] testSentence = {"john", "smith", "intensely", "loves", "going",
                "to", "the", "huge", "cinema", "everyday"};
        
        // Create expected results
        float expected = 0.8f;

        // Calculate actual results
        for (String word : testSentence) {
            analyzer.processToken(word);
        }

        float actual = analyzer.calculateLexicalDensity();

        // Compare expected v. actual and display results
        System.out.println();
        System.out.println("The 'John Smith' Test");

        if (expected == actual) {
            System.out.println("Lexical Density Check: Success");
        } else {
            System.out.println("Lexical Density Check: Failed");
            System.out.println("    Expected: " + expected);
            System.out.println("    Actual: " + actual);
            failures++;
        }

        if (failures > 0) {
            return true;
        } else {
            return false;
        }
    }

}
