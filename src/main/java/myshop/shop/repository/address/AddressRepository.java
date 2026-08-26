package myshop.shop.repository.address;

import myshop.shop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByMemberNo(Long memberNo);

    @Modifying(clearAutomatically = true)
    @Query("update Address a set a.isMain = false where a.member.no=:memberNo and a.isMain=true")
    int updateIsMainToFalse(@Param("memberNo") Long memberNo);

    @Modifying(clearAutomatically = true)
    @Query("update Address a set a.isMain = true where a.no=:addressNo")
    int updateIsMainToTrue(@Param("addressNo") Long addressNo);


    List<Address> findByMemberNoOrderByIsMainDesc(Long memberNo);

    @Modifying(clearAutomatically = true)
    @Query("delete Address a where a.no=:addressNo")
    int deleteAddressByNo(@Param("addressNo") Long no);

    Optional<Address> findByNo(Long addressNo);

    @Query("select a from Address a where a.isMain = true and a.member.no=:memberNo")
    Optional<Address> findByMemberIsMain(@Param("memberNo") Long memberNo);
}
