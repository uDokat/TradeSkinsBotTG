package ua.dokat.entity.json.http;

import lombok.Builder;
import lombok.Getter;
import ua.dokat.entity.json.JsonEntity;

@Builder
@Getter
public class BadResponse extends JsonEntity {

    private String message;

}
