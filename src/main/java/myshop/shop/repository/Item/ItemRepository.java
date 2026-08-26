package myshop.shop.repository.Item;

import myshop.shop.entity.Seller;
import myshop.shop.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    List<Item> findBySeller(Seller seller);

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.viewCount = i.viewCount + 1 where i.no=:itemNo")
    int updateViewCountByNo(@Param("itemNo") Long itemNo);
}
