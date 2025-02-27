package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PemissionsService extends GM3_BaseUrl {
    @Test
    void getPermissionsServices() {
        Response response = given(spec).get("/application/2/permission");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        response.then().statusCode(200);
    }
}
