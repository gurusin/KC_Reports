package dk.keycore.model;

import com.googlecode.jcsv.annotations.MapToColumn;
import dk.keycore.model.reports.RemoteFileNameMapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a data row in the source message.
 * Will be used to collect the data from input file
 */
public class SourceMessage
{

  @MapToColumn(column = 0)
  private String gsxRefNo;

  @MapToColumn(column = 1)
  private String partnerName;

  @MapToColumn(column = 2)
  private String sender;

  @MapToColumn(column = 3)
  private String receiver;

  @MapToColumn(column = 4)
  private String documentType;

  @MapToColumn(column = 5)
  private String primaryKey;

  @MapToColumn(column = 6)
  private String applicationReference;

  @MapToColumn(column = 7)
  private String createdDate;

  @MapToColumn(column = 8)
  private String acceptedDate;

  @MapToColumn(column = 9)
  private String dateExtracted;

  @MapToColumn(column = 10)
  private String commTypes;

  @MapToColumn(column = 11)
  private String direction;

  @MapToColumn(column = 12)
  private String fileErrorCount;

  @MapToColumn(column = 13)
  private String fileReceiver;

  @MapToColumn(column = 14)
  private String fileSize;

  @MapToColumn(column = 15)
  private String translationSessionNo;

  @MapToColumn(column = 16)
  private String key1;

  @MapToColumn(column = 17)
  private String key2;

  @MapToColumn(column = 18)
  private String key3;

  @MapToColumn(column = 19)
  private String key4;

  @MapToColumn(column = 20)
  private String key5;

  @MapToColumn(column = 21)
  private String key6;

  @MapToColumn(column = 22)
  private String key7;

  @MapToColumn(column = 23)
  private String key8;

  @MapToColumn(column = 24)
  private String key9;

  @MapToColumn(column = 25)
  private String key10;

  @MapToColumn(column = 26)
  private String key11;

  @MapToColumn(column = 27)
  private String key12;

  @MapToColumn(column = 28)
  private String key13;

  private String outputFileName ="";

  private final static Logger  logger = Logger.getLogger(SourceMessage.class);

  public void calculateOutPutFile(final RemoteFileNameMapper mapper, final List<String> keys)
  {
    try
    {
      final String mapping = mapper.getMapping(applicationReference);
      String[] tmp = mapping.split("\\*");
      String start = tmp[0];
      String end = tmp[1];
      for (final String key : keys)
      {
         if (applicationReference.startsWith(start) && applicationReference.startsWith(end))
         {
            outputFileName = key;
            break;
         }
      }
    }
    catch (Exception e)
    {
       logger.error(" Error in calculating the Mapping for [" + applicationReference+"]");
       logger.error(e.getMessage());
    }

  }

  public String getOutputFileName()
  {
    return outputFileName;
  }

  public String getGsxRefNo()
  {
    return gsxRefNo;
  }

  public void setGsxRefNo(String gsxRefNo)
  {
    this.gsxRefNo = gsxRefNo;
  }

  public String getPartnerName()
  {
    return partnerName;
  }

  public void setPartnerName(String partnerName)
  {
    this.partnerName = partnerName;
  }

  public String getSender()
  {
    return sender;
  }

  public void setSender(String sender)
  {
    this.sender = sender;
  }

  public String getReceiver()
  {
    return receiver;
  }

  public void setReceiver(String receiver)
  {
    this.receiver = receiver;
  }

  public String getDocumentType()
  {
    return documentType;
  }

  public void setDocumentType(String documentType)
  {
    this.documentType = documentType;
  }

  public String getPrimaryKey()
  {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey)
  {
    this.primaryKey = primaryKey;
  }

  public String getApplicationReference()
  {
    return applicationReference;
  }

  public void setApplicationReference(String applicationReference)
  {
    this.applicationReference = applicationReference;
  }

  public String getCreatedDate()
  {
    return createdDate;
  }

  public void setCreatedDate(String createdDate)
  {
    this.createdDate = createdDate;
  }

  public String getAcceptedDate()
  {
    return acceptedDate;
  }

  public void setAcceptedDate(String acceptedDate)
  {
    this.acceptedDate = acceptedDate;
  }

  public String getDateExtracted()
  {
    return dateExtracted;
  }

  public void setDateExtracted(String dateExtracted)
  {
    this.dateExtracted = dateExtracted;
  }

  public String getCommTypes()
  {
    return commTypes;
  }

  public void setCommTypes(String commTypes)
  {
    this.commTypes = commTypes;
  }

  public String getDirection()
  {
    return direction;
  }

  public void setDirection(String direction)
  {
    this.direction = direction;
  }

  public String getFileErrorCount()
  {
    return fileErrorCount;
  }

  public void setFileErrorCount(String fileErrorCount)
  {
    this.fileErrorCount = fileErrorCount;
  }

  public String getFileReceiver()
  {
    return fileReceiver;
  }

  public void setFileReceiver(String fileReceiver)
  {
    this.fileReceiver = fileReceiver;
  }

  public String getFileSize()
  {
    return fileSize;
  }

  public void setFileSize(String fileSize)
  {
    this.fileSize = fileSize;
  }

  public String getTranslationSessionNo()
  {
    return translationSessionNo;
  }

  public void setTranslationSessionNo(String translationSessionNo)
  {
    this.translationSessionNo = translationSessionNo;
  }

  public String getKey1()
  {
    return key1;
  }

  public void setKey1(String key1)
  {
    this.key1 = key1;
  }

  public String getKey3()
  {
    return key3;
  }

  public void setKey3(String key3)
  {
    this.key3 = key3;
  }

  public String getKey2()
  {
    return key2;
  }

  public void setKey2(String key2)
  {
    this.key2 = key2;
  }

  public void setOutputFileName(String outputFileName)
  {
    this.outputFileName = outputFileName;
  }
}

