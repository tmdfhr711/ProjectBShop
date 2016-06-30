package android.finalproject.bshop.adapter;

import android.content.Context;
import android.finalproject.bshop.R;
import android.finalproject.bshop.model.BleInfo;
import android.finalproject.bshop.model.ShopInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.perples.recosdk.RECOBeacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by OHRok on 2016-06-29.
 */
public class BleRangingListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Beacon> mBleList;

    public BleRangingListAdapter(Context context, ArrayList<Beacon> bleList) {
        mContext = context;
        mBleList = bleList;
    }

    @Override
    public int getCount() {
        return mBleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateAllBeacons(Collection<Beacon> beacons) {
        synchronized (beacons) {
            mBleList = new ArrayList<Beacon>(beacons);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_ble_ranging_custom_view, null);
        TextView beacon_uuid = (TextView) v.findViewById(R.id.ranging_uuid);
        TextView beacon_mac = (TextView) v.findViewById(R.id.ranging_mac);
        TextView beacon_major = (TextView) v.findViewById(R.id.ranging_mac);
        TextView beacon_minor = (TextView) v.findViewById(R.id.ranging_mac);
        TextView beacon_rssi = (TextView) v.findViewById(R.id.ranging_mac);

        //ImageButton ib_shop_coment_num = (ImageButton) v.findViewById(R.id.shop_coment_num);

        Beacon item = mBleList.get(position);
        //img_shop_picture;
        beacon_uuid.setText(item.getProximityUUID().toString());
        beacon_mac.setText(item.getMacAddress().toString());
        beacon_major.setText(String.valueOf(item.getMajor()));
        beacon_minor.setText(String.valueOf(item.getMinor()));
        beacon_rssi.setText(String.valueOf(item.getRssi()));

        return v;
    }
}
