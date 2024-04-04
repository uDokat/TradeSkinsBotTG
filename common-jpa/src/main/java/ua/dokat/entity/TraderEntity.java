package ua.dokat.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ua.dokat.entity.json.Goods;
import ua.dokat.entity.json.buff.BuffSkin;
import ua.dokat.entity.json.http.TraderInformation;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "traders")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class TraderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramUserId;
    @Column(length = 500)
    private String cookie;
    @Column(length = 500)
    private String token;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Goods> items;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<BuffSkin> steamInventory;

    public boolean isActive(){
        return cookie != null && token != null;
    }

    public TraderInformation toInformation(){
        return new TraderInformation(token, cookie);
    }
}
