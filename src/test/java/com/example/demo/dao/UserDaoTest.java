package com.example.demo.dao;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
class UserDaoTest {
    @Autowired
    UserDao userDao;


    @Test
    void findByLogin() {
        User user = new User("artyom", "", "", "", "");
        userDao.save(user);
        assertEquals(userDao.findByLogin("artyom").get().getLogin(), user.getLogin());
    }

    @Test
    void findByLoginAndPassword() {
        User user = new User("artyom", "password", "", "", "");
        userDao.save(user);
        assertEquals(userDao.findByLogin("artyom").get().getLogin(), user.getLogin());
        assertEquals(userDao.findByLogin("artyom").get().getPassword(), user.getPassword());
    }
}