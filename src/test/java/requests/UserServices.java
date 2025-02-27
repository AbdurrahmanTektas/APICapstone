package requests;

import base_urls.GM3_BaseUrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class UserServices extends GM3_BaseUrl {
    public static String userId;

    @Test
    void getCountries() {

        Response response = RestAssured.given(spec).get("/organization/1724253527891397?content=slim");

        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then()
                .statusCode(200);

        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 1)
    void userServicesPost() throws JsonProcessingException {

        String payload = """
                {
                    "app_id": 2,
                    "organization_id": 1724253527891397,
                    "email": "atek0@gmail.com",
                    "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b",
                    "default_role_id": 23,
                    "user_groups": [{"id": 833}]
                }
                """;
        Map mp = new ObjectMapper().readValue(payload, Map.class);
        String randomEmail = Faker.instance().internet().emailAddress();
        mp.put("email", randomEmail);

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(mp)
                .post("/v1/organization/user/register-manual");

        response.prettyPrint();
        response.then().statusCode(201);

        userId = response.jsonPath().getString("id");

        response.then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("app_id", Matchers.equalTo(2))
                .body("organization_id", Matchers.equalTo(1724253527891397L));

        assertEquals(response.statusCode(), 201);
        assertNotNull(response.jsonPath().getString("id"));
        assertEquals(response.jsonPath().getString("email"), randomEmail);
        assertEquals(response.jsonPath().getInt("app_id"), 2);
        assertEquals(response.jsonPath().getLong("organization_id"), 1724253527891397L);


    }

    @Test(priority = 2)
    void getUserId() {
        System.out.println(userId);
        Response response = RestAssured
                .given(spec)
                .get("/user/" + userId);

        response.prettyPrint();

        response.then()
                .statusCode(200);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    void userServicesPut() throws JsonProcessingException {

        String payload = """
                {"id": 1084, "username": "Ali998", "email": "atek95@gmail.com", "is_email_verified": false, 
                "status_id": 1, "status": {"id": 1, "name": "Active"}, "name": "NY Company", 
                "lastname": "Tek", "organization_id": 1724253527891397, 
                "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b", "is_active": true}
                """;

        Map mp = new ObjectMapper().readValue(payload, Map.class);
        mp.put("email", Faker.instance().internet().emailAddress());
        mp.put("username", Faker.instance().name().username());
        System.out.println("mp = " + mp);


        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(mp)
                .put("/v1/user");

        response.prettyPrint();

        response.then().statusCode(200)
                .body("username", Matchers.equalTo(mp.get("username")))
                .body("email", Matchers.equalTo(mp.get("email")));

        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
    void userResetPost() {
        String payload = """
                {
                    "id": 971,
                    "app_id": 2,
                    "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b",
                    "organization_id": 1724253527891397
                }
                """;

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .post("v1/user/reset-credentials");

        response.prettyPrint();

        response.then().statusCode(201)
                .body("id", Matchers.equalTo(971))
                .body("app_id", Matchers.equalTo(2));

        assertEquals(response.statusCode(), 201);
    }

    @Test(priority = 5)
    void userServicesAddRollePut() {

        String respons = """
                {
                              "id": 0,
                              "name": "John",
                              "lastname": "Doe",
                              "username": "john",
                              "email": "john@example.com",
                              "is_email_verified": true,
                              "phone": "+1 123 123 4567",
                              "address": "string",
                              "country_id": "US",
                              "pic_id": 12345,
                              "preferences": {},
                              "status_id": 12345,
                              "user_groups": [
                                {
                                  "id": 0,
                                  "name": "Sales Department",
                                  "short_name": "Sales Dept.",
                                  "group_type_id": 0,
                                  "pic_id": 12345,
                                  "organization_id": 12345,
                                  "is_head": false
                                }
                              ],
                              "created_at": "2025-02-19T09:11:25.618Z",
                              "created_by": 0,
                              "updated_at": "2025-02-19T09:11:25.618Z",
                              "updated_by": 0
                            }
                """;

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(respons)
                .put("user");

        response.prettyPrint();

        response.then().statusCode(200);

        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 6)
    void userServicesDeletId() {

        Response response = RestAssured.given(spec).delete("v1/organization/1724253527891397/user/971");

        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then()
                .statusCode(200);
        assertEquals(response.statusCode(), 200);
    }
}
