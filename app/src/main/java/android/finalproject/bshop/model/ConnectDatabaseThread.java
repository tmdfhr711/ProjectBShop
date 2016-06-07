package android.finalproject.bshop.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.finalproject.bshop.MainActivity;
import android.finalproject.bshop.R;
import android.finalproject.bshop.RegistShopActivity;
import android.finalproject.bshop.RequestHandler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by OHRok on 2016-06-07.
 */
public class ConnectDatabaseThread extends AsyncTask<String, Integer, Long> {
    String insertresult;


    @Override
    protected Long doInBackground(String... params) {
        // TODO Auto-generated method stub

        String mUser_id = null;
        String mPassword = null;
        //String mUser_nick = null;
        String SERVER_ADDR = "http://210.117.181.66:8080/BShop";
        String phpFileName = null;
        //String get_address = null;
        try {

            //mUser_id = URLEncoder.encode(user_id_edt.getText().toString(), "UTF-8");
            //mPassword =
            //mUser_nick =
            phpFileName = params[0];
            mUser_id = params[1];
            mPassword = params[2];
            //mUser_nick = params[3];


        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        try {
            Log.i("URL", SERVER_ADDR + "/sign_up.php?" +
                    "user_id=" + mUser_id +
                    "&password=" + mPassword);
            URL url = new URL(SERVER_ADDR + "/sign_up.php?" +
                    "user_id=" + mUser_id +
                    "&password=" + mPassword);

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

