package TestCases;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;


/**
 * @author Nandkumar Babar
 */
public class UserStory {

    private String cookie;

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {
        FileReader fr = new FileReader("/home/nandkumar/Videos/July_RestAssured/src/main/java/com/arise/Files/jiraLogin.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);

            Response response = RestAssured.given().baseUri("http://localhost:9009/").body(requestBody)
                    .header("Content-Type","application/json")
                .when().post("/rest/auth/1/session").then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

        JSONObject js = new JSONObject(response.asString());
         cookie = "JSESSIONID="+ js.getJSONObject("session").get("value").toString();
    }
    
    @Test(priority = 2)
    public void createUserStory() throws IOException, ParseException {
        System.out.println(cookie);

        FileReader fr = new FileReader("/home/nandkumar/Videos/July_RestAssured/src/main/java/com/arise/Files/createUserStory.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9009/").body(requestBody)
                .header("Content-Type", "application/json")
                .header("Cookie",cookie).when().post("/rest/api/2/issue")
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }
    
    
}
