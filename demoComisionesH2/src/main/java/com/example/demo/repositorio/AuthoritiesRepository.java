package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.Authorities;

import jakarta.transaction.Transactional;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {

	@Transactional
    @Modifying
    @Query(value = "INSERT INTO authorities (username, authority) VALUES (:username, :authority)", nativeQuery = true)
    void insertAuthority(@Param("username") String username, 
    						@Param("authority") String authority);
}
