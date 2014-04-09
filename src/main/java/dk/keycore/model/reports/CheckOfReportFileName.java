package dk.keycore.model.reports;


import org.apache.log4j.Logger;

public class CheckOfReportFileName extends Report {

    private static final String ENVIRONMENT = "mlogprod";
    private String tpCode;
    private String mbid;
    private String firstEventDate;
    private String firstEventTime;
    private String remoteFn;
    private String recipient;
    private String lastEventDate;
    private String lastEventTime;
    private static Logger logger = Logger.getLogger(CheckOfReportFileName.class);

    @Override
    public String toReportString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(ENVIRONMENT).append(",");
        builder.append(tpCode).append(",");
        builder.append(mbid).append(",");
        builder.append(firstEventDate).append(",").append(firstEventTime).append(",");
        builder.append(remoteFn).append(",");
        builder.append(recipient).append(",");
        builder.append(lastEventDate).append(",").append(lastEventTime);
        return builder.toString();
    }

    public void setTpCode(String tpCode) {
        this.tpCode = tpCode;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setFirstEventDate(String obj) {
        String tmp = removeCommas(obj);
        try {
            String[] vals = tmp.split("\\|");
            firstEventDate = vals[0];
            firstEventTime = vals[1];
        } catch (Exception e) {
           // TO prevent the program crashing on a empty value
           logger.error("Invalid date value " + obj);

        }
    }

    public void setRemoteFn(String remoteFn) {
        this.remoteFn = remoteFn;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setLastEventDate(String obj) {
        String tmp = removeCommas(obj);
        try {
            String[] vals = tmp.split("\\|");
            lastEventDate = vals[0];
            lastEventTime = vals[1];
        } catch (Exception e) {
            logger.error("Invalid date value fot last event date " +obj);
        }
    }


    private String removeCommas(String s)
    {
        try {
            s = s.replaceAll("\\,","");
        } catch (Exception e) {
            logger.error("Error while removing spaces" + e.getMessage());
            s="";
        }
        return s;

    }
}
