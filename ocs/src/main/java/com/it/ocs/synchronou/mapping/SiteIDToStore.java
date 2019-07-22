package com.it.ocs.synchronou.mapping;

import java.util.HashMap;

public class SiteIDToStore {

    public static String  searchStoreUrlBySiteIDAndUser(Long siteID,String userID){
		
		HashMap<Object, String> map = new HashMap<>();
		map.put(3L, "http://stores.ebay.com/Neon-Mart-Lighting?_trksid=p2047675.l2563");
		map.put(0L, "http://stores.ebay.co.uk/LE-United-Kindom?_trksid=p2047675.l2563  ");
		map.put(77L+"le.deutschland", "http://stores.ebay.fr/le-deutschland?_trksid=p2047675.l2563");
		map.put(77L+"nm.deutschland", "http://stores.ebay.com/nm-deutschland?_trksid=p2047675.l2563");
		if(siteID.equals(77L)){
			if (map.get(siteID+userID)!=null) {
				
				return map.get(siteID+userID);
			}else{
				return null;
			}
		}else{
			if(map.get(siteID)!=null){
				return map.get(siteID);
			}else{
				return null;
			}
		}
		
	}

}
