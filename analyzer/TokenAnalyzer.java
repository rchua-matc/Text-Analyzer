package java112.analyzer;

/**
 * Interface containing methods for processing tokens and writing output data.
 * 
 * @author Ryan Chua
 */
public interface TokenAnalyzer {
    /**
     * Processes a token. Takes a token from the analyzed file and performs
     * the necessary logic to gather data from the input file.
     * 
     * @param token the token to process
     */
    public abstract void processToken(String token);

    /**
     * Writes a file containing information about the file analyzed.
     * 
     * @param inputFilePath the filepath of the analyzed file
     */
    public abstract void generateOutputFile(String inputFilePath);

}
