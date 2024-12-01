package TestCases;

import io.restassured.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class Bug {

    public static String cookie;
    private String issueId;
    private String url;

//    login is must (Why - we need the cookie to perform the CRUD operations

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/jiraLogin.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        FileReader readFile = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/file.properties");
        Properties pr = new Properties();
        pr.load(readFile);
        url = pr.getProperty("url");


        Response response = RestAssured.given().baseUri(url).body(requestBody)
                .header("Content-Type", "application/json")
                .when().post("/rest/auth/1/session").then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

        JSONObject js =new JSONObject(response.asString());
         cookie = "JSESSIONID="+js.getJSONObject("session").get("value").toString();

    }

    @Test(priority = 2)
    public void createBug() throws IOException, ParseException {

  FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/CreateBug.json");
  JSONParser jp = new JSONParser();
    String requestBody =  jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri(url).body(requestBody)
                .header("Content-Type", "application/json")
                .header("Cookie", cookie).when().post("/rest/api/2/issue")
                .then().log().body().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

 JSONObject js = new JSONObject(response.asString());
   issueId = js.get("key").toString();

    }

    @Test(priority = 3)
    public void getBug(){

        Response response = RestAssured.given().baseUri(url).header("Content-Type", "application/json")
                .header("Cookie", cookie).when().get("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }

    @Test(priority = 4)
    public void updateBug() throws IOException, ParseException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/CreateBug.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        JSONObject updatedBody = new JSONObject(requestBody);
        updatedBody.getJSONObject("fields").put("summary","update the current bug");

        Response response = RestAssured.given().baseUri(url)
                .header("Content-Type", "application/json")
                .header("Cookie", cookie).body(updatedBody.toString())
                .when().put("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.getStatusCode());

    }

    @Test(priority = 5)
    public void deleteBug(){

        Response response = RestAssured.given().baseUri(url)
                .header("Content-Type", "application/json")
                .header("Cookie", cookie)
                .when().delete("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.getStatusCode());
    }
}
