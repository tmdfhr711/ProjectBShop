package android.finalproject.bshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;
import java.net.URLEncoder;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final String SERVER_ADDR = "http://210.117.181.66:8080/BShop";


    private Button sign_in_btn;
    private Button sugn_up_btn;

    private EditText user_id_edt;
    private EditText password_edt;
    private EditText user_nick;


    public void init() {
        user_id_edt = (EditText) findViewById(R.id.user_id);
        password_edt = (EditText) findViewById(R.id.password);
        user_nick = (EditText) findViewById(R.id.user_nick);
    }

    private void signInFunc(){


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void onButtonClicked(View v){
        Button btn = (Button) v;
        if (btn.getId() == R.id.sign_in_button) {
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(btn.getId() == R.id.sign_up_button) {
            String mUser_id = null,mPassword = null,mUser_nick = null;
            try {
                mUser_id = URLEncoder.encode(user_id_edt.getText().toString(), "UTF-8");
                mPassword = URLEncoder.encode(password_edt.getText().toString(), "UTF-8");
                mUser_nick = URLEncoder.encode(user_nick.getText().toString(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Sign_up_Thread().execute(mUser_id, mPassword, mUser_nick);
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    class Sign_up_Thread extends AsyncTask<String, Integer, Long> {
        String insertresult;

        @Override
        protected Long doInBackground(String... params) {
            // TODO Auto-generated method stub

            String mUser_id = null;
            String mPassword = null;
            String mUser_nick = null;
            //String get_address = null;
            try {

                //mUser_id = URLEncoder.encode(user_id_edt.getText().toString(), "UTF-8");
                //mPassword =
                //mUser_nick =

                mUser_id = params[0];
                mPassword = params[1];
                mUser_nick = params[2];


            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            try {
                Log.i("URL", SERVER_ADDR + "/sign_up.php?" +
                        "user_id=" + mUser_id +
                        "&password=" + mPassword +
                        "&user_nick=" + mUser_nick);
                URL url = new URL(SERVER_ADDR + "/sign_up.php?" +
                        "user_id=" + mUser_id +
                        "&password=" + mPassword +
                        "&user_nick=" + mUser_nick);

                url.openStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

            /*
            try {
                URL url = new URL(SERVER_ADDR);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");

                String mUser_id = "seung";
                String mPassword = "123";
                String mUser_nick = "OH";

                String param = "user_id=" + mUser_id + "&password=" + mPassword + "&user_nick=" + mUser_nick;

                OutputStream os = con.getOutputStream();
                os.write(param.getBytes());
                os.flush();
                os.close();

                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line= null;
                while ((line = rd.readLine()) != null) {
                    Log.i("BufferedReader", line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
            */
        }

        @Override
        public void onPostExecute(Long result) {
            super.onPostExecute(result);
            Log.i("sign_up_result", insertresult);

        }
    }
}

