package ZAPTEST;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class TestZAP {
    static String ZAP_PROXY_ADDRESS = "localhost";
    static int ZAP_PROXY_PORT = 8080;
    static String ZAP_API_KEY = "5ikcvvhjl3hqi43no0hmb63bn";

    WebDriver webDriver;
    ClientApi clientApi;

    @BeforeMethod
    public void setup(){
        String proxyServerURL = ZAP_PROXY_ADDRESS + ":" + ZAP_PROXY_PORT;

        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyServerURL);
        proxy.setSslProxy(proxyServerURL);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setProxy(proxy);
        chromeOptions.setAcceptInsecureCerts(true);
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver(chromeOptions);
        clientApi = new ClientApi(ZAP_PROXY_ADDRESS, ZAP_PROXY_PORT, ZAP_API_KEY);
    }

    @Test
    public void amazonTesting(){
        webDriver.get("https://frontend.nopcommerce.com/");
        Assert.assertEquals(webDriver.getCurrentUrl(), webDriver.getCurrentUrl());
    }


    @AfterMethod
    public void tearDown() throws ClientApiException {
        if (clientApi != null){
            clientApi.reports.generate("NopeTestReport","traditional-html",null,"NopeTestReport",null,
                    null, null, null, null, "NopeTestReport",null, System.getProperty("user.dir") ,null);
        }
        webDriver.quit();
    }



}
