package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class MembershipTypeServices extends GM3_BaseUrl {
    @Test
    void getUser() {


        Response response = given(spec).get("v1/user/546/application/2/membership");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then().statusCode(200);


    }
}
