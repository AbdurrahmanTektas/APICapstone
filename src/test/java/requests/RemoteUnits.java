package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RemoteUnits extends GM3_BaseUrl {
    @Test
    public void getSummary() {

        Response response = RestAssured.given(spec)
                .get("v1/organization/1724253527891397/user-group-type/2/summary");

        System.out.println("Status Code: " + response.statusCode());
        response.prettyPrint();

        response.then()
                .statusCode(200)
                .body("[0].group_type_name", Matchers.equalTo("Remote Unit"));
    }
    @Test
    public void postUserGroup() {

        String expectedData = """
            {
                "id": 871,
                "name": "IT Test Int",
                "short_name": "IT",
                "description": "IT",
                "group_type_id": 2,
                "organization_id": 1724253527891397,
                "roles": [
                    {
                        "id": 5
                    }
                ]
            }
        """;

        Response response = RestAssured.given(spec)
//                .contentType("application/json")
//                .accept("application/json")
                .body(expectedData)
                .post("/user-group");

        System.out.println("Status Code: " + response.statusCode());
        response.prettyPrint();

        response.then()
                .statusCode(201)
                .body("name", Matchers.equalTo("IT Test Int"))
                .body("short_name", Matchers.equalTo("IT"))
                .body("description", Matchers.equalTo("IT"));
    }
}
