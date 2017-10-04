package com.ileossa.project.api;

import com.ileossa.project.ProjectApplication;
import com.ileossa.project.api.dao.Roles;
import com.ileossa.project.api.dao.UserAccount;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.preemptive;
import static junit.framework.TestCase.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Created by ileossa on 04/10/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserTest {

    private static final String API_ROOT = "http://localhost:8080/register";

    @Before
    public void setUp(){
        RestAssured.authentication = preemptive().basic("john", "123");
    }


    private UserAccount create_random_user_account(){
        UserAccount userAccount = new UserAccount();
        userAccount.setRoles(String.valueOf(Roles.USER));
        userAccount.setEnabled(true);
        userAccount.setEmail(randomAlphabetic(5) + "@yopmail.com");
        userAccount.setFirstName(randomAlphabetic(10));
        userAccount.setLastName(randomAlphabetic(10));
        userAccount.setPassword(randomAlphabetic(10));
        userAccount.setConfirmationToken(randomAlphabetic(10));
        return userAccount;
    }

    private String new_user_account(UserAccount userAccount){
        Response response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userAccount)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }


    @Test
    public void when_get_all_user_then_ok(){
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }




}
