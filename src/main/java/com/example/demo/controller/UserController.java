package com.example.demo.controller;

import com.example.demo.dao.PictureDao;
import com.example.demo.dao.ProjectDao;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Picture;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.dao.UserDao;
import com.example.demo.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private PictureDao pictureDao;

    User checkIfUserExists(String login, String password) {
        return userDao.findByLoginAndPassword(login, password).orElseThrow(
                () -> new ResourceNotFoundException("User with such username and password doesn't exist: " + login));
    }

    Project checkIfProjectExists(User user, String projectName) {
        return projectDao.findByUseridAndName(user, projectName).orElseThrow(
                () -> new ResourceNotFoundException("Project with such name doesn't exist: " + projectName));
    }

    @GetMapping("/users/login")
    public ResponseEntity<User> login(@RequestParam String login, @RequestParam String password) {
        User user = checkIfUserExists(login, password);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/signup")
    public ResponseEntity<User> signup(@RequestBody User user, @RequestParam String login) {
        if (userDao.findByLogin(login).isPresent()) {
            throw new ResourceNotFoundException("User with such username already exist: " + login);
        } else {
            userDao.save(user);
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/users/get_user")
    public ResponseEntity<User> getUser(@RequestParam String login) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login + " "));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam String login, @RequestParam String password) {
        User updatedUser = checkIfUserExists(login, password);
        updatedUser.setFullname(user.getFullname());
        updatedUser.setDescription(user.getDescription());
        updatedUser.setPhoto(user.getPhoto());
        userDao.save(updatedUser);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/users/update_project")
    public ResponseEntity<User> updateUserProject(@RequestBody ProjectRepresentation projectRepresentation,
                                                  @RequestParam String login,
                                                  @RequestParam String password,
                                                  @RequestParam String initial_project_name) {
        User updatedUser = checkIfUserExists(login, password);

        Project updatedProject = projectDao.findByUseridAndName(updatedUser, initial_project_name).orElseThrow(
                () -> new ResourceNotFoundException("Project doesn't exist: " + initial_project_name));

        if (projectRepresentation.getName().length() == 0) {
            throw new ResourceNotFoundException("Project name can't be empty");
        }

        if ((!initial_project_name.equals(projectRepresentation.getName())) &&
                projectDao.findByUseridAndName(updatedUser, projectRepresentation.getName()).isPresent()) {
            throw new ResourceNotFoundException("Project with such name already exist: " + projectRepresentation.getName());
        }
        pictureDao.deleteById(pictureDao.findAllByProjectid(updatedProject));
        projectDao.delete(updatedProject.getId());
        updatedUser.setProjects(projectDao.findAllByUserid(updatedUser));
        return addNewProjectFromRepresentation(projectRepresentation, updatedUser);
    }

    private ResponseEntity<User> addNewProjectFromRepresentation(@RequestBody ProjectRepresentation projectRepresentation, User updatedUser) {
        Project project = new Project();
        project.setName(projectRepresentation.getName());
        project.setAccess_level(projectRepresentation.getAccess_level());
        project.setDescription(projectRepresentation.getDescription());
        project.setUserid(updatedUser);
        projectDao.save(project);
        for (var picture : projectRepresentation.getPictures()) {
            Picture pic = new Picture(picture.getSrc());
            pic.setProjectid(project);
            pictureDao.save(pic);
        }
        project.setPictures(pictureDao.findAllByProjectid(project));
        updatedUser.setProjects(projectDao.findAllByUserid(updatedUser));
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/users/add_project")
    public ResponseEntity<User> addUserProject(@RequestBody ProjectRepresentation projectRepresentation,
                                               @RequestParam String login,
                                               @RequestParam String password) {
        User updatedUser = checkIfUserExists(login, password);

        if (projectRepresentation.getName().length() == 0) {
            throw  new ResourceNotFoundException("Project name can't be empty");
        }

        if (projectDao.findByUseridAndName(updatedUser, projectRepresentation.getName()).isPresent()) {
            throw new ResourceNotFoundException("Project with such name already exist: " + projectRepresentation.getName());
        } else {
            return addNewProjectFromRepresentation(projectRepresentation, updatedUser);
        }
    }

    @GetMapping("/users/delete_project")
    public ResponseEntity<User> deleteUserProject(@RequestParam String projectName,
                                                  @RequestParam String login,
                                                  @RequestParam String password) {
        User updatedUser = checkIfUserExists(login, password);
        Project project = checkIfProjectExists(updatedUser, projectName);
        pictureDao.deleteById(pictureDao.findAllByProjectid(project));
        projectDao.delete(project.getId());
        updatedUser.setProjects(projectDao.findAllByUserid(updatedUser));
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/users/get_user_project")
    public ResponseEntity<List<Project>> getUserProjects(@RequestParam String name,
                                                         @RequestParam String login,
                                                         @RequestParam String password) {
        User updatedUser = checkIfUserExists(login, password);
        return ResponseEntity.ok(projectDao.findAllByUseridAndNameStartsWith(updatedUser, name));
    }

    @GetMapping("/users/get_project")
    public ResponseEntity<List<Project>> getProjects(@RequestParam String name) {
        return ResponseEntity.ok(projectDao.findAllByNameStartsWith(name));
    }

}
