package android.finalproject.bshop.model;

/**
 * Created by OHRok on 2016-06-02.
 */
public class ShopInfo {
    private String mBeaconUuid;
    private String mShopName;
    private String mPicture;
    private String mCoupon;
    private String mComent;
    private int mComentNum;



    public ShopInfo(String beaconUuid, String shopName, String picture, String coupon, String coment, int comentNum) {

        mBeaconUuid = beaconUuid;
        mShopName = shopName;
        mPicture = picture;
        mCoupon = coupon;
        mComent = coment;
        mComentNum = comentNum;
    }

    public ShopInfo(String beaconUuid, String shopName, String picture, String coupon, String coment) {
        mBeaconUuid = beaconUuid;
        mShopName = shopName;
        mPicture = picture;
        mCoupon = coupon;
        mComent = coment;
    }

    public String getBeaconUuid() {
        return mBeaconUuid;
    }

    public void setBeaconUuid(String beaconUuid) {
        mBeaconUuid = beaconUuid;
    }

    public String getShopName() {
        return mShopName;
    }

    public void setShopName(String shopName) {
        mShopName = shopName;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getCoupon() {
        return mCoupon;
    }

    public void setCoupon(String coupon) {
        mCoupon = coupon;
    }

    public String getComent() {
        return mComent;
    }

    public void setComent(String coment) {
        mComent = coment;
    }
    public int getComentNum() {
        return mComentNum;
    }

    public void setComentNum(int comentNum) {
        mComentNum = comentNum;
    }
}
