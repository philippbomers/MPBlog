package com.example.mpblog.repositories;

import com.example.mpblog.entities.MPBlogEntry;
import com.example.mpblog.entities.MPBlogUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MPBlogEntryRepository extends CrudRepository<MPBlogEntry, Integer> {

    List<MPBlogEntry> findAll();

    MPBlogEntry findById(int id);

    int countByMpBlogUser(Optional<MPBlogUser> mpBlogUser);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MPBlogEntry mpbe SET mpbe.title= :title WHERE mpbe.id = :entryId")
    int updateTitle(@Param("entryId") int entryId, @Param("title") String title);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MPBlogEntry mpbe SET mpbe.content= :content WHERE mpbe.id = :entryId")
    int updateContent(@Param("entryId") int entryId, @Param("content") String content);
}