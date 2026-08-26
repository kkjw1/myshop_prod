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
import java.util.List;

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
        String mainPath;
        List<String> subPath = new ArrayList<>();

        public ImagePath(ItemImage main, List<ItemImage> sub) {
            if (main == null) {
                this.mainPath = null;
            } else {
                this.mainPath = main.getImageUrl();
            }
            for (ItemImage itemImage : sub) {
                this.subPath.add(itemImage.getImageUrl());
            }
        }
    }
}
