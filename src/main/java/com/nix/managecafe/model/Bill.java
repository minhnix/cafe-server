package com.nix.managecafe.model;

import com.nix.managecafe.model.audit.UserAudit;
import com.nix.managecafe.model.enumname.PaymentType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
public class Bill extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "bill",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<BillDetail> billDetails;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private com.nix.managecafe.model.Table table;
    @Enumerated(EnumType.STRING)
    private PaymentType payment;
}
