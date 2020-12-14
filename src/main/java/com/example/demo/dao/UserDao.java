package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByLoginAndPassword(String login, String password);

    @Async("asyncExecutor")
    @Transactional
    @Modifying
    @Query("UPDATE User u SET fullname = :fullname, description = :description, photo = :photo WHERE userid = :userid")
    void asyncUpdate(@Param("userid") long userid,
                     @Param("fullname") String fullname,
                     @Param("description") String description,
                     @Param("photo") String photo);
}