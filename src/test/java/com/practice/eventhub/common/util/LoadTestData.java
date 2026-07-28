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

    public void preparePayload(String key, String value) {
        payload.put(key, value);
    }

    public HashMap<String, String> getPayload() {
        return payload;
    }

    public String csvToJson(String keyCol, String keyVal, String file) {
        try (Reader reader = new FileReader(file);
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<Map<String, String>> rows = new ArrayList<>();
            for (CSVRecord record : parser) {
                if (keyVal.equals(record.get(keyCol))) {
                    rows.add(record.toMap());
                    break;
                }
            }
            rows.getFirst().remove("Test_Case_Id");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(rows.getFirst());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse CSV " + file, e);
        }
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        LoadTestData loadTestData = new LoadTestData();
//        String json = loadTestData.csvToJson("Test_Case_Id", "TC01", "src/test/resources/testdata/payload_data.csv");
//        System.out.println(json);
//    }
}
