package com.it.ocs.fourPX.vo;

import java.util.List;

import com.it.ocs.fourPX.model.SellerInventoryLogModel;
import com.it.ocs.fourPX.model.Unqualified;

public class SellerInventoryLogVO extends SellerInventoryLogModel implements java.io.Serializable {

	private static final long serialVersionUID = 9123545760995728476L;
	
	private List<Unqualified> unqualifieds;
	
	public SellerInventoryLogVO() {
		
	}
	
	public SellerInventoryLogVO(SellerInventoryLogModel model) {
		super(model);
	}

	public List<Unqualified> getUnqualifieds() {
		return unqualifieds;
	}

	public void setUnqualifieds(List<Unqualified> unqualifieds) {
		this.unqualifieds = unqualifieds;
	}
}
