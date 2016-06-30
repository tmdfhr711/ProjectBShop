package android.finalproject.bshop.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by OHRok on 2016-06-13.
 */
public class GetAllShopName {
    public static String[] getShopNames;

    public static final String JSON_ARRAY_IMAGE_PATH="result_image";
    public static final String JSON_ARRAY_SHOPNAME="result_shopname";
    public static final String IMAGE_URL = "url";
    public static final String SHOP_NAME = "shopname";
    private String json;
    private JSONArray urls;
    private JSONArray names;

    public GetAllShopName(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            //urls = jsonObject.getJSONArray(JSON_ARRAY_IMAGE_PATH);
            names = jsonObject.getJSONArray(JSON_ARRAY_SHOPNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getShopName(JSONObject jo){
        String get_name = null;
        try {
            get_name = jo.getString(SHOP_NAME);
            //image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return get_name;
    }

    public void getAllShopName() throws JSONException {
        //bitmaps = new Bitmap[urls.length()];

        //imageURLs = new String[urls.length()];
        getShopNames = new String[names.length()];

        for(int i=0;i<names.length();i++){
            Log.i("urls", String.valueOf(names.length()));
            //imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            getShopNames[i] = names.getJSONObject(i).getString(SHOP_NAME);
            //JSONObject jsonObject = urls.getJSONObject(i);
            //bitmaps[i]=getShopName(jsonObject);
        }
    }
}
