package com.it.ocs.cal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SKUModel implements Serializable{
	private Double length;
	private Double width;
	private Double height;
	private Double gw;
	private int qty = 1;

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getGw() {
		return gw;
	}

	public void setGw(Double gw) {
		this.gw = gw;
	}

	/**
	 * 获取所有约数
	 * @return
	 */
	public List<Integer> findDivisor(){
		List<Integer> cd = new ArrayList<>();
		
		for(int i = 1;i <= this.qty;i++) {
			if(this.qty % i == 0) cd.add(i);
		}
		return cd;
	}
	
	/**
	 * 包装箱组合规则的
	 * 求出所有满足条件的组合
	 * @return
	 */
	public List<PackageModel> findAllConditions(){
		List<Integer> d = this.findDivisor();
		//排列组合，取有效数据
		List<PackageModel>  r = new ArrayList<>();
		for(int i = 0;i < d.size();i++){
			for(int j = 0;j < d.size();j++){
				for(int k = 0;k < d.size();k++){
					PackageModel pm = new PackageModel();
					pm.setQty(this.getQty());
					pm.setLqty(d.get(i));
					pm.setWqty(d.get(j));
					pm.setHqty(d.get(k));
					if(pm.isOk()){
						pm.setSku(this);
						r.add(pm);
					}
				}
			}
		}
		return r;
		
	}
	
	public static void main(String[] args) {
		SKUModel a = new SKUModel();
		a.setQty(6);
		List l = a.findAllConditions();
		for(int i = 0;i<l.size();i++){
			System.out.println(l.get(i));
		}
	}
}
