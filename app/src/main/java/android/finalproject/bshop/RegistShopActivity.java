package android.finalproject.bshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class RegistShopActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView upload_image;
    private Button register_shop_button;
    private EditText shop_name_edt;
    private EditText shop_ceo_edt;
    private EditText shop_phone_edt;
    private EditText shop_address_edt;

    private String get_shop_name;
    private String get_shop_ceo;
    private String get_shop_phone;
    private String get_shop_address;


    public static final String UPLOAD_URL = "http://210.117.181.66:8080/BShop/image_upload.php";
    public static final String UPLOAD_KEY = "image";
    public static final String UPLOAD_SHOP_NAME = "shop_name";
    public static final String UPLOAD_SHOP_CEO = "shop_ceo";
    public static final String UPLOAD_SHOP_PHONE = "shop_phone";
    public static final String UPLOAD_SHOP_ADDRESS = "shop_address";



    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap = null;

    private Uri filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_regist_shop);

        init();

        upload_image.setOnClickListener(this);
        register_shop_button.setOnClickListener(this);

    }
    private void init(){
        upload_image = (ImageView) findViewById(R.id.upload_imege);
        register_shop_button = (Button) findViewById(R.id.register_shop_button);
        shop_address_edt = (EditText) findViewById(R.id.edt_shop_address);
        shop_ceo_edt = (EditText) findViewById(R.id.edt_shop_ceo);
        shop_name_edt = (EditText) findViewById(R.id.edt_shop_name);
        shop_phone_edt = (EditText) findViewById(R.id.edt_shop_phone);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                upload_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegistShopActivity.this, "정보 저장중...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"가게 등록 완료",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistShopActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_SHOP_NAME, get_shop_name);
                data.put(UPLOAD_SHOP_CEO, get_shop_ceo);
                data.put(UPLOAD_SHOP_PHONE, get_shop_phone);
                data.put(UPLOAD_SHOP_ADDRESS, get_shop_address);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        if(bitmap != null){
            ui.execute(bitmap);
        }else{
            bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_insert_photo_grey600_36dp);
            ui.execute(bitmap);
        }

    }


    @Override
    public void onClick(View v) {

        if (v == upload_image) {
            showFileChooser();
        } else if (v == register_shop_button) {
            get_shop_name = shop_name_edt.getText().toString();
            get_shop_ceo = shop_ceo_edt.getText().toString();
            get_shop_phone = shop_phone_edt.getText().toString();
            get_shop_address = shop_address_edt.getText().toString();
            uploadImage();
        }


    }


}
