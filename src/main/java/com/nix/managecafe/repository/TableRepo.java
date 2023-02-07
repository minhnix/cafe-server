package com.nix.managecafe.repository;

import com.nix.managecafe.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepo extends JpaRepository<Table, Long> {
}
