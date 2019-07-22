package com.it.ocs.synchronou.mapping;

import java.util.HashMap;
import java.util.TimeZone;

public class ZoneMapping {

	public static String searchTokenBySiteId(Long siteId){
		HashMap<Long, String> map = new HashMap<>();
		map.put(0l, "America/Los_Angeles");
		map.put(2l, "America/Toronto");
		map.put(3l, "Europe/London");
		map.put(77l, "Europe/Berlin");
		map.put(15l, "");
		map.put(100l, "");
		map.put(101l, "Europe/Rome");
		map.put(146l, "");
		map.put(186l, "");
		map.put(203l, "");
		map.put(201l, "");
		map.put(216l, "");
		map.put(207l, "");
		map.put(211l, "");
		map.put(210l, "");
		map.put(212l, "");
		map.put(123l, "");
		map.put(23l, "");
		map.put(16l, "");
		map.put(193l, "");
		map.put(205l, "");
		
		
		if(map.get(siteId)!=null){
			return map.get(siteId);
		}else{
			return null;
		}
	}
}
