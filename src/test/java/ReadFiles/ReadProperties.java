package ReadFiles;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class ReadProperties {

    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/july_restassured/src/main/java/com/arise/Files/file.properties");
        Properties pr = new Properties();
        pr.load(fr);

      String url = pr.getProperty("url");
        System.out.println(url);

      String username = pr.getProperty("username");
        System.out.println(username);


    }
}
