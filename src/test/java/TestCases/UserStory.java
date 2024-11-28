package TestCases;

import io.restassured.*;
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
    private String issueID;

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {
        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/jiraLogin.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);

            Response response = RestAssured.given().baseUri("http://localhost:9008/").body(requestBody)
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

        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/createUserStory.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9008/").body(requestBody)
                .header("Content-Type", "application/json")
                .header("Cookie",cookie).when().post("/rest/api/2/issue")
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

        JSONObject js = new JSONObject(response.asString());
        issueID =  js.get("key").toString();

    }

    @Test(priority = 3)
    public void getUserStory(){

        Response response =RestAssured.given().baseUri("http://localhost:9008/")
                .header("Content-Type", "application/json")
                .header("Cookie",cookie).when().get("/rest/api/2/issue/"+issueID)
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }

    @Test(priority = 4)
    public void updateUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/createUserStory.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        JSONObject updatedJson = new JSONObject(requestBody);
        updatedJson.getJSONObject("fields").put("summary","updating the user story");

        Response response = RestAssured.given().baseUri("http://localhost:9008/").header("Content-Type", "application/json")
                .header("Cookie", cookie).body(updatedJson.toString()).when().put("/rest/api/2/issue/"+issueID)
                .then().extract().response();

        System.out.println(response.getStatusCode());
    }

    @Test(priority = 5)
    public void deleteUserStory(){

        Response response = RestAssured.given().baseUri("http://localhost:9008/").header("Content-Type", "application/json")
                .header("Cookie", cookie).when().delete("/rest/api/2/issue/" + issueID)
                .then().extract().response();

        System.out.println(response.getStatusCode());

    }
    
    
}
