package com.example.demo.dao;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long>{
    List<Project> findAllByNameStartsWith(String start);
    List<Project> findAllByUseridAndNameStartsWith(User u, String start);
    Optional<Project> findByUseridAndName(User u, String name);
    List<Project> findAllByUserid(User u);
    @Modifying
    @Query("DELETE FROM Project p WHERE p.id = ?1")
    @Transactional
    void delete(long id);
}

