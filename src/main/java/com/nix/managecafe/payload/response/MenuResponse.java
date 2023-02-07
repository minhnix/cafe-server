package com.nix.managecafe.payload.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private String description;
    private Long originCost;
    private Long cost;
}
