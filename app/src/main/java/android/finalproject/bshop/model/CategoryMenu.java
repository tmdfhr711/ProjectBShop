package android.finalproject.bshop.model;

/**
 * Created by OHRok on 2016-06-07.
 */
public class CategoryMenu {
    private int cate_index;
    private String cate_name;

    public CategoryMenu(int cate_index, String cate_name) {
        this.cate_index = cate_index;
        this.cate_name = cate_name;
    }

    public int getCate_index() {
        return cate_index;
    }

    public void setCate_index(int cate_index) {
        this.cate_index = cate_index;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
}
