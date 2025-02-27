package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class RolleService extends GM3_BaseUrl {
    @Test
    public void getAllRolesTest() {
        Response response = RestAssured.given(spec)
                .get("/role");

        response.then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(23))
                .body("[0].name", Matchers.equalTo("Accountant"));

        System.out.println("Response: " + response.asString());
        assertEquals(response.statusCode(), 200);
    }
    @Test
    public void getRoleByIdTest() {
      int appId = 123;
        Response response = RestAssured.given(spec)
                .get("role/23");

        response.then()
                .statusCode(200)
                .body("id", Matchers.equalTo(23))
                .body("name", Matchers.equalTo("Accountant"));

        System.out.println("Response: " + response.asString());

        assertEquals(response.statusCode(), 200);
    }
}
