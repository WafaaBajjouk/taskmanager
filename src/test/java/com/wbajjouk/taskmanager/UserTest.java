package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.usermanagement.UserRequest;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;
import com.wbajjouk.taskmanager.usermanagement.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserTest {

    @Autowired
    private UserService userService;


//    private UserRequest request = new UserRequest();

//    @BeforeEach
//    void setUp(){
//        request.setUsername("Test User");
//        request.setEmail("testemail@gmail.com");
//        request.setRole("ADMIN");
//    }

    @Test
    public void testSaveUser() {
        UserRequest request = new UserRequest();

        request.setUsername("Test User X");
        request.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        request.setRole("ADMIN");
        request.setPasswordHash("PasswordHash");

        UserResponse response = userService.saveUser(request);
        List<UserResponse> allUsers = userService.getAllUsers();
        Optional<UserResponse> gotten = allUsers.stream()
                .filter(u -> u.userId == response.userId)
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("Test User X", gotten.get().username);
    }


    public static class TestUserId {

        private static final Random RANDOM = new Random();

        public static long nextId() {
            return RANDOM.nextLong();
        }
    }
}
