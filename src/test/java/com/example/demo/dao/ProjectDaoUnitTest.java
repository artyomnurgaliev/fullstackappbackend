package com.example.demo.dao;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class ProjectDaoUnitTest {
    @BeforeEach
    void setup() {
        User user = new User("artyom", "", "", "", "");
        userDao.save(user);
        Project project = new Project("TestProject", "", "");
        project.setUserid(user);
        projectDao.save(project);
    }

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Test
    void findAllByNameStartsWith() {
        assertEquals(projectDao.findAllByNameStartsWith("Test").get(0).getName(), "TestProject");
    }

    @Test
    void findAllByUseridAndNameStartsWith() {
        assertEquals(projectDao.findAllByUseridAndNameStartsWith(userDao.findByLogin("artyom").get(), "Test").get(0).getName(),
                "TestProject");
    }

    @Test
    void findByUseridAndName() {
        assertEquals(projectDao.findByUseridAndName(userDao.findByLogin("artyom").get(), "TestProject").get().getName(), "TestProject");
    }

    @Test
    void findAllByUserid() {
        assertEquals(projectDao.findAllByUserid(userDao.findByLogin("artyom").get()).get(0).getName(), "TestProject");
    }

    @Test
    void delete() {
        long id = projectDao.findAllByNameStartsWith("Test").get(0).getId();
        projectDao.delete(id);
        assertTrue(projectDao.findAllByNameStartsWith("Test").isEmpty());
    }
}