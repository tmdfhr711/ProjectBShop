package android.finalproject.bshop;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class RegisterBeaconActivity extends AppCompatActivity {

    private TextView tv_bluetooth_name;
    private TextView tv_bluetooth_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beacon);


        String getUuid = getIntent().getStringExtra("uuid");
        tv_bluetooth_name = (TextView) findViewById(R.id.bluetooth_name);
        tv_bluetooth_address = (TextView) findViewById(R.id.bluetooth_mac_address);
        tv_bluetooth_name.setText(getLocalBluetoothName());
        tv_bluetooth_address.setText(getBluetoothMacAddress());

    }

    public static String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // if device does not support Bluetooth
        if(mBluetoothAdapter==null){
            Log.d("RegisterBeaconActivity", "device does not support bluetooth");
            return null;
        }

        return mBluetoothAdapter.getName();
    }

    /**
     * get bluetooth adapter MAC address
     * @return MAC address String
     */
    public static String getBluetoothMacAddress() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // if device does not support Bluetooth
        if(mBluetoothAdapter==null){
            Log.d("RegisterBeaconActivity","device does not support bluetooth");
            return null;
        }

        return mBluetoothAdapter.getAddress();
    }
}
