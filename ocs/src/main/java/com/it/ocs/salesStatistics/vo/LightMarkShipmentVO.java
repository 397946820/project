package com.it.ocs.salesStatistics.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;

public class LightMarkShipmentVO extends LightMarkShipmentModel implements Serializable {
	
	private static final long serialVersionUID = 4289695827152840896L;
	
	private String markerText;
	
	public LightMarkShipmentVO() {}
	
	public LightMarkShipmentVO(LightMarkShipmentModel model) throws Exception {
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			boolean fieldAccessible = field.isAccessible();
			field.setAccessible(true);
			Field curr = this.getClass().getSuperclass().getDeclaredField(field.getName());
			boolean currAccessible = curr.isAccessible();
			curr.setAccessible(true);
			curr.set(this, field.get(model));
			curr.setAccessible(currAccessible);
			field.setAccessible(fieldAccessible);
		}
	}

	public String getMarkerText() {
		return markerText;
	}

	public void setMarkerText(String markerText) {
		this.markerText = markerText;
	}
}
