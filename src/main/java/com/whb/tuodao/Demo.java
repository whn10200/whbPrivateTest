package com.whb.tuodao;

import java.io.Serializable;

/**
 * @author whb
 * @date 2017年12月29日 上午9:40:31 
 * @Description: 添加借款用户对象
 */
public class Demo extends BasePojo implements Serializable {

    /**@Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -3587830444519460782L;

	/**
     * 用户姓名
     */
    private String userName;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 卡号
     */
    private String bankCardId;

    /**
     * 开户银行
     */
    private String bankAccountId;
    
    /**
     * 个人手机号码
     */
    private String phone;

    /**
     * 银行预留手机号码
     */
    private String bankPhone;
    
    /**
     * 操作类型1：新增，2修改
     */
    private Integer operationType;
    
    private Integer repayStatus;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(Integer repayStatus) {
		this.repayStatus = repayStatus;
	}

}
