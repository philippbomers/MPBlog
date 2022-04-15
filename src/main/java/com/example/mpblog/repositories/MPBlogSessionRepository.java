package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogSession;
import com.mysql.cj.Session;
import org.springframework.data.repository.CrudRepository;

public interface MPBlogSessionRepository extends CrudRepository<MPBlogSession, String> {


}
