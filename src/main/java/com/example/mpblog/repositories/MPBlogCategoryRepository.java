package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MPBlogCategoryRepository extends CrudRepository<MPBlogCategory, Integer> {
    List<MPBlogCategory> findAll();
    MPBlogCategory findById(int id);
}