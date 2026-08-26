package myshop.shop.repository.cart;

import myshop.shop.entity.Cart;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom{
    List<Cart> findByMember(Member member);

    @Modifying(clearAutomatically = true)
    @Query("delete from Cart c where c.no=:cartNo")
    int deleteByNo(@Param("cartNo") Long cartNo);
}
