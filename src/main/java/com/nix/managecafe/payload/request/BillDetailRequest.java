package com.nix.managecafe.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillDetailRequest {
    private Long tableId;
    private Long billId;
    @NotNull
    private Long menuId;
    @NotNull
    private int count;
}
