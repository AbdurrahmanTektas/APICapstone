package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrganizationStatusServices extends GM3_BaseUrl {



    @Test
    void getStatusServices() {
        Response response = given(spec).get("/v1/organization/1724253527891397/summary");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    void putStatusServices(){
        String requestBody = """
                {
                  "id": 1724253527891397,
                  "name": "NioyaTech Company1",
                  "founder_id": 24,
                  "short_name": "_",
                  "country_id": "DE",
                  "created_at": "2024-08-21T15:18:47.879567+00:00",
                  "created_by": 24,
                  "email": "aa.com",
                  "group_types": [
                    {
                      "id": 1,
                      "name": "Department",
                      "number_of_groups": 10,
                      "number_of_users": 5
                    }
                  ],
                  "number_of_applications": 1,
                  "number_of_files": 0,
                  "number_of_groups": 58,
                  "number_of_users": 36,
                  "status_id": 1,
                  "updated_at": "2025-02-12T10:58:15.433771+00:00",
                  "updated_by": 24,
                  "website": "www.acme.com"
                }
                """;
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put("/organization")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Response Body:");
        response.prettyPrint();
    }
}
