package com.haoniu.zzx.app_ts.wxapi;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/4.
 */

public class WXPay {

    /**
     * appid : wx3bca68596fbc3de5
     * noncestr : zzo0hYavThFHdmvY
     * package : Sign=WXPay
     * partnerid : 1463327502
     * prepayid : wx20170506141705f7643a46ae0552791859
     * sign : F9FEEEF0352E5E0912673940A47FD206
     * timestamp : 1494051061
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
