package dk.keycore.model.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudarshana on 3/21/14.
 */
public class RemoteFileNameMapper
{

  private Map<String, String> mapping = new HashMap();

  public RemoteFileNameMapper()
  {
    mapping.put("LO_ADIGT_CUSTF", "*XML");
    mapping.put("LO_ADIGTN_214", "DMCQ*X12");
    mapping.put("LO_ADIGTN_315", "DMCQ*X12");
    mapping.put("LO_ADIGTN_861C", "DMCQ*X12");
    mapping.put("LO_ADIGTN_COM", "DMCQ*X12");
    mapping.put("LO_ADIGTN_CNFB", "SOCON*XML");
    mapping.put("LO_ADIGTN_CNF", "SOCON*XML");
  }

  public String getMapping(final String tpCode)
  {
    return mapping.get(tpCode);
  }
}
