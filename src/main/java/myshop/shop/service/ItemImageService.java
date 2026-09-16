package myshop.shop.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.item.ModifyItemDto;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemImage;
import myshop.shop.repository.Item.ItemImageRepository;
import myshop.shop.repository.Item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;
    private final ItemRepository itemRepository;


    /**
     * 이미지 주소 가져오기
     */
    public ImagePath getItemImageByIsMain(Long itemNo) {
        Item itemProxy = itemRepository.getReferenceById(itemNo);
        ItemImage main = itemImageRepository.findItemImageByIsMain(itemProxy, true).orElse(null);
        List<ItemImage> sub = itemImageRepository.findItemImageListByIsMain(itemProxy, false);
        return new ImagePath(main, sub);
    }

    @Getter
    public static class ImagePath {
        String mainImageUrl;
        String mainImageName;
        List<Map<String, String>> subInfo = new ArrayList<>();

        public ImagePath(ItemImage main, List<ItemImage> sub) {
            if (main == null) {
                this.mainImageUrl = null;
            } else {
                this.mainImageUrl = main.getImageUrl();
                this.mainImageName = main.getImageName();
            }
            for (ItemImage itemImage : sub) {
                this.subInfo.add(Map.of(
                        "imageUrl", itemImage.getImageUrl(),
                        "imageName", itemImage.getImageName()
                ));
            }
        }
    }
}
