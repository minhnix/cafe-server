package com.nix.managecafe.repository;

import com.nix.managecafe.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillDetailRepo extends JpaRepository<BillDetail, Long> {

    BillDetail getBillDetailsByBillIdAndMenuId(Long billId, Long menuId);
}
