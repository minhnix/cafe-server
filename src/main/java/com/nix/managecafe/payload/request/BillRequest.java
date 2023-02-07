package com.nix.managecafe.payload.request;

import com.nix.managecafe.model.enumname.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillRequest {
    @NotNull
    @Valid
    private List<BillDetailRequest> billDetails;
    @NotNull
    private Long tableId;
    @NotNull
    private PaymentType payment;
}
