package ReadFiles;

import org.json.*;
import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class updateJson {

    public static void main(String[] args) throws IOException, ParseException {

        //read json file

        FileReader fr = new FileReader("/home/nandkumar/Videos/July_RestAssured/src/main/java/com/arise/Files/testing.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        // to update the json
        JSONObject js = new JSONObject(requestBody);

        //update virats salary
        js.getJSONArray("groupA").getJSONObject(0).put("salary","21cr");
        //update KL rahul IPL team
        js.getJSONArray("groupB").getJSONObject(0).put("team","RCB");

        //print the updated json
        System.out.println(js);

    }
}
