package myshop.shop.repository.inquiry;

import myshop.shop.entity.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
}
