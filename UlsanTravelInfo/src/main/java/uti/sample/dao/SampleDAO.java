package uti.sample.dao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import uti.common.dao.AbstractDAO;
 
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectMainPage(Map<String, Object> map) throws Exception{
        return (List<Map<String, Object>>)selectList("sample.selectMainPage", map);
    }

	public void insertBoard(Map<String, Object> map) throws Exception{
	    insert("sample.insertBoard", map);
	}

	public void updateHitCnt(Map<String, Object> map) throws Exception{
	    update("sample.updateHitCnt", map);
	}
	 
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
	    return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
	}

	public void updateBoard(Map<String, Object> map) throws Exception{
	    update("sample.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) throws Exception{
	    update("sample.deleteBoard", map);
	}

	//업데이트DB
	public void updateDB(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		update("sample.updateDB", map);
	}

	public String dataAccess(Map<String, Object> map,String requestUrl,String requestName) throws XmlPullParserException, IOException {
//		String PHARM_URL = "http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList";
		String PHARM_URL = requestUrl;
		String KEY = "uXF7aS58LcHItF4VsvqpoHLJ5e7VrJdl%2FeKSmIVu3qai6qZfKR9GOYB4YrH1j7gLrt9UDq7iqaeeRoFBsP%2BR6w%3D%3D";
		String firstName=requestName;
		URL url = new URL(PHARM_URL+"?ServiceKey=" + KEY);
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		xpp.setInput(bis, "utf-8");
		  
		String tag = null;
		int event_type = xpp.getEventType();
		  
		ArrayList<UlsanData> list = new ArrayList<UlsanData>();
		UlsanData temp = new UlsanData("","","","","","","","");
		
		while (event_type != XmlPullParser.END_DOCUMENT) {
		    if (event_type == XmlPullParser.START_TAG) {
		        tag = xpp.getName();
		//              System.out.println(tag.toString());
		    } else if (event_type == XmlPullParser.TEXT) {
		      /**
		   * 태그이름
		   */
//				switch(tag.toString()){
//				case "ulsanscenesTitle" :
//				temp.title = xpp.getText();
//				//          		System.out.println(temp.title);
//					break;
//				case "ulsanscenesAddr" :
//					temp.addr = xpp.getText();
//					break;
//				case "ulsanscenesXpos" :
//					temp.xpos = xpp.getText();
//					break;
//				case "ulsanscenesYpos" :
//					temp.ypos = xpp.getText();
//					break;
//				case "ulsanscenesInfo" :
//					temp.info = xpp.getText();
//					break;
//				case "ulsanscenesTraffic" :
//					temp.traffic = xpp.getText();
//					break;
//				case "ulsaanscenesTel" :
//					temp.tel = xpp.getText();
//					break;
//				case "ulsaanscenesRegDt" :
//				  		temp.regdt = xpp.getText();
//				  		break;
//				  	default :
//				  		break;
//				}
		    	String tagName=tag.toString();
		    	if(tagName.equals(firstName+"Title")||tagName.equals(firstName+"Name")){
		    		temp.title = xpp.getText();
		    	}else if(tagName.equals(firstName+"Addr")){
		    		temp.addr = xpp.getText();
		    	}else if(tagName.equals(firstName+"Xpos")){
		    		temp.xpos = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Ypos")){
		    		temp.ypos = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Info")){
		    		temp.info = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Traffic")){
		    		temp.traffic = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"RegDt")){
		    		temp.regdt = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Tel")){
		    		temp.tel = xpp.getText();
		    	}
		  } else if (event_type == XmlPullParser.END_TAG) {
		      tag = xpp.getName();
		      if (tag.equals("list")) {
		              list.add(new UlsanData(temp.title,temp.addr,temp.xpos,temp.ypos,temp.info,temp.traffic,temp.tel,temp.regdt));
		          }
		  }
		      event_type = xpp.next();
		}
//		String test="";
//		String data = "myObj = { \"data\":[";
		String data = "";
		for(UlsanData entity : list){
		    data+=entity.returnJson()+"^";
//		    test=entity.returnJson();
		}
		data = data.substring(0, data.length()-1); 
//		data += "}";
		System.out.println(data);
		return data;
//		return test;
	}
}
//모든 쿼리는 "NAMESPACE . SQL ID" 의 구조 Sample_SQL의 네임스페이스와 아이디



class UlsanData{
	String title;
	String addr;
	String xpos;
	String ypos;
	String info;
	String traffic;
	String tel;
	String regdt;
	
	public UlsanData(String title, String addr,	String xpos, String ypos, String info, String traffic, String tel, String regdt){
		this.title=title;
		this.addr=addr;
		this.xpos=xpos;
		this.ypos=ypos;
		this.info=info;
		this.traffic=traffic;
		this.tel=tel;
		this.regdt=regdt;
	}
	
	public void showInfo(){
		System.out.println("------------------------------------");
		System.out.println(title);
		System.out.println(addr);
		System.out.println(xpos);
		System.out.println(ypos);
		System.out.println(info);
		System.out.println(traffic);
		System.out.println(tel);
		System.out.println(regdt);
		System.out.println("------------------------------------");
	}
	
	public String returnJson(){
		return "{\"title\": \""+ title + "\" , \"xpos\" : \""+xpos + "\" , \"ypos\" : \""+ypos +"\"}";
	}
}

