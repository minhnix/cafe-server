package com.nix.managecafe.service;

import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.Menu;
import com.nix.managecafe.model.Bill;
import com.nix.managecafe.model.BillDetail;
import com.nix.managecafe.payload.request.BillDetailRequest;
import com.nix.managecafe.payload.response.BillDetailResponse;
import com.nix.managecafe.repository.MenuRepo;
import com.nix.managecafe.repository.BillDetailRepo;
import com.nix.managecafe.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepo billDetailRepo;

    @Autowired
    private MenuRepo menuRepo;

    public BillDetailResponse create(BillDetailRequest billDetailRequest, Bill bill) {
        BillDetail billDetail = new BillDetail();
        billDetail.setCount(billDetailRequest.getCount());
        Long menuId = billDetailRequest.getMenuId();
        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        billDetail.setMenu(menu);
        billDetail.setBill(bill);

        BillDetail newBillDetail = billDetailRepo.save(billDetail);
        return ModelMapper.mapBillDetailToBillDetailResponse(newBillDetail);
    }

    public BillDetailResponse update(BillDetailRequest billDetailRequest, Bill bill) {
        BillDetail billDetail = billDetailRepo.getBillDetailsByBillIdAndMenuId(bill.getId(), billDetailRequest.getMenuId());
        billDetail.setCount(billDetailRequest.getCount());

        BillDetail updatedBillDetail = billDetailRepo.save(billDetail);
        return ModelMapper.mapBillDetailToBillDetailResponse(updatedBillDetail);
    }

}
