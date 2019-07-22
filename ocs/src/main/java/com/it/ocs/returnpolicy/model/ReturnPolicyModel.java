package com.it.ocs.returnpolicy.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class ReturnPolicyModel extends BaseModel {
    private Long returnPolicyId;

    private String returnPolicyName;

    private Long siteId;

    private String policyType;

    private Long daysAllowed;

    private String allowExtension;

    private String refundMethod;

    private String freightCarrier;

    private String depreciationCost;

    private String policyDetail;

    private String enabledFlag;

    private Date creationDate;

    private Long createBy;

    private Date lastUpdateDate;

    private Long lastUpdateBy;
    
    private String ico;

    public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public Long getReturnPolicyId() {
        return returnPolicyId;
    }

    public void setReturnPolicyId(Long returnPolicyId) {
        this.returnPolicyId = returnPolicyId;
    }

    public String getReturnPolicyName() {
        return returnPolicyName;
    }

    public void setReturnPolicyName(String returnPolicyName) {
        this.returnPolicyName = returnPolicyName == null ? null : returnPolicyName.trim();
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType == null ? null : policyType.trim();
    }

    public Long getDaysAllowed() {
        return daysAllowed;
    }

    public void setDaysAllowed(Long daysAllowed) {
        this.daysAllowed = daysAllowed;
    }

    public String getAllowExtension() {
        return allowExtension;
    }

    public void setAllowExtension(String allowExtension) {
        this.allowExtension = allowExtension == null ? null : allowExtension.trim();
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod == null ? null : refundMethod.trim();
    }

    public String getFreightCarrier() {
        return freightCarrier;
    }

    public void setFreightCarrier(String freightCarrier) {
        this.freightCarrier = freightCarrier == null ? null : freightCarrier.trim();
    }

    public String getDepreciationCost() {
        return depreciationCost;
    }

    public void setDepreciationCost(String depreciationCost) {
        this.depreciationCost = depreciationCost == null ? null : depreciationCost.trim();
    }

    public String getPolicyDetail() {
        return policyDetail;
    }

    public void setPolicyDetail(String policyDetail) {
        this.policyDetail = policyDetail == null ? null : policyDetail.trim();
    }
    @Override
    public String getEnabledFlag() {
        return enabledFlag;
    }
    @Override
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }
    @Override
    public Date getCreationDate() {
        return creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    @Override
    public Long getCreateBy() {
        return createBy;
    }
    @Override
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    @Override
    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }
    @Override
    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}