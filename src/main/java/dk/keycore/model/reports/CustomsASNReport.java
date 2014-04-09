package dk.keycore.model.reports;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Created by sudarshana on 3/17/14.
 */
public class CustomsASNReport extends Report {

    private String remoteFn;
    private String blNumber;
    private String sentDate;
    private String tpCode;

    private static final Logger logger = Logger.getLogger(CustomsASNReport.class);

    @Override
    public String toReportString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(remoteFn).append(",");
        builder.append(blNumber).append(",");
        builder.append(sentDate);
        return builder.toString();
    }

    public void setRemoteFn(String remoteFn) {
        this.remoteFn = remoteFn;
    }

    public void setBlNumber(String blNumber) {
        this.blNumber = blNumber;
    }

    public void setSentDate(String sentDate) {
        try {
            this.sentDate = sentDate.replaceAll("\\,","");
        } catch (Exception e) {
            logger.error("Error parsing date " + e.getMessage());
        }
    }

  public void setTpCode(String tpCode)
  {
    this.tpCode = tpCode;
  }
}
