package android.finalproject.bshop.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.finalproject.bshop.MainActivity;
import android.finalproject.bshop.RequestHandler;
import android.finalproject.bshop.model.RbPreference;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.finalproject.bshop.R;
import android.widget.Toast;

import java.util.HashMap;

public class SignUpFragment extends Fragment {

    private Button complete_sign_up_button;
    private TextView user_id;
    private TextView user_pass;
    private TextView user_nick;

    public static final String UPLOAD_USER_ID = "user_id";
    public static final String UPLOAD_USER_PASS = "password";
    public static final String UPLOAD_USER_NICK = "user_nick";


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);
        complete_sign_up_button = (Button) rootView.findViewById(R.id.complete_signup_button);
        user_id = (TextView) rootView.findViewById(R.id.user_id);
        user_pass = (TextView) rootView.findViewById(R.id.password);
        user_nick = (TextView) rootView.findViewById(R.id.user_nick);

        complete_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(user_id.getText().toString(), user_pass.getText().toString(),user_nick.getText().toString());
            }
        });
        return rootView;
    }

    private void signup(String... param){
        class SignUpThread extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "회원가입 중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(!s.equals("failed")){
                    Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(),"입력하신 정보를 다시 확인해주세요.",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {
                String mUser_id = null;
                String mPassword = null;
                String mUser_nick = null;
                String UPLOAD_URL = "http://210.117.181.66:8080/BShop/sign_up.php";

                mUser_id = params[0];
                mPassword = params[1];
                mUser_nick = params[2];
                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USER_ID, mUser_id);
                data.put(UPLOAD_USER_PASS, mPassword);
                data.put(UPLOAD_USER_NICK, mUser_nick);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        SignUpThread ui = new SignUpThread();
        ui.execute(param[0],param[1],param[2]);

    }

}
