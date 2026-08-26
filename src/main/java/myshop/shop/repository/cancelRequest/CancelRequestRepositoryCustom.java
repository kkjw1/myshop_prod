package myshop.shop.repository.cancelRequest;

import myshop.shop.dto.cancelRequest.ManageCancelReturnDto;
import myshop.shop.dto.cancelRequest.SellerManageRequestDto;

import java.util.List;

public interface CancelRequestRepositoryCustom {
    List<ManageCancelReturnDto> findCancelReturnList(Long memberNo);
    List<SellerManageRequestDto> findSellerManageRequest(Long sellerNo);
}
