package com.nix.managecafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nix.managecafe.model.audit.UserAudit;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonIgnore
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    private int count;
    private String status;
}