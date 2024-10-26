package com.inn.cafe.dao;

import com.inn.cafe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Integer> {

  User findByEmail(@Param("email") String email);
}
