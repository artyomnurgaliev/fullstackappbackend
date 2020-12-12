package com.example.demo.dao;

import com.example.demo.model.Picture;
import com.example.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PictureDao extends JpaRepository<Picture, Long> {
    List<Picture> findAllByProjectid(Project project);

    @Modifying
    @Query("DELETE FROM Picture p WHERE p in ?1")
    @Transactional
    void deleteById(List<Picture> id);
}
