package com.practice.eventhub.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadTestData {
    private final HashMap<String, String> payload = new HashMap<>();
    private static final ObjectMapper mapper=new ObjectMapper();

    public String csvToJson(String keyCol, String keyVal, String file) {
        //try-with resources block to implement CLosable- to close parser and reader at the end of the block
        try (Reader reader = new FileReader(file); //open the CSV file
             //Apache Common's CSVParser to process the file and consider the first row of the CSV file as Header which will help in JSON mapping
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<Map<String, String>> rows = new ArrayList<>();
            for (CSVRecord record : parser) { //CSVRecord [comment='null', recordNumber=1, values=[TC01, email_data, password_data]]
                if (keyVal.equals(record.get(keyCol))) { //record.get("Test_Case_Id") -> TC01
                    rows.add(record.toMap()); //record.toMap() -> {Test_Case_Id=TC01, email=email_data, password=password_data}
                    break;
                }
            }
//            System.out.println(rows);
            if(rows.isEmpty()){
                throw new IllegalArgumentException("No records found for "+keyVal);
            }
            rows.getFirst().remove(keyCol); //remove the "Test_Case_Id" field
            return mapper.writeValueAsString(rows.getFirst()); //Convert the List<Map<>> object to JSON styled String {email: "", password: ""}
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse CSV " + file, e);
        }
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        LoadTestData loadTestData = new LoadTestData();
//        String json = loadTestData.csvToJson("Test_Case_Id", "TC01", "src/test/resources/testdata/registration_payload_data.csv");
//        System.out.println(json);
//    }
}
