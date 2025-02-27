package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class UserStatusServices extends GM3_BaseUrl {
    @Test
    public void getUserStatusServicesTest() {
        Response response = RestAssured.given(spec)
                .get("user-status");

        response.then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Active"))
        ;

        System.out.println("Response: " + response.asString());
        assertEquals(response.statusCode(), 200);
    }
    @Test
    public void getUserStatusServicesIdTest() {
        Response response = RestAssured.given(spec)
                .get("user-status/1");

        response.then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Active"));

        System.out.println("Response: " + response.asString());

        assertEquals(response.statusCode(), 200);
    }
}
