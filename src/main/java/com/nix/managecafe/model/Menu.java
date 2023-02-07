package com.nix.managecafe.model;

import com.nix.managecafe.model.audit.UserAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Menu extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @NotNull
    private Long originCost;
    @NotNull
    private Long cost;
    private String imageUrl;

    public Menu(String name, String description, Category category, Long originCost, Long cost) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.originCost = originCost;
        this.cost = cost;
    }
}
