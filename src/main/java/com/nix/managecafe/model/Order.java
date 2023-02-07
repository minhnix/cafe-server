package com.nix.managecafe.model;

import com.nix.managecafe.model.audit.UserAudit;
import com.nix.managecafe.model.enumname.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@jakarta.persistence.Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDetail> orderDetails;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private com.nix.managecafe.model.Table table;
}