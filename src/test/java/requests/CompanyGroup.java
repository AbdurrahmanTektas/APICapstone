package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CompanyGroup extends GM3_BaseUrl {
    @Test
    void getCompanyGroup(){

        Response response = RestAssured
                .given(spec).get("/organization-group?content=full");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();

        response.then().statusCode(200)
                .body("[0].id", Matchers.equalTo(2),
                "[0].name", Matchers.equalTo("Acme Company Group Edited"))
                .body("[0].short_name", Matchers.equalTo("Acme CG"));

        assertEquals(response.statusCode(), 200);
    }
    }
