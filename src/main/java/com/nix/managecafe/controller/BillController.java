package com.nix.managecafe.controller;

import com.nix.managecafe.model.Bill;
import com.nix.managecafe.payload.request.BillRequest;
import com.nix.managecafe.payload.response.ApiResponse;
import com.nix.managecafe.payload.response.BillResponse;
import com.nix.managecafe.payload.response.PagedResponse;
import com.nix.managecafe.service.BillService;
import com.nix.managecafe.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/{billId}")
    public BillResponse getOne(@PathVariable("billId") Long billId) {
        return billService.getOne(billId);
    }

    @GetMapping
    public PagedResponse<BillResponse> getAll(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        return billService.getAll(page, size, sortBy, sortDir);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> createBill(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = billService.create(billRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{buildId}")
                .buildAndExpand(bill.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Bill created successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{billId}")
    public ResponseEntity<ApiResponse> updateBill(@PathVariable("billId") Long billId,
                           @RequestBody BillRequest billRequest
    ) {
        billService.update(billRequest, billId);
        return new ResponseEntity<>(new ApiResponse(true, "Bill updated successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{billId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable("billId") Long billId) {
        billService.delete(billId);
    }
}
