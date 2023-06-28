package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.dto.AccountDto;
import com.quangtoi.flowerstore.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    Optional<Account> findByUsername(String username);

    @Query("select new com.quangtoi.flowerstore.dto.AccountDto(a.id, a.name, a.username, a.password, a.email, a.urlAvatar, a.customer.id, a.cart.id, a.createdAt) from Account a where a.username = :username")
    Optional<AccountDto> findAccountByUsername(@Param("username") String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("select a from Account a where a.resetPasswordToken = :token")
    Account findByResetPasswordToken(@Param("token") String token);
}
