package com.practice.eventhub.common.util;

import java.util.HashMap;

public class LoadTestData {
    private final HashMap<String,String> payload = new HashMap<>();

    public void preparePayload(String key, String value) {
        payload.put(key, value);
    }

    public HashMap<String, String> getPayload() {
        return payload;
    }
}
