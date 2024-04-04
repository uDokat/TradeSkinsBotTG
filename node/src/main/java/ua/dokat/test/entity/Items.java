package ua.dokat.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

    private String code;
    private ItemData data;

    public ItemData getData() {
        return data;
    }

    public String getCode() {
        return code;
    }
}
