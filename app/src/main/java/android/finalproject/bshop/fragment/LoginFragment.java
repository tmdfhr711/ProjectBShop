package android.finalproject.bshop.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.finalproject.bshop.MainActivity;
import android.finalproject.bshop.RegistShopActivity;
import android.finalproject.bshop.RequestHandler;
import android.finalproject.bshop.model.ConnectDatabaseThread;
import android.finalproject.bshop.model.RbPreference;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.finalproject.bshop.R;
import android.widget.Toast;

import java.util.HashMap;

public class LoginFragment extends Fragment {

    private Button login_button;
    private Button sign_up_button;
    private TextView user_id;
    private TextView user_pass;
    private CheckBox auto_login;

    public static final String UPLOAD_USER_ID = "user_id";
    public static final String UPLOAD_USER_PASS = "password";
    public RbPreference mPref;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_login, container, false);
        login_button = (Button) rootView.findViewById(R.id.sign_in_button);
        sign_up_button = (Button) rootView.findViewById(R.id.sign_up_button);
        user_id = (TextView) rootView.findViewById(R.id.user_id);
        user_pass = (TextView) rootView.findViewById(R.id.password);
        auto_login = (CheckBox) rootView.findViewById(R.id.auto_login_checkbox);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ConnectDatabaseThread().execute("/sign_in.php?" , user_id.getText().toString(), user_pass.getText().toString());
                signIn(user_id.getText().toString(), user_pass.getText().toString());
                //Intent intent = new Intent(getActivity(), MainActivity.class);
                //startActivity(intent);
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("go_signup_fragment", 1);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }

    private void loginInfoSave(){
        // 값 저장하기

    }



    private void signIn(String... param){
        class SignInThread extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "로그인 중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(!s.equals("failed")){
                    Toast.makeText(getActivity(),s + "님 환영합니다.",Toast.LENGTH_LONG).show();
                    mPref = new RbPreference(getActivity());
                    if(auto_login.isChecked()){
                        mPref.put("auto_login", true);
                    }
                    mPref.put("user_id",s);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),"입력하신 정보를 다시 확인해주세요.",Toast.LENGTH_LONG).show();

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


