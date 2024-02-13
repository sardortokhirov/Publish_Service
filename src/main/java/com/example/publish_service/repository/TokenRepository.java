package com.example.publish_service.repository;

import com.example.publish_service.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Date-21/01/2024
 * By Sardor Tokhirov
 */

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
            select t from Token t inner join User s on t.user.userId = s.userId where s.userId = :userId and (t.expired =false or t.revoked =false)
            """)
    List<Token> findByValidTokensBySupplier(UUID userId);

    Optional<Token> findByToken(String token);
}
