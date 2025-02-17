package requests;

import base_urls.GM3_BaseUrl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class CountryService extends GM3_BaseUrl {
    @Test
    void getCountries(){

        Response response = RestAssured
                .given(spec).get("/country/DE");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        response.prettyPrint();

        response.then().statusCode(200).body("name", Matchers.equalTo("Germany"),
                "currency",Matchers.equalTo("EUR"));

}
}
