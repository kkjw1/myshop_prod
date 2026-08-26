package myshop.shop.entity.log;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class LoginLog extends LogBaseEntity {
    @Id @GeneratedValue
    @Column(name = "login_log_no")
    private Long no;
    private String id;

    public LoginLog() {
    }

    public LoginLog(String id) {
        this.id = id;
    }
}
