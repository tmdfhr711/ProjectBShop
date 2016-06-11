package android.finalproject.bshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.finalproject.bshop.model.RbPreference;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final String SERVER_ADDR = "http://210.117.181.66:8080/BShop";


    private Button sign_in_btn;
    private Button sign_up_btn;

    private EditText user_id_edt;
    private EditText password_edt;

    private CheckBox auto_login;

    public static final String UPLOAD_USER_ID = "user_id";
    public static final String UPLOAD_USER_PASS = "password";
    public RbPreference mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        user_id_edt = (EditText) findViewById(R.id.user_id);
        password_edt = (EditText) findViewById(R.id.password);
        sign_in_btn = (Button) findViewById(R.id.sign_in_button);
        sign_up_btn = (Button) findViewById(R.id.sign_up_button);
        auto_login = (CheckBox) findViewById(R.id.auto_login_checkbox);

        sign_up_btn.setOnClickListener(this);
        sign_in_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("Onclick", "Onclick");
        if (v == sign_in_btn) {
            Log.e("Onclick", "sign_in_btn");
            String mUser_id = null;
            String mPassword = null;
            try{
                Log.e("Onclick", "sign_in_btn_try");
                mUser_id = URLEncoder.encode(user_id_edt.getText().toString(), "UTF-8");
                mPassword = URLEncoder.encode(password_edt.getText().toString(), "UTF-8");
                if(mUser_id != null && mPassword != null) {
                    signIn(mUser_id, mPassword);

                }else {
                    Toast.makeText(LoginActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT);
                }
            } catch(Exception e){
                Log.e("Onclick", "sign_in_btn_catch");
                e.printStackTrace();
            }

        } else if (v == sign_up_btn) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void signIn(String... param){
        class SignInThread extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "로그인 중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(!s.equals("failed")){
                    Toast.makeText(LoginActivity.this,s + "님 환영합니다.",Toast.LENGTH_LONG).show();
                    mPref = new RbPreference(LoginActivity.this);
                    if(auto_login.isChecked()){
                        mPref.put("auto_login", true);
                        mPref.put("is_login", true);
                    }
                    mPref.put("user_id",s);
                    mPref.put("is_login", true);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"입력하신 정보를 다시 확인해주세요.",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {
                String mUser_id = null;
                String mPassword = null;
                String UPLOAD_URL = "http://210.117.181.66:8080/BShop/sign_in.php";

                mUser_id = params[0];
                mPassword = params[1];

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USER_ID, mUser_id);
                data.put(UPLOAD_USER_PASS, mPassword);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        SignInThread ui = new SignInThread();
        ui.execute(param[0],param[1]);

    }
}

