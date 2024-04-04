package ua.dokat.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
