package android.finalproject.bshop;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.finalproject.bshop.adapter.NavigationCateAdapter;
import android.finalproject.bshop.fragment.LoginFragment;
import android.finalproject.bshop.model.CategoryMenu;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


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

    Toolbar toolbar;
    /*
     * Toolbar는 5.0Ver 부터 Actionbar를 대체 할 수 있는 bar
     * Actionbar와 동일한 역할을 하지만 View 형태로 구성되어있어 자유롭게 배치가능
     * Actionbar를 Toolbar로 대체 하였기 때문에 중복방지를 위해 Theme를 NoActionBar로 바꿔줘야함.
     */
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    /*
     * 네비게이션 드로어 상태를 알려주는 아이콘을 표시하기 위해 ActionBarDrawerToggle을 생성.
     * ActionBarDrawerToggle 동작을 위한 나머지 코드는 Override 된 함수에서 구현.
     */

    private Button navigation_register_shop_button;
    private Button navigation_register_beacon_button;
    private Button navigation_user_mypage_button;
    private Button navigation_login_button;
    private ImageView navigation_user_profile_picture;
    private TextView navigation_user_nick;

    private ListView navigation_category_listview;
    private List<CategoryMenu> listCategory;
    private NavigationCateAdapter navi_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSupportActionBar(toolbar);
        //ActionBar를 Toolbar로 대체함.
        //기존의 Actionbar 외에 별도로 Toolbar를 사용하고 싶다면 이 메소드를 호출하지 않고
        //Toolbar만 단독으로 사용가능.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };
        dlDrawer.setDrawerListener(dtToggle);

        navigation_category_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigation_category_listview.setItemChecked(position,true);
                dlDrawer.closeDrawers();
            }
        });
        checkUserBluetooth();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_register_shop_button = (Button) findViewById(R.id.navigation_register_shop_button);
        navigation_register_beacon_button = (Button) findViewById(R.id.navigation_register_beacon_button);
        navigation_user_mypage_button = (Button) findViewById(R.id.navigation_mypage_button);
        navigation_login_button = (Button) findViewById(R.id.navigation_login_button);
        navigation_user_profile_picture = (ImageView) findViewById(R.id.navigation_user_profile_picture);
        navigation_user_nick = (TextView) findViewById(R.id.navigation_user_nick);
        navigation_category_listview = (ListView) findViewById(R.id.navigation_category_listview);

        listCategory = new ArrayList<>();

        listCategory.add(new CategoryMenu(R.drawable.ic_store_mall_directory_grey600_18dp, "카페"));
        listCategory.add(new CategoryMenu(R.drawable.ic_store_mall_directory_grey600_18dp, "고기"));
        listCategory.add(new CategoryMenu(R.drawable.ic_store_mall_directory_grey600_18dp, "옷가게"));
        listCategory.add(new CategoryMenu(R.drawable.ic_store_mall_directory_grey600_18dp, "술집"));

        navi_adapter = new NavigationCateAdapter(this, listCategory);
        navigation_category_listview.setAdapter(navi_adapter);

        navigation_register_shop_button.setOnClickListener(this);
        navigation_register_beacon_button.setOnClickListener(this);
        navigation_user_mypage_button.setOnClickListener(this);
        navigation_user_profile_picture.setOnClickListener(this);
        navigation_login_button.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //Sync the toggle state after onRestoreInstanceState has occurred
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public void onClick(View v) {
        if (v == navigation_register_shop_button) {
            Intent intent = new Intent(MainActivity.this, RegistShopActivity.class);
            startActivity(intent);
        } else if (v == navigation_register_beacon_button) {
            Intent intent = new Intent(MainActivity.this, RegistShopActivity.class);
            startActivity(intent);
        } else if (v == navigation_user_mypage_button) {
            Intent intent = new Intent(MainActivity.this, RegistShopActivity.class);
            startActivity(intent);
        } else if (v == navigation_user_profile_picture) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v == navigation_login_button) {
            replaceFragment();
            dlDrawer.closeDrawers();
        }
    }

    private void replaceFragment(){
        Fragment fragment = null;

        fragment = new LoginFragment();

        if (null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}
