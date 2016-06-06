package android.finalproject.bshop.adapter;

import android.content.Context;
import android.finalproject.bshop.BluetoothObject;
import android.finalproject.bshop.R;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eduardo Flores on 3/23/15.
 */
public class FoundBTDevicesAdapter extends ArrayAdapter<BluetoothObject>
{
    private Context context;
    private ArrayList<BluetoothObject> arrayFoundDevices;
    private LayoutInflater mLayoutInflater;

    public FoundBTDevicesAdapter(Context context, ArrayList<BluetoothObject> arrayOfAlreadyPairedDevices)
    {
        super(context, R.layout.row_bt_scan_new_devices, arrayOfAlreadyPairedDevices);

        this.context = context;
        this.arrayFoundDevices = arrayOfAlreadyPairedDevices;
        //mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //BluetoothObject bluetoothObject = arrayFoundDevices.get(position);

        // 1. Create Inflater
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = View.inflate(context,R.layout.row_bt_scan_new_devices,null);

        // 3. Get the widgets from the rowView
        TextView bt_name = (TextView) rowView.findViewById(R.id.textview_bt_scan_name);
        TextView bt_address = (TextView) rowView.findViewById(R.id.textview_bt_scan_address);
        TextView bt_bondState = (TextView) rowView.findViewById(R.id.textview_bt_scan_state);
        TextView bt_type = (TextView) rowView.findViewById(R.id.textview_bt_scan_type);
        TextView bt_uuid = (TextView) rowView.findViewById(R.id.textview_bt_scan_uuid);
        TextView bt_signal_strength = (TextView) rowView.findViewById(R.id.textview_bt_scan_signal_strength);

        BluetoothObject item = arrayFoundDevices.get(position);

        // 4. Set the text for each widget
        bt_name.setText(item.getBluetooth_name());
        bt_address.setText("address: " + item.getBluetooth_address());
        bt_bondState.setText("state: " + item.getBluetooth_state());
        bt_type.setText("type: " + item.getBluetooth_type());
        bt_signal_strength.setText("RSSI: " + item.getBluetooth_rssi() + "dbm");

        ParcelUuid uuid[] = item.getBluetooth_uuids();
        if (uuid != null)
            bt_uuid.setText("uuid" + uuid[0]);


        // 5. return rowView
        return rowView;

    }//end getView()


}//end class AlreadyPairedAdapter































