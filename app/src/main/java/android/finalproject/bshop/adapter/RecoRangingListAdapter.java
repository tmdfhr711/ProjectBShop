/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package android.finalproject.bshop.adapter;

import android.content.Context;
import android.finalproject.bshop.R;
import android.finalproject.bshop.model.CategoryMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perples.recosdk.RECOBeacon;

import java.util.ArrayList;
import java.util.Collection;

public class RecoRangingListAdapter extends BaseAdapter {
    private ArrayList<RECOBeacon> mRangedBeacons;
    private Context context;

    public RecoRangingListAdapter(Context context, ArrayList<RECOBeacon> mRangedBeacons) {
        super();
        this.context = context;
        this.mRangedBeacons = mRangedBeacons;
    }

    public void updateBeacon(RECOBeacon beacon) {
        synchronized (mRangedBeacons) {
            if(mRangedBeacons.contains(beacon)) {
                mRangedBeacons.remove(beacon);
            }
            mRangedBeacons.add(beacon);
        }
    }

    public void updateAllBeacons(Collection<RECOBeacon> beacons) {
        synchronized (beacons) {
            mRangedBeacons = new ArrayList<RECOBeacon>(beacons);
        }
    }

    public void clear() {
        mRangedBeacons.clear();
    }

    @Override
    public int getCount() {
        return mRangedBeacons.size();
    }

    @Override
    public Object getItem(int position) {
        return mRangedBeacons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.item_ranging_beacon, null);
        TextView recoProximityUuid = (TextView)v.findViewById(R.id.recoProximityUuid);
        TextView recoMajor = (TextView)v.findViewById(R.id.recoMajor);
        TextView recoMinor = (TextView)v.findViewById(R.id.recoMinor);
        TextView recoTxPower = (TextView)v.findViewById(R.id.recoTxPower);
        TextView recoRssi = (TextView)v.findViewById(R.id.recoRssi);
        TextView recoProximity = (TextView)v.findViewById(R.id.recoProximity);
        TextView recoAccuracy = (TextView)v.findViewById(R.id.recoAccuracy);

        RECOBeacon recoBeacon = mRangedBeacons.get(position);

        String proximityUuid = recoBeacon.getProximityUuid();

        recoProximityUuid.setText(String.format("%s-%s-%s-%s-%s", proximityUuid.substring(0, 8), proximityUuid.substring(8, 12), proximityUuid.substring(12, 16), proximityUuid.substring(16, 20), proximityUuid.substring(20) ));
        recoMajor.setText(recoBeacon.getMajor() + "");
        recoMinor.setText(recoBeacon.getMinor() + "");
        recoTxPower.setText(recoBeacon.getTxPower() + "");
        recoRssi.setText(recoBeacon.getRssi() + "");
        recoProximity.setText(recoBeacon.getProximity() + "");
        recoAccuracy.setText(String.format("%.2f", recoBeacon.getAccuracy()));

        return v;
    }

}
