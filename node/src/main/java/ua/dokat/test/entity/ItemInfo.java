package ua.dokat.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ItemInfo {

    private String icon_url;
    private String steam_price;
}
