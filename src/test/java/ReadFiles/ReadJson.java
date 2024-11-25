package ReadFiles;


import org.json.*;
import org.json.simple.parser.*;
import java.io.*;
public class ReadJson {
    public static void main(String[] args) throws IOException, ParseException {
        //read json file
        FileReader fr = new FileReader("/home/nandkumar/Videos/July_RestAssured/src/main/java/com/arise/Files/testing.json");
        JSONParser jp = new JSONParser();
        String jsonBody = jp.parse(fr).toString();
        System.out.println(jsonBody);

// To read the spcific keys from the json we may have to use one class - JSONOBJECT
        JSONObject js = new JSONObject(jsonBody);
        String firstname = js.getJSONArray("groupB").getJSONObject(1).get("firstName").toString();
        System.out.println(firstname);

        // read the team from groupA index-0
        String team = js.getJSONArray("groupA").getJSONObject(0).get("team").toString();
        System.out.println(team);

        //Assignment
//        1. find the team name from group-A index-0
//        2. find the last name from group-A index-1
//        3. find the first name from group-B index-1
//        4. find the salary  from group-B index-0


    }
}
