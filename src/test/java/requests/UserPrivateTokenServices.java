package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserPrivateTokenServices extends GM3_BaseUrl {

    public static String tokenId;

    @Test(priority = 0)
    void getUserTokens() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .get("/user-private-token?app_id=2");

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 1)
    void createUserToken() {
        String json = """
                {
                    "user_id": 546,
                    "username": "cstm",
                    "app_id": 2,
                    "subscription_id": "3f5f58ee-72bf-41bb-a5ba-31beceff791b",
                    "is_enabled": true,
                    "user_secret_expires_days_after": 30,
                    "scopes": ["inventory:read"]
                }
                """;

        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .body(json)
                .post("/user-private-token");

        response.prettyPrint();

        response.then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("scopes", hasItem("inventory:read"));

        tokenId = response.jsonPath().getString("id");
    }

    @Test(priority = 2, dependsOnMethods = "createUserToken")
    void getUserTokenById() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .get("/user-private-token/" + tokenId + "?app_id=2");

        response.prettyPrint();
        response.then()
                .statusCode(200)
                .body("id", equalTo(tokenId));
    }

    @Test(priority = 3, dependsOnMethods = "createUserToken")
    void deleteUserToken() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .delete("/user-private-token/" + tokenId + "?app_id=2");

        response.prettyPrint();
        response.then().statusCode(200);
    }

}
