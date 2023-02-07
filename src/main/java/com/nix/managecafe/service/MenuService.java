package com.nix.managecafe.service;

import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.Category;
import com.nix.managecafe.model.Menu;
import com.nix.managecafe.payload.request.MenuRequest;
import com.nix.managecafe.payload.response.PagedResponse;
import com.nix.managecafe.repository.CategoryRepo;
import com.nix.managecafe.repository.MenuRepo;
import com.nix.managecafe.util.ValidatePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public PagedResponse<Menu> getAll(int page, int size, String sortBy, String sortDir) {
        ValidatePageable.invoke(page, size);

        Sort sort = (sortDir.equalsIgnoreCase("des")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Menu> menus = menuRepo.findAll(pageable);

        return new PagedResponse<>(menus.getContent(), menus.getNumber(),
                menus.getSize(), menus.getTotalElements(), menus.getTotalPages(), menus.isLast());
    }

    public Menu getOne(Long id) {

        return menuRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
    }

    public Menu create(MenuRequest menu) {
        Category category = categoryRepo.findById(menu.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", menu.getCategoryId()));
        Menu menu1 = new Menu(menu.getName(), menu.getDescription(), category, menu.getOriginCost(), menu.getCost());
        return menuRepo.save(menu1);
    }

    public Menu update(Long id, MenuRequest menu) {
        Menu menu1 = menuRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        Category category = categoryRepo.findById(menu.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", menu.getCategoryId()));

        menu1.setDescription(menu.getDescription());
        menu1.setName(menu.getName());
        menu1.setCost(menu.getCost());
        menu1.setCategory(category);
        menu1.setOriginCost(menu.getOriginCost());

        return menuRepo.save(menu1);
    }

    public void delete(Long id) {
        Menu menu = menuRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        menuRepo.delete(menu);
    }

    public List<Menu> getMenuByCategoryId(Long categoryId) {
        return menuRepo.findByCategoryId(categoryId);
    }


}
