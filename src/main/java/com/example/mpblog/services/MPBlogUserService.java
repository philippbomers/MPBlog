package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MPBlogUserService {

    private final MPBlogUserRepository mpBlogUserRepository;

    public MPBlogUserService(MPBlogUserRepository mpBlogUserRepository) {
        this.mpBlogUserRepository = mpBlogUserRepository;
    }

    public void addMPBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUserRepository.save(mpBlogUser);
    }


    public List<MPBlogUser> getMPBlogUser() {
        return this.mpBlogUserRepository.findAll();
    }


    public MPBlogUser getMPBlogUser(int id) {
        return this.mpBlogUserRepository.findById(id);
    }

    public MPBlogUser getMPBlogUser(String username) {
        return this.mpBlogUserRepository.findByUserName(username);
    }

    public Optional<MPBlogUser> getMPBlogUser(MPBlogUser mpBlogUser) {
        return this.mpBlogUserRepository.findByUserNameAndPassword(mpBlogUser.getUserName(), mpBlogUser.getPassword());
    }

    ;
}
