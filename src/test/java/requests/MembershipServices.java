package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class MembershipServices extends GM3_BaseUrl {

    @Test
    void denem(){
        System.out.println("calisiyor");
    }

    @Test
    void getSubsciptions(){

        Response response = RestAssured
                .given(spec).get("v1/user/546/application/2/subscription");


        response.then().statusCode(200).body("subscription_id", Matchers.notNullValue());
    }

    @Test
    void negatifGetSubscriptions(){
        Response response = RestAssured
                .given(spec).get("v1/user/800/application/2/subscription");

        response.prettyPrint();


        response.then().statusCode(200).body("",Matchers.hasSize(0));
    }
}
