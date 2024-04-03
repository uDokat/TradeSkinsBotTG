package ua.dokat.entity;

import lombok.Getter;
import lombok.Setter;
import ua.dokat.entity.json.buff.BuffOrder;

import java.io.Serializable;

@Getter
@Setter
public class Notify implements Serializable {

    private BuffOrder order;
    private String chatId;
    private String message;

}
