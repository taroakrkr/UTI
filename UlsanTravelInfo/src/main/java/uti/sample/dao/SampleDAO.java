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
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectComment(Map<String, Object> map, String TITLE,String XY) throws Exception{
		map.put("TITLE", (String)TITLE);
		map.put("XY", (String)XY);
		return (List<Map<String, Object>>)selectList("sample.selectComment",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> insertComment(Map<String, Object> map, String TITLE, String XY, String CREA_NAME, String COMMENT, Integer STAR_POINT) {
		map.put("TITLE", (String)TITLE);
		map.put("XY", (String)XY);
		map.put("CREA_NAME", (String)CREA_NAME);
		map.put("COMMENT", (String)COMMENT);
		map.put("STAR_POINT", (Integer)STAR_POINT);
		
		insert("sample.insertComment", map);
		return (List<Map<String, Object>>)selectList("sample.selectComment",map);
	}
	
	public String dataAccess(Map<String, Object> map,String requestUrl,String requestName) throws XmlPullParserException, IOException {
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
		UlsanData temp = new UlsanData("","","","","");
		
		while (event_type != XmlPullParser.END_DOCUMENT) {
		    if (event_type == XmlPullParser.START_TAG) {
		        tag = xpp.getName();
		    } else if (event_type == XmlPullParser.TEXT) {
		      /**
		   * 태그이름
		   */
		    	String tagName=tag.toString();
		    	if(tagName.equals(firstName+"Title")||tagName.equals(firstName+"Name")){
		    		temp.title = xpp.getText();
		    	}else if(tagName.equals(firstName+"Addr")||tagName.equals(firstName+"NewAddr")){
		    		temp.addr = xpp.getText();
		    	}else if(tagName.equals(firstName+"Xpos")){
		    		temp.xpos = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Ypos")){
		    		temp.ypos = xpp.getText();
		    	}
		    	else if(tagName.equals(firstName+"Tel")){
		    		temp.tel = xpp.getText();
		    	}
		  } else if (event_type == XmlPullParser.END_TAG) {
		      tag = xpp.getName();
		      if (tag.equals("list")) {
		              list.add(new UlsanData(temp.title,temp.addr,temp.xpos,temp.ypos,temp.tel));
		          }
		  }
		      event_type = xpp.next();
		}
		String data = "";
		for(UlsanData entity : list){
		    data+=entity.returnJsonString()+"^";
		}
		data = data.substring(0, data.length()-1); 
		return data;
	}

	
}
//모든 쿼리는 "NAMESPACE . SQL ID" 의 구조 Sample_SQL의 네임스페이스와 아이디



class UlsanData{
	String title;
	String addr;
	String xpos;
	String ypos;
	String tel;

	public UlsanData(String title, String addr,	String xpos, String ypos, String tel){
		this.title=title;
		this.addr=addr;
		this.xpos=xpos;
		this.ypos=ypos;
		this.tel=tel;

	}

	
	public String returnJsonString(){
		return "{\"title\": \""+ title + "\" , \"addr\" : \""+addr + "\" , \"tel\" : \""+tel + "\" , \"xpos\" : \""+xpos + "\" , \"ypos\" : \""+ypos +"\"}";
	}
}

