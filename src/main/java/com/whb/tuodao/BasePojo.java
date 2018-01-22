package com.whb.tuodao;

/**
 * @author whb
 * @date 2018年1月22日 下午3:41:40 
 * @Description: 公共请求信息
 */
public class BasePojo {

    /**
     * 权限ID
     */
    private String accessId;
    /**
     * 请求类型 (1:WEB,2:FINACE_API)
     */
    private String requestType;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户ip编号
     */
    private String ip;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
