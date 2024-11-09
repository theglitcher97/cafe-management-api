package com.inn.cafe.dao;

import com.inn.cafe.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDAO extends JpaRepository<Bill, Integer> {}
