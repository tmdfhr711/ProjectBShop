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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    private static final String TAG_RESULT = "result_info";
    private static final String TAG_NAME = "shopname";
    private static final String TAG_PHOTO = "photo";

    JSONArray arrShopInfo = null;

    ArrayList<HashMap<String, String>> infoList;

    String myJSON;

    private static final String url = "http://210.117.181.66:8080/BShop/get_shopinfo_db.php";

    public GetAlImages getAlImages;

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
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

        infoList = new ArrayList<HashMap<String, String>>();

        //minor값 받아오기
        int get_minor = getIntent().getIntExtra("minor",000000);
        beacon_id.setText(String.valueOf(get_minor));

        //minor값 int로 변경
        get_beacon_minor = String.valueOf(get_minor);
        mPref = new RbPreference(getApplicationContext());
        //user_id값 가져오기
        get_user_id = mPref.getValue("user_id","");
        userid.setText(get_user_id);

        getData(url,get_user_id);
        //get_shopinfo_function(get_user_id);
        //search_shopname(get_user_id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterBeaconInfoActivity.this, RecoRangingActivity.class);
        startActivity(intent);
        finish();
    }

    /*
     * 받아온 JSON형태를 KEY,VALUE 형태롤 변형 한 뒤 가게명 및 사진을 화면에 보여준다.
     */
    private void showData(){
        class GetShopPhoto extends AsyncTask<String, Void, String>{

            Bitmap shopBitmap = null;
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                shop_name.setText(infoList.get(0).get(TAG_NAME));
                shop_picture.setImageBitmap(shopBitmap);
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    arrShopInfo = jsonObj.getJSONArray(TAG_RESULT);

                    for (int i = 0; i < arrShopInfo.length(); i++) {
                        JSONObject c = arrShopInfo.getJSONObject(i);
                        String name = c.getString(TAG_NAME);
                        String photo = c.getString(TAG_PHOTO);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_NAME, name);
                        map.put(TAG_PHOTO, photo);

                        infoList.add(map);
                    }
                    Log.e("infoList.get(0)", infoList.get(0).toString());
                    Log.e("name = ", infoList.get(0).get(TAG_NAME));
                    Log.e("photo = ", infoList.get(0).get(TAG_PHOTO));


                    get_photo_path = infoList.get(0).get(TAG_PHOTO);
                    get_shop_name = infoList.get(0).get(TAG_NAME);
                    URL photoUrl = new URL(infoList.get(0).get(TAG_PHOTO));
                    HttpURLConnection connection  = (HttpURLConnection) photoUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    shopBitmap = BitmapFactory.decodeStream(input);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        GetShopPhoto getShopPhoto = new GetShopPhoto();
        getShopPhoto.execute("");
    }


    /*
     * user_id를 넘겨 JSON 형식으로 넘겨, 가게명, 사진경로를 JSON으로 받아와 ShowData()를 호출한다.
     */
    private void getData(String url,String user_id){
        class GetShopInfoJSON extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterBeaconInfoActivity.this, "정보 찾는중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();
                myJSON = result;
                Log.e("result", result);
                showData();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String user_id = params[1];
                BufferedReader bufferedReader = null;

                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);

                    HashMap<String,String> data = new HashMap<>();

                    data.put(UPLOAD_USER_ID, user_id);

                    String result  = rh.sendPostRequest(uri,data);
                    //
                    // user_id 보내기 완료
                    //
                    return result;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetShopInfoJSON get = new GetShopInfoJSON();
        get.execute(url,user_id);
    }


    /*
     * 모든 정보를 입력 받은 뒤 정보를 JSON 형태로보내 MYSQL에 저장하는 함수
     */
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
                //Toast.makeText(RegisterBeaconInfoActivity.this,s,Toast.LENGTH_LONG).show();
                //mPref = new RbPreference(RegisterBeaconInfoActivity.this);

                if(!s.equals("failed")){
                    Toast.makeText(RegisterBeaconInfoActivity.this,s,Toast.LENGTH_LONG).show();
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

                Log.e("postValue",mBeaconMinor+","+mUserId+","+mShopName+","+mShopPhoto+","+mEventName+","+mEventContent+","+mComent);

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
