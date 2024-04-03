package ua.dokat.entity.json.buff;

import lombok.*;
import ua.dokat.enums.SkinRarity;
import ua.dokat.enums.SkinType;

import java.io.Serializable;

@Getter
public class BuffSkin implements Serializable {

    private int appid;
    private String assetid;
    private String classid;
    private int contextid;
    private int goods_id;
    private String instanceid;
    private String name;
    private String sell_order_id;
    private String sell_order_price;
    private boolean tradable;
    private String paintwear;
    private String inspect_url;

    private SkinType type;
    private SkinRarity rarity;
}
