package com.example.hemavathi.mypoolinapp;

import java.io.Serializable;

/**
 * Created by hemavathi on 22/01/18.
 */

public class AccessStringInfoData implements Serializable {
    static final long serialVersionUID = 1L;
    private String P2P_ID;
    private String P2P_KEY;
    private String P2M_ID;
    private String P2M_KEY;
    private String GROUP_ID;
    private String GROUP_KEY;
    private String type;
    private String selectAll;

    public String getP2P_ID() {
        return P2P_ID;
    }

    public void setP2P_ID(String p2P_ID) {
        P2P_ID = p2P_ID;
    }

    public String getP2P_KEY() {
        return P2P_KEY;
    }

    public void setP2P_KEY(String p2P_KEY) {
        P2P_KEY = p2P_KEY;
    }

    public String getP2M_ID() {
        return P2M_ID;
    }

    public void setP2M_ID(String p2M_ID) {
        P2M_ID = p2M_ID;
    }

    public String getP2M_KEY() {
        return P2M_KEY;
    }

    public void setP2M_KEY(String p2M_KEY) {
        P2M_KEY = p2M_KEY;
    }

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public String getGROUP_KEY() {
        return GROUP_KEY;
    }

    public void setGROUP_KEY(String GROUP_KEY) {
        this.GROUP_KEY = GROUP_KEY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectAll() {
        return selectAll;
    }

    public void setSelectAll(String selectAll) {
        this.selectAll = selectAll;
    }
}
