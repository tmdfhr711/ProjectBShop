package android.finalproject.bshop.adapter;

import android.content.Context;
import android.finalproject.bshop.R;
import android.finalproject.bshop.model.CategoryMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OHRok on 2016-06-07.
 */
public class NavigationCateAdapter extends BaseAdapter{

    private Context context;
    private List<CategoryMenu> item;

    public NavigationCateAdapter(Context context, List<CategoryMenu> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.category_custom_view, null);
        ImageView img = (ImageView) v.findViewById(R.id.category_image);
        TextView tv = (TextView) v.findViewById(R.id.category_name);

        CategoryMenu cate_menu = item.get(position);
        img.setImageResource(cate_menu.getCate_index());
        tv.setText(cate_menu.getCate_name());

        return v;
    }
}
