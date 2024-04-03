package ua.dokat.entity.json.buff;

import lombok.Getter;
import lombok.Setter;
import ua.dokat.entity.json.buff.BuffSkin;
import ua.dokat.enums.ResponseStatus;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class TraderInventory implements Serializable {

    private List<BuffSkin> skins;

    private ResponseStatus status;

    public boolean isValid(){
        return status.equals(ResponseStatus.OK);
    }
}
