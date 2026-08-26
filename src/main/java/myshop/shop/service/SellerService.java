package myshop.shop.service;

import lombok.RequiredArgsConstructor;
import myshop.shop.dto.seller.LoginSellerDto;
import myshop.shop.entity.Seller;
import myshop.shop.repository.seller.SellerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * 로그인
     */
    public Seller login(LoginSellerDto loginSellerDto) {

        Seller seller = sellerRepository.findById(loginSellerDto.getId()).orElse(null);

        if (seller != null && passwordEncoder.matches(loginSellerDto.getPassword(), seller.getPassword())) {
            return seller;
        }
        return null;
    }
}
