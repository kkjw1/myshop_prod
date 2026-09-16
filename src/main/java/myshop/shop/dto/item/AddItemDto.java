package myshop.shop.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemStatus;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
@ToString(of = {"sellerNo", "name", "itemCategory", "price", "totalStock", "discountPer", "addItemOptionDtoList", "mainImage",
        "mainImageUrl", "mainImageName", "subImages", "subImagesInfo", "content", "itemStatus", "useOptions", "viewCount"})
public class AddItemDto {
    private Long sellerNo;

    private String name;
    private ItemCategory itemCategory;

    private BigDecimal price;
    private int totalStock;
    private BigDecimal discountPer;
    private List<AddItemOptionDto> addItemOptionDtoList = new ArrayList<>();

    private MultipartFile mainImage;
    private String mainImageUrl;
    private String mainImageName;

    private List<MultipartFile> subImages = new ArrayList<>();
    private List<Map<String, String>> subImagesInfo = new ArrayList<>();
    private String content;

    private ItemStatus itemStatus;
    private boolean useOptions;     //옵션 사용 체크박스

    private Long viewCount;


    public AddItemDto() {
    }



    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, int price, int totalStock, int discountPer,
                      List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage, String mainImageUrl,
                      List<MultipartFile> subImages, List<Map<String, String>> subImagesInfo, String content, boolean useOptions) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = BigDecimal.valueOf(price);
        this.totalStock = totalStock;
        this.discountPer = BigDecimal.valueOf(discountPer);
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = ItemStatus.승인대기;
        this.useOptions = useOptions;
    }
    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, BigDecimal price, int totalStock,
                      BigDecimal discountPer, List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage,
                      String mainImageUrl, List<MultipartFile> subImages, List<Map<String, String>> subImagesInfo,
                      String content, boolean useOptions) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = price;
        this.totalStock = totalStock;
        this.discountPer = discountPer;
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = ItemStatus.승인대기;
        this.useOptions = useOptions;
    }



    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, int price, int totalStock, int discountPer,
                      List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage, String mainImageUrl,
                      List<MultipartFile> subImages, List<Map<String, String>> subImagesInfo, String content, ItemStatus itemStatus, boolean useOptions) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = BigDecimal.valueOf(price);
        this.totalStock = totalStock;
        this.discountPer = BigDecimal.valueOf(discountPer);
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = itemStatus;
        this.useOptions = useOptions;
    }
    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, BigDecimal price, int totalStock, BigDecimal discountPer,
                      List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage, String mainImageUrl,
                      List<MultipartFile> subImages, List<Map<String, String>> subImagesInfo, String content, ItemStatus itemStatus, boolean useOptions) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = price;
        this.totalStock = totalStock;
        this.discountPer = discountPer;
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = itemStatus;
        this.useOptions = useOptions;
    }



    // 테스트 전용
    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, int price, int totalStock, int discountPer,
                      List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage, String mainImageUrl, List<MultipartFile> subImages,
                      List<Map<String, String>> subImagesInfo, String content, ItemStatus itemStatus, boolean useOptions, Long viewCount) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = BigDecimal.valueOf(price);
        this.totalStock = totalStock;
        this.discountPer = BigDecimal.valueOf(discountPer);
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = itemStatus;
        this.useOptions = useOptions;
        this.viewCount = viewCount;
    }
    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, BigDecimal price, int totalStock, BigDecimal discountPer,
                      List<AddItemOptionDto> addItemOptionDtoList, MultipartFile mainImage, String mainImageUrl,
                      List<MultipartFile> subImages, List<Map<String, String>> subImagesInfo, String content, ItemStatus itemStatus,
                      boolean useOptions, Long viewCount) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = price;
        this.totalStock = totalStock;
        this.discountPer = discountPer;
        this.addItemOptionDtoList = addItemOptionDtoList;
        this.mainImage = mainImage;
        this.mainImageUrl = mainImageUrl;
        this.subImages = subImages;
        this.subImagesInfo = subImagesInfo;
        this.content = content;
        this.itemStatus = itemStatus;
        this.useOptions = useOptions;
        this.viewCount = viewCount;
    }



    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, int price, int totalStock, int discountPer,
                      String mainImageUrl, Map<String, String> subImagesInfo, String content, ItemStatus itemStatus) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = BigDecimal.valueOf(price);
        this.totalStock = totalStock;
        this.discountPer = BigDecimal.valueOf(discountPer);
        this.mainImageUrl = mainImageUrl;
        this.subImagesInfo.add(subImagesInfo);
        this.content = content;
        this.itemStatus = itemStatus;
    }
    public AddItemDto(Long sellerNo, String name, ItemCategory itemCategory, BigDecimal price, int totalStock,
                      BigDecimal discountPer, String mainImageUrl, Map<String, String> subImagesInfo, String content, ItemStatus itemStatus) {
        this.sellerNo = sellerNo;
        this.name = name;
        this.itemCategory = itemCategory;
        this.price = price;
        this.totalStock = totalStock;
        this.discountPer = discountPer;
        this.mainImageUrl = mainImageUrl;
        this.subImagesInfo.add(subImagesInfo);
        this.content = content;
        this.itemStatus = itemStatus;
    }



    public void updateAddItemOptionDtoList(AddItemOptionDto addItemOptionDto) {
        this.addItemOptionDtoList.add(addItemOptionDto);
    }

    public void addTotalStock(int optionStock) {
        this.totalStock += optionStock;
    }
}
