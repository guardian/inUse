package services;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.gson.Gson;

/**
 * The Class DynamoUtil. This is copied from https://github.com/sarveshkaushal/cloud-utilities. Thanks sarveshkaushal!
 */
public class DynamoUtil {

    /** The Constant EXPORT_FILE. */
    private static final String EXPORT_FILE = "export.csv";

    /** The client. */
    private AmazonDynamoDBClient client;

    /**
     * Instantiates a new dynamo util.
     *
     * @param client the client
     */
    public DynamoUtil(AmazonDynamoDBClient client) {
        this.client = client;
    }

    /**
     * Export.
     *
     * @param tableName the table name
     * @param filePath the file path
     * @return the file
     * @throws IOException if the printer doesn't closes.
     */
    public File export(String tableName, String filePath) throws IOException{
        ScanResult result = null;
        List<Map<String, AttributeValue>> resultList = new ArrayList<>();
        //Implement pagination if the return size of of result is more than 1MB.
        do {
            ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
            if (result != null) {
                scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
            }
            result = this.client.scan(scanRequest);
            resultList.addAll(result.getItems());
        } while (result.getLastEvaluatedKey() != null);

        List<String> columnList = getColums(resultList.get(0));
        List<String> rowList = getRows(resultList);

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter(',');

        FileWriter fileWriter;
        String fileName = filePath + EXPORT_FILE;
        CSVPrinter csvFilePrinter = null;

        try {
            fileWriter = new FileWriter(fileName);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord(columnList);

            int num = rowList.size()/columnList.size();
            int startIndex = 0;
            int breakIndex = columnList.size();
            for(int i=0; i < num; i++){
                csvFilePrinter.printRecord(rowList.subList(startIndex, breakIndex));
                startIndex = breakIndex;
                breakIndex += columnList.size();
            }

            csvFilePrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != csvFilePrinter) {
                csvFilePrinter.close();
            }
        }
        return (new File(fileName));
    }


    /**
     * Export.
     *
     * @param tableName the table name
     * @param filePath the file path
     * @param format the format
     * @param projectionExpression the projection expression
     * @return the file
     * @throws IOException
     */
    public File export(String tableName, String filePath, String format, String projectionExpression) throws IOException{
        return export(tableName, filePath);
    }

    /**
     * Gets the colums.
     *
     * @param itemMap the item map
     * @return the colums
     */
    private List<String> getColums(Map<String, AttributeValue> itemMap) {
        return itemMap.keySet().stream().collect(toList());
    }

    /**
     * Gets the rows.
     *
     * @param itemList the item list
     * @return the rows
     */
    private List<String> getRows(List<Map<String, AttributeValue>> itemList) {
        List<String> rows = new ArrayList<>();
        itemList.forEach((map)-> map.forEach((k,v)-> rows.add( convertToString(v) )));
        return rows;
    }

    /**
     * Convert to string.
     *
     * @param attribute the attribute
     * @return the string
     */
    private static String convertToString(AttributeValue attribute){
        String type = null;
        Gson gson = new Gson();
        String result = null;
        if (attribute.getS() != null) {
            type = "S";
        }
        if (attribute.getN() != null) {
            type = "N";
        }
        if (attribute.getM() != null) {
            type = "M";
        }
        if (attribute.getL() != null) {
            type = "L";
        }
        if (attribute.getBOOL() != null) {
            type = "B";
        }

        switch (type) {
            case "S":
                result = gson.toJson(attribute.getS());
                break;
            case "N":
                result = gson.toJson(attribute.getN());
                break;
            case "B":
                result = gson.toJson(attribute.getBOOL());
                break;
            case "M":
                result = gson.toJson((Map<String, AttributeValue>) attribute.getM());
                break;
            case "L":
                result = gson.toJson(attribute.getL());
                break;
            default:
                result = "";
        }
        return result;
    }
}
