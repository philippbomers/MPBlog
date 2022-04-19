package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogCategory;
import com.example.mpblog.repositories.MPBlogCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record MPBlogCategoryService(MPBlogCategoryRepository mpBlogCategoryRepository) {
    public List<MPBlogCategory> findAll() {
        return mpBlogCategoryRepository.findAll();
    }

    public List<String> getCategoryNames() {
        List<String> catNames = new ArrayList<>();
        for (MPBlogCategory mpBlogCategory : findAll()) {
            catNames.add(mpBlogCategory.getName());
        }
        return catNames;
    }
}