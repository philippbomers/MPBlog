package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MPBlogUserRepository extends CrudRepository<MPBlogUser, Integer> {
    List<MPBlogUser> findAll();

    MPBlogUser findById(int id);

    MPBlogUser findByUserName(String username);

    Optional<MPBlogUser> findByUserNameAndPassword(String username, String password);
}
