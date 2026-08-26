package myshop.shop.repository.log;

import myshop.shop.entity.log.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
