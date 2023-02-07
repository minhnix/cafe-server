package com.nix.managecafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nix.managecafe.model.audit.UserAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@jakarta.persistence.Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
public class Table extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Bill> bills;
    @Column(columnDefinition = "varchar(255) default 'not_used'")
    private String status;
}

