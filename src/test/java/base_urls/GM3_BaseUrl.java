package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class GM3_BaseUrl {

    protected static RequestSpecification spec;

    @BeforeSuite
    public void setSpec() {

        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        driver.get("https://qa-gm3.quaspareparts.com/");
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("username")).sendKeys("cstm@qualitron.com");
        driver.findElement(By.id("password")).sendKeys("NuTl2_hwkPcqOl" + Keys.ENTER);
        String gSessionId = driver.manage().getCookieNamed("GSESSIONID").getValue();
        System.out.println(gSessionId);
        driver.quit();

        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-gm3.quaspareparts.com/a3m/auth/api")
                .addHeader("Cookie", "GSESSIONID=" + gSessionId)
                .build();
    }
/*
    @AfterSuite
    public void executeReport() throws IOException, InterruptedException {
        // Allure komutunu çalıştır
        Thread.sleep(3000);
        ProcessBuilder processBuilder = new ProcessBuilder("allure", "serve", "allure-results");
        processBuilder.inheritIO();
        // Konsol çıktısını göster
//        Process başlat
        Process process = processBuilder.start();
        // Process'in tamamlanmasını bekle
        process.waitFor();


    }

 */

    @AfterSuite
    public void generateAllureReport() throws IOException {
        new ProcessBuilder().command("cmd.exe", "/c", "allure serve").start();
    }
}