package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record MPBlogUserService(MPBlogUserRepository mpBlogUserRepository) {

    public void addMPBlogUser(MPBlogUser mpBlogUser) {
        this.mpBlogUserRepository.save(mpBlogUser);
    }

    public List<MPBlogUser> getMPBlogUsers() {
        return this.mpBlogUserRepository.findAll();
    }


    public MPBlogUser getMPBlogUser(int id) {
        return this.mpBlogUserRepository.findById(id);
    }

    public MPBlogUser getMPBlogUsers(String username) {
        return this.mpBlogUserRepository.findByUserName(username);
    }

    public Optional<MPBlogUser> getMPBlogUsers(MPBlogUser mpBlogUser) {
        return this.mpBlogUserRepository.findByUserNameAndPassword(mpBlogUser.getUserName(), mpBlogUser.getPassword());
    }

    public void changeUserAdminStatus(MPBlogUser mpBlogUser) {

        mpBlogUser.setAdminRights(!mpBlogUser.isAdminRights());
        this.mpBlogUserRepository.save(mpBlogUser);
    }
}
