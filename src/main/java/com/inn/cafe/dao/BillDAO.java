package com.inn.cafe.dao;

import com.inn.cafe.entities.Bill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface BillDAO extends JpaRepository<Bill, Integer> {

  List<Bill> getAllBills();

  List<Bill> getBillsByUsername(@Param("createdBy") String currentUserName);
}
