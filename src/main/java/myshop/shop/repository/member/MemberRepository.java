package myshop.shop.repository.member;

import myshop.shop.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findById(String id);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.password=:password where m.id=:id")
    int updatePassword(@Param("id") String id, @Param("password") String password);

}
