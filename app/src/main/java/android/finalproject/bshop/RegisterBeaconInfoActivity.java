package android.finalproject.bshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.finalproject.bshop.model.GetAlImages;
import android.finalproject.bshop.model.RbPreference;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterBeaconInfoActivity extends AppCompatActivity {

    private TextView beacon_id;
    private TextView shop_name;
    private TextView userid;
    private ImageView shop_picture;
    private EditText event_name;
    private EditText event_content;
    private EditText shop_coment;
    private Button register_comp_button;


    private String get_beacon_minor;
    private String get_shop_name;
    private String get_user_id;
    private String get_photo_path;
    private String get_event_name;
    private String get_event_content;
    private String get_shop_coment;

    private final String SERVER_ADDRESS = "http://210.117.181.66:8080/BShop";

    public static final String GET_IMAGE_URL="http://simplifiedcoding.16mb.com/PhotoUpload/getAllImages.php";

    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";
    //private String get_userid;
    private RbPreference mPref;
    public static final String UPLOAD_USER_ID = "user_id";
    public static final String UPLOAD_PHOTOPATH = "photo_path";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beacon_info);

        init();

        register_comp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_event_name = event_name.getText().toString();
                get_event_content = event_content.getText().toString();
                get_shop_coment = shop_coment.getText().toString();

                register_beacon_complete(get_beacon_minor, get_user_id, get_shop_name, get_photo_path, get_event_name, get_event_content, get_shop_coment);
            }
        });
    }

    private void init(){
        beacon_id = (TextView) findViewById(R.id.register_beacon_minor);
        userid = (TextView) findViewById(R.id.register_beacon_userid);
        shop_name = (TextView) findViewById(R.id.register_shop_name);
        shop_picture = (ImageView) findViewById(R.id.register_shop_image);
        event_name = (EditText) findViewById(R.id.register_event_name);
        event_content = (EditText) findViewById(R.id.register_event_content);
        shop_coment = (EditText) findViewById(R.id.register_shop_coment);
        register_comp_button = (Button) findViewById(R.id.register_beacon_complete_button);

        beacon_id = (TextView) findViewById(R.id.register_beacon_minor);

        int get_minor = getIntent().getIntExtra("minor",000000);
        beacon_id.setText(String.valueOf(get_minor));

        get_beacon_minor = String.valueOf(get_minor);
        mPref = new RbPreference(getApplicationContext());
        get_user_id = mPref.getValue("user_id","");
        userid.setText(get_user_id);

        search_shopname(get_user_id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterBeaconInfoActivity.this, RecoRangingActivity.class);
        startActivity(intent);
        finish();
    }

    private void search_shopname(String... param){
        class SearchNameThread extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterBeaconInfoActivity.this, "정보 찾는중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterBeaconInfoActivity.this,s,Toast.LENGTH_LONG).show();
                mPref = new RbPreference(RegisterBeaconInfoActivity.this);

                shop_name.setText(s.toString());
                get_shop_name = s;
                search_photopath(get_user_id);
            }

            @Override
            protected String doInBackground(String... params) {
                String mUser_id = null;
                String UPLOAD_URL = "http://210.117.181.66:8080/BShop/register_beacon_info_search.php";

                mUser_id = params[0];

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USER_ID, mUser_id);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        SearchNameThread ui = new SearchNameThread();
        ui.execute(param[0]);

    }

    private void search_photopath(String... param){
        class SearchPhotoPath extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            Bitmap bmImg;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterBeaconInfoActivity.this, "정보 찾는중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterBeaconInfoActivity.this,s,Toast.LENGTH_LONG).show();
                mPref = new RbPreference(RegisterBeaconInfoActivity.this);
                get_photo_path = s;
                shop_picture.setImageBitmap(bmImg);
                //return image;

                //shop_picture.setImageBitmap(image);
                //shop_picture.setImageBitmap(s.toString());

            }

            @Override
            protected String doInBackground(String... params) {
                String mUser_id = null;
                String UPLOAD_URL = "http://210.117.181.66:8080/BShop/register_beacon_info_search_photopath.php";

                mUser_id = params[0];

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USER_ID, mUser_id);

                String result = rh.sendPostRequest(UPLOAD_URL,data);


                try {
                    URL myFileUrl = new URL(result);
                    HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();

                    conn.setDoInput(true);

                    conn.connect();
                    InputStream is = conn.getInputStream();



                    bmImg = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result;
            }

        }

        SearchPhotoPath photo = new SearchPhotoPath();
        photo.execute(param[0]);

    }

    private void register_beacon_complete(String... param){
        class SearchNameThread extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterBeaconInfoActivity.this, "비콘 정보 저장중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterBeaconInfoActivity.this,s,Toast.LENGTH_LONG).show();
                //mPref = new RbPreference(RegisterBeaconInfoActivity.this);

                if(!s.equals("failed")){
                    Intent intent = new Intent(RegisterBeaconInfoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterBeaconInfoActivity.this, "입력 정보를 다시 확인해주세요", Toast.LENGTH_SHORT);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String mBeaconMinor, mUserId, mShopName, mShopPhoto, mEventName, mEventContent, mComent;
                String UPLOAD_URL = "http://210.117.181.66:8080/BShop/register_beacon_info_complete.php";


                mBeaconMinor = params[0];
                mUserId = params[1];
                mShopName = params[2];
                mShopPhoto = params[3];
                mEventName = params[4];
                mEventContent = params[5];
                mComent = params[6];


                HashMap<String,String> data = new HashMap<>();

                data.put("minor", mBeaconMinor);
                data.put("userid", mUserId);
                data.put("shopname", mShopName);
                data.put("photo", mShopPhoto);
                data.put("eventname", mEventName);
                data.put("eventcontent", mEventContent);
                data.put("coment", mComent);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        SearchNameThread ui = new SearchNameThread();
        ui.execute(param[0],param[1],param[2],param[3],param[4],param[5],param[6]);

    }

}
