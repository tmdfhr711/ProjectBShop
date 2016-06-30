package android.finalproject.bshop;

import android.content.Intent;
import android.finalproject.bshop.adapter.BleRangingListAdapter;
import android.finalproject.bshop.adapter.RecoRangingListAdapter;
import android.finalproject.bshop.model.BleInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BleRangingActivity extends AppCompatActivity {

    private static final String TAG = "BleRangingActivity";
    private BeaconManager mBeaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("rid", null, null, null);


    private ListView mListView;
    private BleRangingListAdapter mBleRangingListAdapter;
    private ArrayList<Beacon> mBleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_ranging);

    }

    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected  void onResume(){
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(BleRangingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
