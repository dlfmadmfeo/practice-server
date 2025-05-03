package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RefreshToken;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>  {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<RefreshToken> findByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM RefreshToken r WHERE r.userId = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
