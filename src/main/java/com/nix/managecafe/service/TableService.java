package com.nix.managecafe.service;

import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.Table;
import com.nix.managecafe.payload.response.PagedResponse;
import com.nix.managecafe.repository.TableRepo;
import com.nix.managecafe.util.ValidatePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TableService {
    @Autowired
    private TableRepo tableRepo;

    public PagedResponse<Table> getAll(int page, int size, String sortBy, String sortDir) {
        ValidatePageable.invoke(page, size);

        Sort sort = (sortDir.equalsIgnoreCase("des")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Table> tables = tableRepo.findAll(pageable);

        return new PagedResponse<>(tables.getContent(), tables.getNumber(),
                tables.getSize(), tables.getTotalElements(), tables.getTotalPages(), tables.isLast());
    }

    public Table getOne(Long id) {

        return tableRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", id));
    }

    public Table create(Table table) {
        return tableRepo.save(table);
    }

    public Table update(Long id, Table table) {
        Table table1 = tableRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", id));
        table1.setDescription(table.getDescription());
        table1.setName(table.getName());

        return tableRepo.save(table1);
    }

    public void delete(Long id) {
        Table table = tableRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", id));
        tableRepo.delete(table);
    }
}
