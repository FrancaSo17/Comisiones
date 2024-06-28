package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.Users;

public interface UsersRepositorio extends JpaRepository<Users, String>{

}
