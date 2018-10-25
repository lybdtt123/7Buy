package com.haoniu.zzx.uidemo.model;

/**
 * Created by zzx on 2017/12/26 0026.
 */

public class VoteModel {

    /**
     * Success : true
     * Msg : 投票成功！
     * Vote : 1568487
     */

    private boolean Success;
    private String Msg;
    private int Vote;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public int getVote() {
        return Vote;
    }

    public void setVote(int Vote) {
        this.Vote = Vote;
    }
}
