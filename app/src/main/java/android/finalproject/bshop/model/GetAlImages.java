package android.finalproject.bshop.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by OHRok on 2016-06-12.
 */
public class GetAlImages {

    public static String[] imageURLs;
    public static String[] getShopNames;
    public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY_IMAGE_PATH="result_image";
    public static final String JSON_ARRAY_SHOPNAME="result_shopname";
    public static final String IMAGE_URL = "url";
    public static final String SHOP_NAME = "shopname";
    private String json;
    private JSONArray urls;
    private JSONArray names;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY_IMAGE_PATH);

            Log.e("urls", urls.toString());
            //names = jsonObject.getJSONArray(JSON_ARRAY_SHOPNAME);
            //Log.e("names", names.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            //getShopNames = jo.getString(SHOP_NAME);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];
        getShopNames = new String[names.length()];

        for(int i=0;i<urls.length();i++){
            Log.i("urls", String.valueOf(urls.length()));
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            //getShopNames[i] = names.getJSONObject(i).getString(SHOP_NAME);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
        }
    }
}