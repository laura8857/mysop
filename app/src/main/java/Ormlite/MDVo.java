package Ormlite;


import java.util.ArrayList;
import java.util.HashMap;


public class MDVo implements Cloneable {

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}


	public boolean rowEditable=true;//控制列是否可編輯
	public String txMode="";//異動模式(I,)
	//public var TxEmp:String;//異動人員
	//public var TxDat:String;//異動日期
	//public var TxTm:String;//異動時間
	
	private int icon;//listview顯示icon用
	private int color;////listview顯示顏色用

	private boolean check=false;////listview顯示check用	
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}


	private HashMap<String,ArrayList> detailData=new HashMap<String,ArrayList>();//明細["A",PAC]
	
	//以子區塊代號取得子區塊明細資料
	public ArrayList getDetailData(String block){
		
		return detailData.get(block);
		
	}
	
	public void setDetailData(String block,ArrayList value){
		if(detailData==null){
			detailData=new HashMap<String,ArrayList>();
		}
		detailData.put(block, value);
	}
	
	
	
	//查核是否有明細資料
	public boolean checkHasDetailData(){
		//var collection:PagedArrayCollection;
		boolean ret=false;
		/**
		for each(var a:Array in detailData){
			collection=a[1];
			if(collection.length>0){
				ret=true;
				break;
			}
		}
		*/
		return ret;
	}
	
	
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
