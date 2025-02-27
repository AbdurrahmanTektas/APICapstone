package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DepartmentServices extends GM3_BaseUrl {

    public static String depId;

    @Test
    void getDepartments(){
        Response response = RestAssured
                .given(spec).get("/v1/organization/1724253527891397/user-group-type/8/summary");

        response.prettyPrint();


        response.then().statusCode(200).body("group_type_name[0]", equalTo("Department"));
    }

    @Test(priority = 1)
    void createDepartment(){

        String json = """
                {
                     "name": "Bilgi Teknolojileri Departmani",
                     "short_name": "BT",
                     "group_type_id": 8,
                     "roles":  [{"id": 5}]
                 }
                """;

        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .body(json)
                .post("/user-group");

        System.out.println("Response Body: " + response.getBody().asString());


        response.then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Bilgi Teknolojileri Departmani"))
                .body("short_name", equalTo("BT"))
                .body("group_type_id", equalTo(8))
                .body("roles[0].id", equalTo(5));

       depId = response.jsonPath().getString("id");

    }

    @Test(priority = 2, dependsOnMethods = "createDepartment")
    void getByIdDepartment(){

        System.out.println(depId);

        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .get("/v1/organization/1724253527891397/user-group/" + depId + "/details");

        System.out.println("GET Response Body: " + response.getBody().asString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(depId)))
                .body("name", equalTo("Bilgi Teknolojileri Departmani"))
                .body("short_name", equalTo("BT"))
                .body("group_type_id", equalTo(8))
                .body("roles[0].id", equalTo(5));
    }

    @Test(priority = 2, dependsOnMethods = "createDepartment")
    void updateDepartment() {
        String json = String.format("""
        {
            "id": %s,
            "name": "Güncellenmiş BT Departmanı",
            "short_name": "GBTD",
            "group_type_id": 8
            ,
            "organization_id": 1724253527891397,
            "roles": [
                { "id": 5 }
            ]
        }
        """, depId);

        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .body(json)
                .put("/user-group");

        response.then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(depId)))
                .body("name", equalTo("Güncellenmiş BT Departmanı"))
                .body("short_name", equalTo("GBTD"))
                .body("group_type_id", equalTo(8))
                .body("roles[0].id", equalTo(5));
    }

    @Test(priority = 3, dependsOnMethods = "updateDepartment")
    void deleteDepartment() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .delete("/user-group/" + depId);

        response.then().statusCode(200);
    }


    @Test(priority = 4, dependsOnMethods = "deleteDepartment")
    void verifyDepartmentDeleted() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .get("/v1/organization/1724253527891397/user-group/" + depId + "/details");

        response.then()
                .statusCode(406);
    }


}
