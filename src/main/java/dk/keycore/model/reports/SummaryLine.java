package dk.keycore.model.reports;

/**
 * Created by sudarshana on 3/20/14.
 */
public class SummaryLine extends Report {

    private final String line;
    public SummaryLine(final String line)
    {
       this.line = line;
    }
    @Override
    public String toReportString() {
        return line;
    }
}
