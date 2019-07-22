package com.it.ocs.payment.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EbayPaymentModel extends BaseModel {
    private Long payid;

    private String payName;

    private Long siteId;

    private String payAccount;

    private String supportPayment;

    private String description;

    private String enabledFlag;

    private Date creationDate;

    private Long createBy;

    private Date lastUpdateDate;

    private Long lastUpdateBy;

    public Long getPayid() {
        return payid;
    }

    public void setPayid(Long payid) {
        this.payid = payid;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getSupportPayment() {
        return supportPayment;
    }

    public void setSupportPayment(String supportPayment) {
        this.supportPayment = supportPayment == null ? null : supportPayment.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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