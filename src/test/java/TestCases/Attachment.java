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
public class Attachment {

    public static String cookie;
    private String issueId;

//    login is must (Why - we need the cookie to perform the CRUD operations

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/jiraLogin.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9008").body(requestBody)
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

        Response response = RestAssured.given().baseUri("http://localhost:9008").body(requestBody)
                .header("Content-Type", "application/json")
                .header("Cookie", cookie).when().post("/rest/api/2/issue")
                .then().log().body().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

 JSONObject js = new JSONObject(response.asString());
   issueId = js.get("key").toString();

    }

    @Test(priority = 3)
    public void addAttachment(){


        File f = new File("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/ariseLOGO.png");
        Response response = RestAssured.given().baseUri("http://localhost:9008").header("Content-Type", "multipart/form-data")
                .header("Cookie", cookie).header("X-Atlassian-Token", "no-check").multiPart("file", f)
                .when().post("/rest/api/2/issue/"+issueId+"/attachments")
                .then().log().all().extract().response();

    }

}
