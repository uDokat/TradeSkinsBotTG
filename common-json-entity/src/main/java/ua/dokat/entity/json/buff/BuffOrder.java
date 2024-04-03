package ua.dokat.entity.json.buff;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import ua.dokat.enums.ResponseStatus;
import ua.dokat.enums.SkinRarity;
import ua.dokat.enums.SkinType;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BuffOrder implements Serializable {

    private int appid;
    private int created_at;
    private String id;
    private String price;
    private int updated_at;
    private String user_id;
    private String asset_id;
    private String class_id;
    private int context_id;
    private int goods_id;
    private String instance_id;
    private String icon_url;
    private String inspect_url;
    private int paintindex;
    private int paintseed;

    private SkinType type;
    private SkinRarity rarity;
    private ResponseStatus status;

    public String getUrl(){
        return "https://buff.market/market/goods/" + goods_id +"?game=csgo";
    }
}
