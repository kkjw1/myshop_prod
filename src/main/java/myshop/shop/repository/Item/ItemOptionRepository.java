package myshop.shop.repository.Item;

import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
    List<ItemOption> findByItem(Item item);

    @Modifying(clearAutomatically = true)
    @Query("delete ItemOption io where io.item=:item")
    int deleteItemOptionByItem(@Param("item") Item item);
}
