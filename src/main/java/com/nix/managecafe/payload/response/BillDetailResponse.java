package com.nix.managecafe.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillDetailResponse {
    private Long id;
    private MenuResponse menu;
    private int count;
}
