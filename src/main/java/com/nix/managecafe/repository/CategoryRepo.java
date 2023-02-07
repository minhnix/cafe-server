package com.nix.managecafe.repository;

import com.nix.managecafe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category, Long> {
}
