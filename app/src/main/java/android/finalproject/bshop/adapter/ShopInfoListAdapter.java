package android.finalproject.bshop.adapter;

import android.content.Context;
import android.finalproject.bshop.R;
import android.finalproject.bshop.model.ShopInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.List;

/**
 * Created by OHRok on 2016-06-02.
 */
public class ShopInfoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopInfo> mShopListData;

    public ShopInfoListAdapter(Context context, List<ShopInfo> shopListData) {
        mContext = context;
        mShopListData = shopListData;
    }

    @Override
    public int getCount() {
        return mShopListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mShopListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        View v = View.inflate(mContext, R.layout.item_shoplist_view, null);
        ImageView img_shop_picture = (ImageView) v.findViewById(R.id.shoplist_shop_name);
        TextView tv_shop_name = (TextView) v.findViewById(R.id.shoplist_shopimage);
        //ImageButton ib_shop_coment_num = (ImageButton) v.findViewById(R.id.shop_coment_num);

        ShopInfo item = mShopListData.get(position);
        //img_shop_picture;
        tv_shop_name.setText(item.getShopName());
        */
        return null;
    }
}
