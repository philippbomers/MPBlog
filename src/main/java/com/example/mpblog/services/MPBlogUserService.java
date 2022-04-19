package com.example.mpblog.services;

import com.example.mpblog.entities.MPBlogUser;
import com.example.mpblog.repositories.MPBlogUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class (record) for the user class.
 *
 * @param mpBlogUserRepository is used to access the necessary database queries for users
 */
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

    /**
     * Method to change the admin status of a certain user
     *
     * @param mpBlogUser takes the blog user as parameter
     */
    public void changeUserAdminStatus(MPBlogUser mpBlogUser) {
        mpBlogUser.setAdminRights(!mpBlogUser.isAdminRights());
        this.mpBlogUserRepository.save(mpBlogUser);
    }
}
