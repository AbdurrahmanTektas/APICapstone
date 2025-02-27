package requests;

import base_urls.GM3_BaseUrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserServicesFethi extends GM3_BaseUrl {
    public static int userId;
    @Test(priority = 1)
    void getAllUsers() {
        Response response = given(spec).get("/v1/organization/1724253527891397/user-group-type/3/summary");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();

    }

    @Test(priority = 2)
    void createNewUser() throws JsonProcessingException {
        Faker faker = new Faker();
        String fakeEmail = faker.internet().emailAddress();
        String fakeUsername = faker.name().username();
        String requestBody = """
              {"app_id":2,"organization_id":1724253527891397,"email":"rastgele@gmail.com","subscription_id":"3f5f58ee-72bf-41bb-a5ba-31beceff791b",
              "default_role_id":5,"user_groups":[{"id":833}]}""";
        Map mapbody = new ObjectMapper().readValue(requestBody, Map.class);
        mapbody.put("id",userId);
        mapbody.put("email",fakeEmail);
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/v1/organization/user/register-manual")
                .then()
                .statusCode(201)
                .extract().response();
        System.out.println("Response Body:");
        response.prettyPrint();
        userId = Integer.parseInt(response.jsonPath().getString("id"));
    }

    @Test(priority = 3)
    void getUser() {
        Response response = given(spec).get("/v1/user/"+userId);
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then().statusCode(200).body(
                "id", equalTo(userId));
    }

    @Test(priority = 4)
    void updateUser() throws JsonProcessingException {
        Faker faker = new Faker();
        String fakeEmail = faker.internet().emailAddress();
        String fakeUsername = faker.name().username();
        String requestBody = """
        {"id":931,"username":"fethi123@gmail.com","email":"fethi@gmail.com","is_email_verified":false,"status_id":1,"status":{"id":1,"name":"Active","description":"User account is activated and authorized to use the application"},"organizations":[{"id":1724253527891397,"name":"NioyaTech Company1","founder_id":24,"short_name":"_","country_id":"DE","email":"aa.com","website":"www.acme.com","status_id":1,"created_at":"2024-08-21T15:18:47.879567Z","created_by":24,"updated_at":"2025-02-19T13:24:48.133455Z","updated_by":24}],"user_groups":[{"id":833,"name":"Test Department","short_name":"IT","group_type_id":2,"organization_id":1724253527891397,"is_head":false}],"roles":[{"role_id":5,"name":"Business Owner","subscription_id":"3f5f58ee-72bf-41bb-a5ba-31beceff791b","is_default":true}],"applications":[{"id":2,"name":"Quaspareparts Gateway App","short_name":"Quaspareparts","description":"Cloud Gateway Reverse Proxy Service","base_uri":"https://qa-gm3.quaspareparts.com","domain_name":"gm3.quaspareparts.com","environment":"Development","default_membership_type_id":6,"is_enabled":true,"is_subscription_required":false,"is_self_registration_enabled":false,"is_password_recovery_enabled":true,"is_external_idp_login_enabled":false,"is_mfa_enabled":false,"subscription_path":"-","login_path":"/auth/login","logo_id":2,"created_at":"2023-01-17T19:50:28.204649Z","updated_at":"2024-03-11T11:09:14.589300Z","updated_by":1}],"created_at":"2025-02-10T09:30:17.019206Z","created_by":546,"updated_at":"2025-02-10T09:30:17.019207Z","updated_by":546,"organization_id":1724253527891397,"subscription_id":"3f5f58ee-72bf-41bb-a5ba-31beceff791b","is_active":true,"membership_created_at":"2025-02-19T14:20:58.147424Z","membership_updated_at":"2025-02-19T14:20:58.147424Z"}                """;// RestAssured ile POST isteğini yap
        Map mapbody = new ObjectMapper().readValue(requestBody, Map.class);
        mapbody.put("id",userId);
        mapbody.put("username",fakeUsername);
        mapbody.put("email",fakeEmail);
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(mapbody)
                .when()
                .put("/v1/user")
                .then()
                .statusCode(200)
                .extract().response();

        // Yanıtı yazdır
        System.out.println("Response Body:");
        response.prettyPrint();
    }

    @Test(priority = 5)
    void deleteUser() {
        Response response = given(spec).delete("/user-group/"+userId);
        System.out.println("Response Body:");
        response.prettyPrint();

    }

}
