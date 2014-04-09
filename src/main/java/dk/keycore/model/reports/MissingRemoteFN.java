package dk.keycore.model.reports;


public class MissingRemoteFN extends Report
{

  private String reportName;
  private String tpCode;
  private String mbid;
  private String date;

  @Override
  public String toReportString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append(reportName).append(",").append(tpCode).append(",");
    builder.append(mbid).append(",").append(date);
    return builder.toString();
  }

  public String getReportName()
  {
    return reportName;
  }

  public void setReportName(String reportName)
  {
    this.reportName = reportName;
  }

  public String getTpCode()
  {
    return tpCode;
  }

  public void setTpCode(String tpCode)
  {
    this.tpCode = tpCode;
  }

  public String getMbid()
  {
    return mbid;
  }

  public void setMbid(String mbid)
  {
    this.mbid = mbid;
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }
}
