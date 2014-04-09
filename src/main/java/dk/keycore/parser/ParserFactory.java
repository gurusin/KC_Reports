package dk.keycore.parser;

/**
 * Created by sudarshana on 3/19/14.
 */
public class ParserFactory {

    public Parser getParser(final String filePath)
    {
        if (filePath.endsWith(".csv"))
        {
            return new CSVParser(filePath);
        }
        System.out.print("Unsupported file type  and will be ignored" + filePath);
        return null;
    }
}
