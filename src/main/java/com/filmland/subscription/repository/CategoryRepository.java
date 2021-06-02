package com.filmland.subscription.repository;

import com.filmland.subscription.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameNotIn(List<String> names);

    Category findByName(String name);
}
