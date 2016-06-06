package android.finalproject.bshop;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.finalproject.bshop.adapter.FoundBTDevicesAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FoundBTDevices extends AppCompatActivity
{
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothObject> arrayOfFoundBTDevices;
    private FoundBTDevicesAdapter mAdapter = null;
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_beacon);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mListView = (ListView) findViewById(R.id.lv_beaconlist);
        //arrayOfFoundBTDevices =
        displayListOfFoundDevices();
    }

    private void displayListOfFoundDevices()
    {
        arrayOfFoundBTDevices = new ArrayList<BluetoothObject>();

        // start looking for bluetooth devices
        mBluetoothAdapter.startDiscovery();

        // Discover new devices
        // Create a BroadcastReceiver for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    // Get the bluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    // Get the "RSSI" to get the signal strength as integer,
                    // but should be displayed in "dBm" units
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

                    // Create the device object and add it to the arrayList of devices
                    BluetoothObject bluetoothObject = new BluetoothObject();
                    bluetoothObject.setBluetooth_name(device.getName());
                    bluetoothObject.setBluetooth_address(device.getAddress());
                    bluetoothObject.setBluetooth_state(device.getBondState());
                    bluetoothObject.setBluetooth_type(device.getType());    // requires API 18 or higher
                    bluetoothObject.setBluetooth_uuids(device.getUuids());
                    bluetoothObject.setBluetooth_rssi(rssi);

                    arrayOfFoundBTDevices.add(bluetoothObject);

                    // 1. Pass context and data to the custom adapter
                    FoundBTDevicesAdapter adapter = new FoundBTDevicesAdapter(getApplicationContext(), arrayOfFoundBTDevices);

                    // 2. setListAdapter
                    //setListAdapter(adapter);

                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        mBluetoothAdapter.cancelDiscovery();
    }
}