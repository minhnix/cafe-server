package com.nix.managecafe.repository;

import com.nix.managecafe.model.Bill;
import com.nix.managecafe.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
}
