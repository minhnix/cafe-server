package com.nix.managecafe.controller;

import com.nix.managecafe.model.Table;
import com.nix.managecafe.payload.response.ApiResponse;
import com.nix.managecafe.payload.response.PagedResponse;
import com.nix.managecafe.service.TableService;
import com.nix.managecafe.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping
    public PagedResponse<Table> getAll(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        return tableService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public Table getOne(@PathVariable("id") Long id) {
        return tableService.getOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Table table) {
        Table table1 = tableService.create(table);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{tableId}")
                .buildAndExpand(table1.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Table created successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Table table) {
        tableService.update(id, table);
        return ResponseEntity.ok(new ApiResponse(true, "Table updated successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tableService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Table deleted successfully"));
    }
}
