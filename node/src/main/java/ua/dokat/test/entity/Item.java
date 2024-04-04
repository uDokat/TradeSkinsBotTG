package ua.dokat.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Item {

    private int id;
    private ItemInfo goods_info;
    private String name;
    private String sell_min_price;

    public String getUrl(){
        return "https://buff.market/market/goods/" + id;
    }
}
