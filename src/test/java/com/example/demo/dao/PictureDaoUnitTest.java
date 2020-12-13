package com.example.demo.dao;

import com.example.demo.model.Picture;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class PictureDaoUnitTest {
    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    PictureDao pictureDao;

    @BeforeEach
    void setup() {
        User user = new User("artyom", "", "", "", "");
        userDao.save(user);
        Project project = new Project("TestProject", "", "");
        project.setUserid(user);
        projectDao.save(project);
        Picture picture = new Picture("picture");
        picture.setProjectid(project);
        pictureDao.save(picture);
    }


    @Test
    void findAllByProjectid() {
        Project project = projectDao.findAllByNameStartsWith("Test").get(0);
        assertEquals(pictureDao.findAllByProjectid(project).get(0).getSrc(), "picture");
    }

    @Test
    void deleteById() {
        Project project = projectDao.findAllByNameStartsWith("Test").get(0);
        Picture picture = pictureDao.findAllByProjectid(project).get(0);
        pictureDao.deleteById(List.of(picture));
        assertTrue(pictureDao.findAllByProjectid(project).isEmpty());
    }
}