package dk.keycore.parser;

import com.googlecode.jcsv.reader.CSVEntryParser;
import dk.keycore.model.SourceMessage;
import java.util.List;

/**
 * Created by sudarshana on 3/19/14.
 */
public abstract class Parser  implements CSVEntryParser<SourceMessage>{

    private final String filePath;

    protected Parser(final String filePath)
    {
        this.filePath = filePath;
    }

    public abstract List<SourceMessage> readMessages();

    protected String getFileName() {
        return filePath;
    }


}
