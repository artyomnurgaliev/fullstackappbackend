package com.example.demo.controller;

import com.example.demo.dao.PictureDao;
import com.example.demo.dao.ProjectDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.representation.ProjectRepresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
public class UserControllerTest {
    @TestConfiguration
    static class UserControllerTestContextConfiguration {

        @Bean
        public UserController userController() {
            return new UserController();
        }
    }
    @Autowired
    private UserController userController;

    @MockBean
    UserDao userDao;

    @MockBean
    ProjectDao projectDao;

    @MockBean
    PictureDao pictureDao;

    @Test
    public void checkIfUserExists() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        User user = userController.checkIfUserExists("artyom", "password");

        Mockito.verify(userDao).findByLoginAndPassword("artyom", "password");
        assertThat(user.getUserid(), is(1l));
    }

    @Test
    public void checkIfProjectExists() {
        User art = new User("art", "", "", "", "");
        Mockito.when(projectDao.findByUseridAndName(art, "First Project")).thenReturn(Optional.of(new Project()));
        userController.checkIfProjectExists(art, "First Project");
        Mockito.verify(projectDao, Mockito.times(1)).findByUseridAndName(art, "First Project");
    }

    @Test
    public void login() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        User user = userController.login("artyom", "password").getBody();

        Mockito.verify(userDao, Mockito.times(1)).findByLoginAndPassword("artyom", "password");
        assertThat(user.getUserid(), is(1l));
    }

    @Test
    public void signup() {
        User art = new User("art", "", "", "", "");
        Mockito.when(userDao.findByLogin("art")).thenReturn(Optional.empty());
        Mockito.when(userDao.save(art)).thenReturn(art);
        User user = userController.signup(art, "art").getBody();
        Mockito.verify(userDao, Mockito.times(1)).findByLogin("art");
        Mockito.verify(userDao, Mockito.times(1)).save(art);
    }

    @Test
    public void getUser() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Mockito.when(userDao.findByLogin("artyom")).thenReturn(Optional.of(u));
        userController.getUser("artyom");
        Mockito.verify(userDao, Mockito.times(1)).findByLogin("artyom");
    }

    @Test
    public void updateUser() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Mockito.when(userDao.save(u)).thenReturn(u);
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        userController.updateUser(u, "artyom", "password");
        Mockito.verify(userDao, Mockito.times(1)).findByLoginAndPassword("artyom", "password");
        Mockito.verify(userDao, Mockito.times(1)).save(u);
    }

    @Test
    public void updateUserProject() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Project project = new Project("1",  "", "");
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        Mockito.when(projectDao.findByUseridAndName(u, "test1")).thenReturn(Optional.of(project));
        ProjectRepresentation projectRepresentation = new ProjectRepresentation("1", "", "", new ArrayList<>());
        userController.updateUserProject(projectRepresentation, "artyom", "password", "test1");
        Mockito.verify(userDao, Mockito.times(1)).findByLoginAndPassword("artyom", "password");
        Mockito.verify(projectDao, Mockito.times(1)).findByUseridAndName(u, "test1");
    }

    @Test
    public void addUserProject() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Project project = new Project("1",  "", "");
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        Mockito.when(projectDao.findByUseridAndName(u, "test")).thenReturn(Optional.empty());
        ProjectRepresentation projectRepresentation = new ProjectRepresentation("test", "", "", new ArrayList<>());
        userController.addUserProject(projectRepresentation, "artyom", "password");
        Mockito.verify(projectDao, Mockito.times(1)).findByUseridAndName(u, "test");
    }

    @Test
    public void getUserProjects() {
        User u = new User("artyom", "password", "", "", "");
        u.setUserid(1L);
        Project project = new Project("1",  "", "");
        Mockito.when(userDao.findByLoginAndPassword("artyom", "password")).thenReturn(java.util.Optional.of(u));
        Mockito.when(projectDao.findAllByUseridAndNameStartsWith(u, "test1")).thenReturn(List.of(project));
        userController.getUserProjects("test1", "artyom", "password");
        Mockito.verify(userDao, Mockito.times(1)).findByLoginAndPassword("artyom", "password");
        Mockito.verify(projectDao, Mockito.times(1)).findAllByUseridAndNameStartsWith(u, "test1");
    }

    @Test
    public void getProjects() {
        Project project = new Project("1",  "", "");
        Mockito.when(projectDao.findAllByNameStartsWith("test2")).thenReturn(List.of(project));
        userController.getProjects("test2");
        Mockito.verify(projectDao, Mockito.times(1)).findAllByNameStartsWith("test2");
    }
}