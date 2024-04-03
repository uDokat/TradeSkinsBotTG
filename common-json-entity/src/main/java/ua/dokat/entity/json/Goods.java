package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import ua.dokat.enums.ResponseStatus;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Goods implements Serializable {

    private GoodsData data;
    private ResponseStatus status;

    public boolean isValid(){
        return status.equals(ResponseStatus.OK);
    }

    @Getter
    public static class GoodsData implements Serializable{

        private int id;
        private int appid;
        private String name;
        private String internal_name;
        private String url;
    }
}
