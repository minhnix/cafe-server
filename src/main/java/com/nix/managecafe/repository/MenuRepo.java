package com.nix.managecafe.repository;

import com.nix.managecafe.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    @Query("select menu from Menu menu where menu.category.id = :categoryId")
    List<Menu> findByCategoryId(@Param("categoryId") Long categoryId);
}
