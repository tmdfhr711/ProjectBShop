package android.finalproject.bshop.model;

/**
 * Created by OHRok on 2016-06-29.
 */
public class BleInfo {
    private String mUUID;
    private String mMAC;
    private String mMajor;
    private String mMinor;
    private String mRssi;

    public BleInfo(String UUID, String MAC, String major, String minor, String rssi) {
        mUUID = UUID;
        mMAC = MAC;
        mMajor = major;
        mMinor = minor;
        mRssi = rssi;
    }

    public String getUUID() {
        return mUUID;
    }

    public void setUUID(String UUID) {
        mUUID = UUID;
    }

    public String getMAC() {
        return mMAC;
    }

    public void setMAC(String MAC) {
        mMAC = MAC;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public String getMinor() {
        return mMinor;
    }

    public void setMinor(String minor) {
        mMinor = minor;
    }

    public String getRssi() {
        return mRssi;
    }

    public void setRssi(String rssi) {
        mRssi = rssi;
    }
}
