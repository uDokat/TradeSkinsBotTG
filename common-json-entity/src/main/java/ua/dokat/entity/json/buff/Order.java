package ua.dokat.entity.json.buff;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Order {

    private int id;
    private int sell_num;
}
