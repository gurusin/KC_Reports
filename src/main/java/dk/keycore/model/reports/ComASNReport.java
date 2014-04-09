package dk.keycore.model.reports;


import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class ComASNReport extends Report {


    private String gxs_ref_no;
    private String lastEventDate;
    private String lastEventTime;
    private String remote_fn;
    private String blNumber;
    private String tpCode;

    final Logger logger = Logger.getLogger(ComASNReport.class);
    public void setEventDate(String dateValue)
    {
        try {
            dateValue = dateValue.replaceAll("\\,","");
            final String[] tmp = dateValue.split("\\|");
            lastEventDate = tmp[0];
            lastEventTime = tmp[1];
        } catch (Exception e) {
            logger.error("Error in parsing date field " + e.getMessage());
        }
        // Add the log.
    }

  public void setTpCode(String tpCode)
  {
    this.tpCode = tpCode;
  }

  public String getGxs_ref_no() {
        return gxs_ref_no;
    }

    public void setGxs_ref_no(String gxs_ref_no) {
        this.gxs_ref_no = gxs_ref_no;
    }

    public String getLastEventDate() {
        return lastEventDate;
    }


    public String getLastEventTime() {
        return lastEventTime;
    }



    public String getRemote_fn() {
        return remote_fn;
    }

    public void setRemote_fn(String remote_fn) {
        this.remote_fn = remote_fn;
    }

    @Override
    public String toReportString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(gxs_ref_no).append(",");
        builder.append(lastEventDate).append(",").append(lastEventTime).append(",");
        builder.append(remote_fn).append(",").append(blNumber);
        return builder.toString();
    }

    public void setBLNumber(String blNumber) {
        this.blNumber = blNumber;
    }
}
