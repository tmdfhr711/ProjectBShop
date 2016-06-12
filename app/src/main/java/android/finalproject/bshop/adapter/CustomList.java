package android.finalproject.bshop.adapter;

import android.app.Activity;
import android.finalproject.bshop.R;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by OHRok on 2016-06-12.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] urls;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] urls, Bitmap[] bitmaps) {
        super(context, R.layout.item_shoplist_view, urls);
        this.context = context;
        this.urls= urls;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_shoplist_view, null, true);
        TextView shop_name = (TextView) listViewItem.findViewById(R.id.shoplist_shop_name);
        ImageView shop_image = (ImageView) listViewItem.findViewById(R.id.shoplist_shopimage);

        shop_name.setText(urls[position]);
        //shop_image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],100,50,false));
        shop_image.setImageBitmap(bitmaps[position]);

        return  listViewItem;
    }
}