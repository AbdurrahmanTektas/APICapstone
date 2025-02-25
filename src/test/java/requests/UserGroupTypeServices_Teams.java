package requests;

import base_urls.GM3_BaseUrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserGroupTypeServices_Teams extends GM3_BaseUrl {
    public static int teamsId;
    @Test(priority = 1)
    void getAllTeamDepartments(){
        Response response = given(spec).get("v1/organization/1724253527891397/user-group-type/3/summary");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
    }

    @Test(priority = 2)
    void postTeam(){
        String requestBody = """
              {
                    "name": "ApiTeamAdded2",
                    "group_type_id": 3,
                    "roles": []
                }
              """;
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/user-group")
                .then()
                .statusCode(201)
                .extract().response();
        System.out.println("Response Body:");
        response.prettyPrint();
        teamsId = Integer.parseInt(response.jsonPath().getString("id"));
    }
    @Test(priority = 3)
    void getDepartment(){
        Response response = given(spec).get("/v1/organization/1724253527891397/user-group/"+teamsId+"/details");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then().statusCode(200).body(
                "id", equalTo(teamsId));
    }
    @Test(priority = 4)
    void updateUser() throws JsonProcessingException {
        Faker faker = new Faker();
        String fakeName = faker.name().name();
        String requestBody = """
                {"id":998,"name":"ApiTeamAdded123","group_type_id":3,"organization_id":1724253527891397,"roles":[]}
                """;
        Map mapbody = new ObjectMapper().readValue(requestBody, Map.class);
        mapbody.put("id",teamsId);
        mapbody.put("name",fakeName);
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(mapbody)
                .when()
                .put("/user-group")
                .then()
                .statusCode(200)
                .extract().response();
        // Yanıtı yazdır
        System.out.println("Response Body:");
        response.prettyPrint();
    }
    @Test(priority = 5)
    void deleteUser() {
        Response response = given(spec).delete("/user-group/"+teamsId);
        System.out.println("Response Body:");
        response.prettyPrint();

    }
}
