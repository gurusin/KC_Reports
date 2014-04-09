package dk.keycore.model.reports;

import dk.keycore.util.DateParser;

import java.util.Date;

/**
 *
 */
public class TrafficByTpCode  extends Report{

    private String tpCode;
    private Date firstEventDate;
    private long messageCount;
    private double receivedFiles;
    private double sentFiles;

    public TrafficByTpCode(final String applicationReference) {
        this.tpCode = applicationReference;
    }

    public void setFirstEventDate(final Date createdDate) {
        // Set this only, if the passed value is smaller than the current value
        if (firstEventDate == null || (createdDate != null && createdDate.before(firstEventDate)))
        {
            this.firstEventDate = createdDate;
        }
    }

    public void incrementMessageCount() {
        messageCount++;
    }

    public void setSendOrReceivedMessageCount(double messageCount) {
        if (tpCode.startsWith("LI"))
        {
             receivedFiles += messageCount;
        }else if (tpCode.startsWith("LO")  )
        {
              sentFiles += messageCount;
        }else if (tpCode.startsWith("LP"))
        {
          receivedFiles += messageCount;
          sentFiles += messageCount;
        }
    }

    public String toReportString()
    {
       final StringBuilder builder = new StringBuilder();
       builder.append(tpCode).append(",");
       builder.append(DateParser.dateToString(firstEventDate)).append(",");
       builder.append(sentFiles).append(",");
       builder.append(receivedFiles).append(",");
       builder.append(messageCount);
       return builder.toString();
    }

    public double getReceivedFiles() {
        return receivedFiles;
    }

    public double getSentFiles() {
        return sentFiles;
    }

    public double getMessageCount() {
        return messageCount;
    }
}
