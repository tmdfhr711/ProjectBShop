package android.finalproject.bshop;

import android.finalproject.bshop.adapter.ShopInfoListAdapter;
import android.finalproject.bshop.model.ShopInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {

    private ListView mListView = null;
    private ShopInfoListAdapter mAdapter = null;
    private static List<ShopInfo> mShopInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        mListView = (ListView) findViewById(R.id.lv_shoplist);
        mShopInfoList = new ArrayList<>();

    }
}
