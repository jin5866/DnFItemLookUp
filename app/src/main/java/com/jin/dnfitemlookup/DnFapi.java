
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by SSA on 2017-12-14.
 */

public class DnFapi {

    String apiURL = "https://api.neople.co.kr";
    String apiKey = "5j7OCSLo8bXCI2p44DKIeQhowxOpASWk";

    String serverName = "";

    String characterName = "";
    String characterId = "";

    String searchCharFormat = "/df/servers/%s/charaters?characterName=%s&apikey=%s";//서버,닉네임,api키


    public static DnFapi instance = null;

    public static DnFapi GetInstance()
    {
        if(instance == null)
        {
            instance = new DnFapi();
        }
        return instance;
    }

    private DnFapi()
    {

    }

    public void SetCharName(String name)
    {
        this.characterName = name;
    }


    public static String encodeURIComponent(String component)   {
        String result = null;

        try {
            result = URLEncoder.encode(component, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            result = component;
        }
            return result;
    }


    String GetSearchCharUrl()
    {
        return encodeURIComponent(String.format(searchCharFormat,serverName,characterName,apiKey));
    }

    String getHttpHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
