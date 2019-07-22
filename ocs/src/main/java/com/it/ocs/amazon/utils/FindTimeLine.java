package com.it.ocs.amazon.utils;

import java.util.ArrayList;
import java.util.List;

import com.it.ocs.amazon.model.ReportRequestListModel;

public class FindTimeLine {
	private List<ReportRequestListModel> data;
	private String startDate;
	private String endDate;
	private List<ReportRequestListModel> startList;
	private List<ReportRequestListModel> returnData;
	
	public FindTimeLine(List<ReportRequestListModel> data,String startDate,String endDate){
		this.data = data;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public List<ReportRequestListModel> getReturn(){
		returnData = new ArrayList<>();
		ReportRequestListModel re = this.getDataTree();
		if(null != re){
			ReportRequestListModel mid = re;
			returnData.add(re);
			System.out.println(mid.getStartDate()+"--"+mid.getEndDate());
			if(re.hasChild()){
				do{
					mid = mid.getChildren().get(0);
					returnData.add(mid);
					System.out.println(mid.getStartDate()+"--"+mid.getEndDate());
				}while(null != mid&&mid.hasChild());
			}
			
		}
		for(ReportRequestListModel result : returnData){
			if(result.getIsGetData()== 0){
				return null;
			}
		}
		return returnData;
	}
	
	public ReportRequestListModel getDataTree(){
		startList = new ArrayList<>();
		for(ReportRequestListModel model : data){
			if(startDate.equals(model.getStartDate())){
				if(endDate.equals(model.getEndDate())){
					return model;
				}else{
					List<ReportRequestListModel> mid = getChildren(model.getEndDate());
					if(null != mid && mid.size()> 0){
						model.setChildren(getChildren(model.getEndDate()));
						startList.add(model);
					}
				}
				
			}
		}
		return startList.get(0);
	}

	private List<ReportRequestListModel> getChildren(String start) {
		List<ReportRequestListModel> children = new ArrayList<>();
		for(ReportRequestListModel model : data){
			if(model.getStartDate().equals(start)){
				List<ReportRequestListModel> mid = getChildren(model.getEndDate());
				if(null == mid){
					continue;
				}else if(mid.size() == 0){
					if(model.getEndDate().equals(endDate)){
						model.setChildren(mid);
						children.add(model);
						return children;
					}else{
						return null;
					}
				}else{
					model.setChildren(mid.subList(0, 1));
					children.add(model);
				}
			}
		}
		return children;
	}
	
	public static void main(String[] args) {
		List<ReportRequestListModel> data1 = new ArrayList<>();
		ReportRequestListModel r1 = new ReportRequestListModel();
		r1.setStartDate("12:00");
		r1.setEndDate("13:00");
		data1.add(r1);
		ReportRequestListModel r2 = new ReportRequestListModel();
		r2.setStartDate("12:00");
		r2.setEndDate("15:00");
		data1.add(r2);
		ReportRequestListModel r3 = new ReportRequestListModel();
		r3.setStartDate("13:00");
		r3.setEndDate("15:00");
		data1.add(r3);
		data1.add(r3);
		ReportRequestListModel r4 = new ReportRequestListModel();
		r4.setStartDate("13:00");
		r4.setEndDate("14:00");
		data1.add(r4);
		ReportRequestListModel r5 = new ReportRequestListModel();
		r5.setStartDate("14:00");
		r5.setEndDate("15:00");
		data1.add(r5);
		ReportRequestListModel r6 = new ReportRequestListModel();
		r6.setStartDate("14:00");
		r6.setEndDate("17:00");
		data1.add(r6);
		ReportRequestListModel r7 = new ReportRequestListModel();
		r7.setStartDate("15:00");
		r7.setEndDate("16:00");
		data1.add(r7);
		ReportRequestListModel r8 = new ReportRequestListModel();
		r8.setStartDate("12:00");
		r8.setEndDate("17:00");
		data1.add(r8);
		FindTimeLine line = new FindTimeLine(data1, "12:00", "16:00");
		ReportRequestListModel re = line.getDataTree();
		if(null != re){
			ReportRequestListModel mid = re;
			System.out.println(mid.getStartDate()+"--"+mid.getEndDate());
			if(re.hasChild()){
				do{
					mid = mid.getChildren().get(0);
					System.out.println(mid.getStartDate()+"--"+mid.getEndDate());
				}while(null != mid&&mid.hasChild());
			}
			
		}
		System.out.println("end");
	}
}
