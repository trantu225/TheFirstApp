package tiwaco.thefirstapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by TUTRAN on 30/03/2017.
 */

public class MyJsonReader {

    public static JSONObject readJsonFromUrl(String jsonText) throws IOException, JSONException {

            JSONObject json = new JSONObject(jsonText);
            return json;

        }
}
