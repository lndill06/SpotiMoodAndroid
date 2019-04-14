package com.example.spotimood;
import android.util.Log;
import android.widget.Toast;

import com.paralleldots.paralleldots.App;
import org.json.simple.JSONObject;
import org.apache.commons.io.FileUtils;
import com.example.spotimood.MainActivity;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.*;

public class Facial_Emotions
{
    static JSONParser parser = new JSONParser();
    private static String key = "sWFSPYcy1W4lt9jLblN8FnHNYKzUC5gbPQvIH6UyeUw";
    static App pd = new App(key);
    static String authToken;

    String firstT;
    String secondT;
    double firstScore;
    double secondScore;

    public String getFT(){
        return firstT;
    }
    public String getST()
    {
        return secondT;
    }
    public double getFS()
    {
        return firstScore;
    }
    public double getSS()
    {
        return secondScore;
    }

    public Facial_Emotions(String filepath, String authToken) {
        this.authToken = authToken;
        System.out.println("Facial Emotion");
        String facial_emotion;
        File plData = new File(filepath);


        double currMax = 0;
        try {

            /* Read in playlist data*/
            /*
            String buff = FileUtils.readFileToString(plData, "UTF-8");
            JSONObject data = (JSONObject) parser.parse(buff);
            JSONArray dataArray = (JSONArray) data.get("emotion");
            JSONObject firstPlaylist = (JSONObject) dataArray.get(0);
            JSONObject playlist;
            String firstP = (String) firstPlaylist.toString();
            System.out.println(firstP);
            */


            facial_emotion = pd.facial_emotion(plData.getPath());
            JSONObject obj = (JSONObject) parser.parse(facial_emotion);
            String x = obj.toString();

            JSONArray jArray = (JSONArray) obj.get("facial_emotion");

            //Print emotion data to ADB log
            Log.i("SpotiMood", "response from api" + jArray);

            /*--------------------------------------------------------------------------------*/
            JSONObject firstEntry = (JSONObject) jArray.get(0);
            JSONObject secondEntry = (JSONObject) jArray.get(1);

            firstT = (String) firstEntry.get("tag");
            firstScore = (Double) firstEntry.get("score");
            secondT = (String) secondEntry.get("tag");
            secondScore = (Double) secondEntry.get("score");

            Log.i("SpotiMood","first tag: "+ firstT +", score: "+firstScore);
            Log.i("SpotiMood","second tag: "+ secondT +", score: "+secondScore);

            if (firstScore < .75)
            {
                //Spotify sp = new Spotify(authToken);
                //TODO: get playlist using both moods

				/* Text Emotion Analysis -
				 Used to send playlist titles to the PD API, returns emotional analysis as a JSON-formatted String
				 should be commented out during normal use
				 */

//				FileWriter writer = new FileWriter("data/analysis.txt", true);
//				JSONArray text_list = (JSONArray)parser.parse("[\"The Writer's Playlist\",\"Sin Estrés\",\"Winter Sounds\",\"Boozy Brunch\",\"Country Heartache\",\"Edvard Grieg – Classical Moods: Nostalgia\",\"Soul Coffee\",\"Despierta y Sonríe\"]");
//				String emotion = pd.emotion_batch(text_list);
//				writer.write(emotion);
//				System.out.println(emotion);
//				writer.close();
            } else {
                //TODO: get playlist for first mood
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
