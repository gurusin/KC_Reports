package dk.keycore.parser;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import dk.keycore.model.SourceMessage;
import dk.keycore.model.reports.RemoteFileNameMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CSVParser extends Parser
{

  private static final Logger logger = Logger.getLogger(CSVParser.class);

  private static RemoteFileNameMapper mapper = new RemoteFileNameMapper();

  public CSVParser(final String filePath)
  {
    super(filePath);
  }

  @Override
  public List<SourceMessage> readMessages()
  {
    List<SourceMessage> messageList = null;
    try
    {
      final Reader r = new FileReader(getFileName());
      CSVReader<SourceMessage> csvPersonReader = new CSVReaderBuilder<SourceMessage>(r).strategy(CSVStrategy.UK_DEFAULT)
          .entryParser(this).build();
      messageList = csvPersonReader.readAll();
      messageList.remove(0); // Remove the header
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(getFileName() + " is corrupted or not found and will be ignored for input");
      messageList = new ArrayList<SourceMessage>();
    }
    return messageList;
  }

  @Override
  public SourceMessage parseEntry(String... data)
  {
    final SourceMessage message = new SourceMessage();
    int index = 0;
    message.setGsxRefNo(data[index++]);
    message.setPartnerName(data[index++]);
    message.setSender(data[index++]);
    message.setReceiver(data[index++]);
    message.setDocumentType(data[index++]);
    message.setPrimaryKey(data[index++]);
    message.setApplicationReference(data[index++]);
    message.setCreatedDate(data[index++]);
    message.setAcceptedDate(data[index++]);
    message.setDateExtracted(data[index++]);
    message.setCommTypes(data[index++]);
    message.setDirection(data[index++]);
    message.setFileErrorCount(data[index++]);
    message.setFileReceiver(data[index++]);
    message.setFileSize(data[index++]);
    message.setTranslationSessionNo(data[index++]);

    final String applicationReference = message.getApplicationReference();
    final String mapping = mapper.getMapping(applicationReference);
    if (mapping != null)
    {
      String[] tmp = mapping.split("\\*");
      String start = tmp[0];
      String end = tmp[1];
      if (start.length()==0)
      {
        message.setOutputFileName(matchEndOnly(end,data,index,message.getApplicationReference()));
      }
      else
      {
        message.setOutputFileName(matchStartAndEnd(index, applicationReference, start, end, data));
      }

    }
    return message;
  }

  private String matchStartAndEnd(int index, String applicationReference, String start, String end,
                                String[] data)
  {
    String retVal = "";
    for (; index < data.length; index++)
    {
      try
      {
        final String val = data[index].toUpperCase();
        if (val.startsWith(start) && val.endsWith(end))
        {
          retVal = data[index];
          break;
        }
      }
      catch (Exception e)
      {
        logger.error(" Error in calculating the Mapping for [" + applicationReference + "]");
        logger.error(e.getMessage());
      }
    }
    return retVal;
  }

  private String matchEndOnly(final String endStr, String[] data, int startIndex, final String applicationReference)
  {
    String returnVal ="";
    for (; startIndex < data.length; startIndex++)
    {
      try
      {
        final String val = data[startIndex].toUpperCase();
        if (val.endsWith(endStr))
        {
          returnVal = data[startIndex];
          break;
        }
      }
      catch (Exception e)
      {
        logger.error(" Error in calculating the Mapping for [" + applicationReference + "]");
        logger.error(e.getMessage());
      }
    }
    return returnVal;
  }
}
