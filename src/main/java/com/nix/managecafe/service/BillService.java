package com.nix.managecafe.service;

import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.*;
import com.nix.managecafe.payload.request.BillDetailRequest;
import com.nix.managecafe.payload.request.BillRequest;
import com.nix.managecafe.payload.response.BillResponse;
import com.nix.managecafe.payload.response.PagedResponse;
import com.nix.managecafe.repository.MenuRepo;
import com.nix.managecafe.repository.BillRepo;
import com.nix.managecafe.repository.TableRepo;
import com.nix.managecafe.repository.UserRepo;
import com.nix.managecafe.util.ModelMapper;
import com.nix.managecafe.util.ValidatePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private TableRepo tableRepo;

    @Autowired
    private BillRepo billRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BillDetailService billDetailService;

    public BillResponse getOne(Long billId) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));
        User user = userRepo.findById(bill.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", bill.getCreatedBy()));
        return ModelMapper.mapBillToBillResponse(bill, user);
    }

    public PagedResponse<BillResponse> getAll(int page, int size, String sortBy, String sortDir) {
        ValidatePageable.invoke(page, size);

        Sort sort = (sortDir.equalsIgnoreCase("des")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Bill> bills = billRepo.findAll(pageable);
        List<BillResponse> billResponses = bills.getContent().stream().map(
                bill -> {
                    User user = userRepo.findById(bill.getCreatedBy())
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", bill.getCreatedBy()));
                    return ModelMapper.mapBillToBillResponse(bill, user);
                }
        ).toList();

        return new PagedResponse<>(billResponses, bills.getNumber(),
                bills.getSize(), bills.getTotalElements(), bills.getTotalPages(), bills.isLast());
    }

    public Bill create(BillRequest billRequest) {
        Bill bill = new Bill();
        Table table = tableRepo.findById(billRequest.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", billRequest.getTableId()));

        Table newTable = tableRepo.save(table);
        bill.setTable(newTable);
        bill.setPayment(billRequest.getPayment());
        Bill newBill = billRepo.save(bill);

        for (BillDetailRequest billDetailRequest : billRequest.getBillDetails()) {
            billDetailService.create(billDetailRequest, newBill);
        }
        return newBill;
    }

    public Bill update(BillRequest billRequest, Long billId) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));

        bill.setPayment(billRequest.getPayment());
        Bill updatedBill = billRepo.save(bill);

        for (BillDetailRequest billDetailRequest : billRequest.getBillDetails()) {
            billDetailService.update(billDetailRequest, updatedBill);
        }
        return updatedBill;
    }

    public void delete(Long billId) {
        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));

        billRepo.delete(bill);
    }

}
