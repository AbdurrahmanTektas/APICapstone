package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class UserService extends GM3_BaseUrl {
    @Test
    void getCountries(){

        Response response= RestAssured.given(spec).get("/country");


        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();

//        response.then()
//                .statusCode(200)
//                .body("name", Matchers.equalTo("Germany"),
//                        "currncy", Matchers.equalTo("EUR"));
    }

}
