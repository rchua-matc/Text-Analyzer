package java112.analyzer;

/**
 * Runs the Analyzer program. The program reads a text file and writes five
 * files describing the text:
 * <ul>
 *     <li>A summary file with the input file's information and number of
 *         tokens (words) in the file.</li>
 *     <li>A file with a list of all the unique tokens in the text.</li>
 *     <li>A file with a count of how many times each token appears.</li>
 *     <li>A file containing all the tokens above a specified length.</li>
 *     <li>A file detailing the lexical density of the text.</li>
 *     <li>A file describing the length distribution of tokens in the file.</li>
 *     <li>A file with the locations of predetermined search words.</li>
 * </ul>
 * 
 * @author Ryan Chua
 * @version 3.0
 */
public class Driver {
    /**
     * Instantiates a {@link FileAnalysis} object and calls its 
     * {@code analyze} method.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileAnalysis analyzer = new FileAnalysis();
        analyzer.analyze(args);
    }
}
