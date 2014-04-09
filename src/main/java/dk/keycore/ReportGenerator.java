package dk.keycore;

import dk.keycore.model.SourceMessage;
import dk.keycore.model.reports.*;
import dk.keycore.parser.Parser;
import dk.keycore.parser.ParserFactory;
import dk.keycore.util.DateParser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.*;

/**
 * This is the startup class for the  
 */
public class ReportGenerator {

    private static String OUT_PATH="";
    private static String IN_PATH="";


    private final Logger logger = Logger.getLogger(ReportGenerator.class);

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");
        final ReportGenerator reportEngine = new ReportGenerator();
        reportEngine.processReport();
    }

    private void processReport()
    {
        loadProperties();
        final List<SourceMessage> list = new ArrayList<SourceMessage>();
        final File file = new File(IN_PATH);
        if (file.isDirectory())
        {
          // Read the files
           for (File f : file.listFiles())
           {
             list.addAll(readMessages(f.getAbsolutePath()));
           }
        }
        creteReports(list);
    }

    private List<SourceMessage> readMessages(final String filePath)
    {
        logger.debug("Starting processing file[" +filePath+"]");
        final ParserFactory factory = new ParserFactory();
        final Parser p = factory.getParser(filePath);
        try {
            return p.readMessages();
        } catch (Exception e) {

            // Error in parsing.
            logger.debug("Ignoring file[" +filePath+"]");
        }
        return new ArrayList<SourceMessage>();
    }

    private void loadProperties()
    {
        try {
            final Properties prop = new Properties();
            FileInputStream fi = new FileInputStream("Report.properties");
            prop.load(fi);
            fi.close();

            IN_PATH = prop.getProperty("Input.path");
            OUT_PATH = prop.getProperty("Output.path");

            // Check the report path
            File f = new File(OUT_PATH);
            if (!f.exists())
            {
                f.mkdirs();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void creteReports(final List<SourceMessage> sourceMessageList)
    {
        final Map<String,Report> reportMap = new HashMap<String,Report>();
        final List<Report> comASNList = new ArrayList<Report>();
        final List<Report> customsASNList = new ArrayList<Report>();
        final List<Report> remoteFileReportList = new ArrayList<Report>();
        final List<Report> missingRemoteFn = new ArrayList<Report>();

        for (final SourceMessage sourceMessage:sourceMessageList)
        {
            addToCOMASNReport(comASNList,sourceMessage,missingRemoteFn);
            addToCustomsASNReport(customsASNList,sourceMessage,missingRemoteFn);
            addToRemoteFileReport(remoteFileReportList,sourceMessage,missingRemoteFn);
            addToTrafficByTpCode(reportMap,sourceMessage);
        }


        // Adding the SummaryLine
        double count =0;
        double sum_received=0;
        double sum_sent=0;
        for (final Report obj : reportMap.values())
        {
            final TrafficByTpCode tp = (TrafficByTpCode)obj;
            count = count + tp.getMessageCount();
            sum_received = sum_received+ tp.getReceivedFiles();
            sum_sent = sum_sent + tp.getSentFiles();
        }
        final SummaryLine summary = new SummaryLine(",,"+sum_sent+","+sum_received+","+count);
        final Collection tpList = new ArrayList(reportMap.values());
        tpList.add(summary);

        // Write the information in the map to a CSV file
        writeToFile(tpList,"Traffic_By_TP_Code.csv",getTrafficByTPCodeHeader());

        // Process other files
        writeToFile(comASNList, "COM_ASN_Report.csv",getComASNHeader());
        writeToFile(customsASNList,"Customs_ASN_Report.csv",getCustomsASNHeader());
        writeToFile(remoteFileReportList,"Remote_File_Report.csv",getRemoteFileHeader());
        writeToFile(missingRemoteFn,"Missing_Remote_FN.csv",getMissingRemoteFNHeader());
    }

  private static String getMissingRemoteFNHeader()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("Report Name,").append("TPCode,").append("MBid,");
    builder.append("Date");
    return builder.toString();
  }

  private static void addToTrafficByTpCode(final Map<String,Report> reportMap,final SourceMessage message)
    {
        // Calculate the traffic by TPCode report.

        TrafficByTpCode reportLine = null;
        String applicationReference = message.getApplicationReference();
        if (!applicationReference.startsWith("LA"))
        {
            if (reportMap.containsKey(applicationReference))
            {
                reportLine = (TrafficByTpCode)reportMap.get(applicationReference);
            }else
            {
                reportLine = new TrafficByTpCode(applicationReference);
                reportMap.put(applicationReference,reportLine);
            }

            // Processing the report
            final Date eventDate = DateParser.parse(message.getCreatedDate());
            reportLine.setFirstEventDate(eventDate);
            reportLine.incrementMessageCount();
            double messageCount = 0;
            try
            {
                messageCount = Double.parseDouble(message.getFileSize());
            }catch(Exception e)
            {
                // Log the error as to incorrect value;
            }
            reportLine.setSendOrReceivedMessageCount(messageCount);
        }
    }

    private static void addToRemoteFileReport(final List<Report> dataList, final SourceMessage message,final List<Report> missingFn)
    {
        final CheckOfReportFileName rpt = new CheckOfReportFileName();
        rpt.setTpCode(message.getApplicationReference());
        rpt.setMbid(message.getGsxRefNo());
        rpt.setFirstEventDate(message.getAcceptedDate());
        rpt.setRemoteFn(message.getOutputFileName());
        rpt.setRecipient(message.getReceiver());
        rpt.setLastEventDate(message.getDateExtracted());
        dataList.add(rpt);
      checkForMissingRemoteFn("Remote File",message,missingFn);
    }

    private static void addToCOMASNReport(final List<Report> dataList, final SourceMessage message, final List<Report> missingFn)
    {
        if (message.getApplicationReference().equals("LO_ADIGTN_COM"))
        {
            final ComASNReport rpt = new ComASNReport();
            rpt.setGxs_ref_no(message.getGsxRefNo());
            rpt.setEventDate(message.getDateExtracted());
            rpt.setRemote_fn(message.getOutputFileName());
            rpt.setBLNumber(message.getPrimaryKey());
            rpt.setTpCode(message.getApplicationReference());
            dataList.add(rpt);
            checkForMissingRemoteFn("COM ASN",message,missingFn);
        }

    }

    private static void checkForMissingRemoteFn(final String reportName,final SourceMessage message, final List<Report> missingFn)
    {
       if (message.getOutputFileName().length()==0)
       {
          final MissingRemoteFN obj = new MissingRemoteFN();
          obj.setReportName(reportName);
          obj.setTpCode(message.getApplicationReference());
          obj.setMbid(message.getGsxRefNo());
          obj.setDate(message.getCreatedDate());
          missingFn.add(obj);
       }
    }

    private static void addToCustomsASNReport(final List<Report> dataList, final SourceMessage message,final List<Report> missingFn)
    {
        if (message.getApplicationReference().equals("LO_ADIGT_CUSTF"))
        {
            final CustomsASNReport rpt = new CustomsASNReport();
            rpt.setRemoteFn(message.getOutputFileName());
            rpt.setSentDate(message.getDateExtracted());
            rpt.setBlNumber(message.getPrimaryKey());
            rpt.setTpCode(message.getApplicationReference());
            dataList.add(rpt);
            checkForMissingRemoteFn("CUSTOMS ASN",message,missingFn);
        }

    }

    private static void writeToFile(final Collection<Report> reports, final String filePath, final String header)
    {
        try {
            final FileWriter writer = new FileWriter(OUT_PATH+"//"+filePath);
            writer.append(header);
            writer.append("\n");
            for (final Report report:reports)
            {
                writer.append(report.toReportString());
                writer.append("\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getComASNHeader()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("Mbid").append(",").append("LastEventDate").append(",");
      builder.append("LastEventTime").append(",").append("Remote Fn").append(",").append("B/L Numbers");
      return builder.toString();
    }

    private static String getCustomsASNHeader()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("Remote Fn").append(",").append("B/L Numbers").append(",");
      builder.append("Send Date");
      return builder.toString();
    }

    private static String getRemoteFileHeader()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("Env").append(",").append("TP").append(",").append("Mbid").append(",");
      builder.append("FirstEventDate").append(",").append("FirstEventTime").append(",");
      builder.append("Remote Fn").append(",").append("Recipient").append(",");
      builder.append("LastEventDate").append(",").append("LastEventTime");
      return builder.toString();
    }


  private static String getTrafficByTPCodeHeader()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("TP Code,");
    builder.append("Reporting Date & Time").append(",");
    builder.append("Size of Sent Files,").append("Size of Received Files");
    builder.append(",Number of Files");
    return builder.toString();
  }

}
