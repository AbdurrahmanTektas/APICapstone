package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RemoteUnits extends GM3_BaseUrl {
    @Test
    public void getRemoteUnits() {

        Response response = RestAssured.given(spec)
                .get("v1/organization/1724253527891397/user-group-type/2/summary");

        System.out.println("Status Code: " + response.statusCode());
        response.prettyPrint();

        response.then()
                .statusCode(200)
                .body("[0].group_type_name", Matchers.equalTo("Remote Unit"));
        assertEquals(response.statusCode(), 200);
    }
    @Test (priority = 1)
    void createRemoteUnits() {
        String payload = """
               {"name":"Test Deneme","group_type_id":2,"roles":[]}
        """;

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .post("user-group-type");

        response.prettyPrint();
        response.then().statusCode(201)
                .body("name", Matchers.equalTo("Test Deneme"));

        assertEquals(response.statusCode(), 201);
    }
    @Test
    void getRemoteUnitsId() {
        Response response = RestAssured
                .given(spec)
                .get("/v1/organization/1724253527891397/user-group/871/details");

        response.prettyPrint();
        response.then().statusCode(200)
                .body("name", Matchers.equalTo("IT Test"))
                .body("group_type_name", Matchers.equalTo("Remote Unit"));
        assertEquals(response.statusCode(), 200);
    }
    @Test (priority = 2)
    void updateRemoteUnits() {
        String payload = """
                {
                            "id": 1,
                            "name": "Remote unit1",
                            "description": "Organization unit which is not located within company premises, such as remote office, remote branch or warehouse etc."
                          }
        """;

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .put("user-group-type");

        response.prettyPrint();
        response.then().statusCode(200)
                .body("id",Matchers.equalTo(1))
                .body("name",Matchers.equalTo("Remote unit1"));
        assertEquals(response.statusCode(), 200);
    }
    @Test (priority = 3)
    void deleteRemoteUnits() {

        Response response = RestAssured
                .given(spec)
                .delete("user-group/912");

        response.prettyPrint();

        response.then().statusCode(200);
        assertEquals(response.statusCode(), 200);

    }
}
