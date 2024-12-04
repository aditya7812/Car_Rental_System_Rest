package com.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrental.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
