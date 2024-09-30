package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Category_repo extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
}
