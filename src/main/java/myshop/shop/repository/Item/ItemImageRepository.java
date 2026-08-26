package myshop.shop.repository.Item;

import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query("select i from ItemImage i where i.item=:item and i.isMain=:isMain")
    Optional<ItemImage> findItemImageByIsMain(@Param("item") Item item, @Param("isMain") boolean isMain);

    @Query("select i from ItemImage i where i.item=:item and i.isMain=:isMain")
    List<ItemImage> findItemImageListByIsMain(@Param("item") Item item, @Param("isMain") boolean isMain);

    @Modifying(clearAutomatically = true)
    @Query("delete ItemImage ii where ii.item=:item")
    int deleteItemImageByItem(@Param("item") Item item);
}
