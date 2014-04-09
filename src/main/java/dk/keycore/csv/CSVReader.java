package dk.keycore.csv;

import dk.keycore.model.SourceMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudarshana on 3/18/14.
 */
public class CSVReader {

    private final String filePath;
    public CSVReader(final String filePath)
    {
       this.filePath = filePath;
    }

    public List<SourceMessage> readMessages()
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        final List<SourceMessage> messageList = new ArrayList<SourceMessage>();

        try {

            br = new BufferedReader(new FileReader(filePath));
            br.readLine(); // Ignore the header
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                messageList.add(readFromRow(values));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return messageList;
    }

    private SourceMessage readFromRow(final String[] values)
    {
        final SourceMessage message = new SourceMessage();
        int cellIndex = 0;
        message.setGsxRefNo(values[cellIndex++]);
        message.setPartnerName(values[cellIndex++]);
        message.setSender(values[cellIndex++]);
        message.setReceiver(values[cellIndex++]);
        message.setDocumentType(values[cellIndex++]);
        message.setPrimaryKey(values[cellIndex++]);
        message.setApplicationReference(values[cellIndex++]);
        message.setCreatedDate(values[cellIndex++]);
        message.setAcceptedDate(values[cellIndex++]);
        message.setDateExtracted(values[cellIndex++]);
        message.setCommTypes(values[cellIndex++]);
        message.setReceiver(values[cellIndex++]);
        message.setDirection(values[cellIndex++]);
        message.setFileErrorCount(values[cellIndex++]);
        message.setFileReceiver(values[cellIndex++]);
        message.setFileSize(values[cellIndex++]);
        message.setTranslationSessionNo(values[cellIndex++]);
        final List<String> keys = new ArrayList<String>();
        // Reading the secondary keys
        for (int i =0 ; i < 3 ;i++){
            keys.add(values[cellIndex++]);
        }
        //message.setDocSecondaryKeys(keys);
        return message;

    }
}
