package com.nix.managecafe.payload.response;

import com.nix.managecafe.model.Table;
import com.nix.managecafe.model.User;
import com.nix.managecafe.model.enumname.PaymentType;
import com.nix.managecafe.payload.UserSummary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillResponse {
    private Long id;
    private List<BillDetailResponse> billDetails;
    private String table;
    private PaymentType payment;
    private UserSummary user;
    private Instant createDate;
}
