package android.finalproject.bshop;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;
    public static final boolean DISCONTINUOUS_SCAN = true;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_LOCATION = 10;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    private View mLayout;

    private TextView tv = null;
    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUserBluetooth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkUserBluetooth(){
        //If a user device turns off bluetooth, request to turn it on.
        //사용자가 블루투스를 켜도록 요청합니다.
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }
    }

    public void onButtonClicked(View v){
        Button btn = (Button) v;
        if (btn.getId() == R.id.start_ranging) {
            final Intent intent = new Intent(this, RecoRangingActivity.class);
            startActivity(intent);
            finish();
        } else if(btn.getId() == R.id.sign_button){
            final Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (btn.getId() == R.id.register_beacon_button) {
            final Intent intent = new Intent(this, FoundBTDevices.class);
            startActivity(intent);
            //finish();
        } else if (btn.getId() == R.id.image_upload_button) {
            final Intent intent = new Intent(this, RegistShopActivity.class);
            startActivity(intent);
            //finish();
        }
    }
}
