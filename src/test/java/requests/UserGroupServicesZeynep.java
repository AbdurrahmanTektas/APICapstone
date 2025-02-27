package requests;

import base_urls.GM3_BaseUrl;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserGroupServicesZeynep extends GM3_BaseUrl {
    private static int kullaniciId;
    private static final Faker faker = new Faker();
    static String email = faker.internet().emailAddress();
    static String username = faker.name().username();

    @Test(priority = 1)
    public void getUsers() {
        given()
                .spec(spec)
                .get("v1/user?size=15&sortOrder=ASC&sortBy=username&content=full&baseUri=/a3m&pageKey=0&sortKey=&page=1&pageRequest=first")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(priority = 2)
    public void createUser() {


        String requestBody = String.format("""
        {
            "app_id": 2,
            "organization_id": 1724253527891397,
            "email": "%s",
            "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b",
            "default_role_id": 5,
            "user_groups": [{"id": 479}]
        }
    """, email);

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/organization/user/register-manual");

        response.prettyPrint();
        response.then().statusCode(201).body("id", notNullValue());
        kullaniciId = response.jsonPath().getInt("id");
    }

    @Test(priority = 3, dependsOnMethods = "createUser")
    public void getUserById() {
        given()
                .spec(spec)
                .when()
                .get("v1/user/" + kullaniciId + "?content=fullTree")
                .then()
                .statusCode(200)
                .body("id", equalTo(kullaniciId));
    }

    @Test(priority = 4, dependsOnMethods = "createUser")
    public void updateUser() {
        String requestBody = String.format("""
            {
                "id": %d,
                "username": "%s",
                "email": "%s"
            }
        """, kullaniciId,username, email);


        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("v1/user")
                .then()
                .statusCode(200)
                .body("email", equalTo(email));
    }

    @Test(priority = 5, dependsOnMethods = "createUser")
    public void resetUserPassword() {
        String requestBody = String.format("""
            {
                "id": %d,
                "app_id": 2,
                "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b",
                "organization_id": 1724253527891397
            }
        """, kullaniciId);


        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("v1/user/reset-credentials")
                .then()
                .statusCode(201)
                .body("id", equalTo(kullaniciId));
    }

    @Test(priority = 6, dependsOnMethods = "createUser")
    public void addUserRole() {
        given()
                .spec(spec)
                .when()
                .put("v1/user/" + kullaniciId + "/application/2/membership/3f5f58ee-72bf-41bb-a5ba-31beceff791b/role/19/add-role")
                .then()
                .statusCode(200);
    }

    @Test(priority = 7, dependsOnMethods = "createUser")
    public void deleteUser() {
        given()
                .spec(spec)
                .when()
                .delete("v1/organization/1724253527891397/user/" + kullaniciId)
                .then()
                .statusCode(200);
    }
}

