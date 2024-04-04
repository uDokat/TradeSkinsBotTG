package ua.dokat.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ua.dokat.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramUserId;
    private String chatId;
    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private UserState state;

    public boolean isActive(){
        return isActive;
    }
}
