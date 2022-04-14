package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPBlogUserRepository extends CrudRepository<MPBlogUser, Integer> {
    List<MPBlogUser> findAll();

    MPBlogUser findById(int id);

    MPBlogUser findByUserName(String username);
}
