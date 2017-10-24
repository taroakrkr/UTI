//package uti.sample.service;
//
//import java.io.BufferedInputStream;
//import java.net.URL;
//import java.util.ArrayList;
// 
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
// 
//public class UTIParser {
// 
//    public final static String PHARM_URL = "http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList";
//    public final static String KEY = "uXF7aS58LcHItF4VsvqpoHLJ5e7VrJdl%2FeKSmIVu3qai6qZfKR9GOYB4YrH1j7gLrt9UDq7iqaeeRoFBsP%2BR6w%3D%3D";
// 
//    public UTIParser() {
//        try {
//            apiParserSearch();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
// 
//    
//    /**
//     * 
//     * @throws Exception
//     */
//    public void apiParserSearch() throws Exception {
//        URL url = new URL(getURLParam(null));
// 
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setNamespaceAware(true);
//        XmlPullParser xpp = factory.newPullParser();
//        BufferedInputStream bis = new BufferedInputStream(url.openStream());
//        xpp.setInput(bis, "utf-8");
//        
//        String tag = null;
//        int event_type = xpp.getEventType();
//        
//        ArrayList<UlsanData> list = new ArrayList<UlsanData>();
//        UlsanData temp = new UlsanData("","","","","","","","");
//
//        while (event_type != XmlPullParser.END_DOCUMENT) {
//            if (event_type == XmlPullParser.START_TAG) {
//                tag = xpp.getName();
////                System.out.println(tag.toString());
//            } else if (event_type == XmlPullParser.TEXT) {
//                /**
//                 * 태그이름
//                 */
//            	switch(tag.toString()){
//            	case "ulsanscenesTitle" :
//            		temp.title = xpp.getText();
////            		System.out.println(temp.title);
//            		break;
//            	case "ulsanscenesAddr" :
//            		temp.addr = xpp.getText();
//            		break;
//            	case "ulsanscenesXpos" :
//            		temp.xpos = xpp.getText();
//            		break;
//            	case "ulsanscenesYpos" :
//            		temp.ypos = xpp.getText();
//            		break;
//            	case "ulsanscenesInfo" :
//            		temp.info = xpp.getText();
//            		break;
//            	case "ulsanscenesTraffic" :
//            		temp.traffic = xpp.getText();
//            		break;
//            	case "ulsaanscenesTel" :
//            		temp.tel = xpp.getText();
//            		break;
//            	case "ulsaanscenesRegDt" :
//            		temp.regdt = xpp.getText();
//            		break;
//            	default :
//            		break;
//            	}
//            } else if (event_type == XmlPullParser.END_TAG) {
//                tag = xpp.getName();
////                System.out.println("/"+tag.toString());
//                if (tag.equals("list")) {
//                    list.add(new UlsanData(temp.title,temp.addr,temp.xpos,temp.ypos,temp.info,temp.traffic,temp.tel,temp.regdt));
//                }
//            }
//            
//            event_type = xpp.next();
//        }
////        printList(list);
//        String data = "myObj = { 'data':[";
//        for(UlsanData entity : list){
//            data+=entity.returnJson()+",";
//        }
//        data = data.substring(0, data.length()-1); 
//        data += " ]};";
//        System.out.println(data);
//    }
//    
//    /**
//     * 결과 값을 출력해본다.
//     * @param list
//     */
//    private void printList(ArrayList<UlsanData> list){
//        for(UlsanData entity : list){
//            entity.showInfo();
//        }
//    }
//    
//    
//    private String getURLParam(String search){
//        String url = PHARM_URL+"?ServiceKey="+KEY;
//        if(search != null){
////        url = url+"&toiletEntId="+search;
//        }
//        return url;
//    }
// 
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        new UTIParser();
//    }
// 
//}
//
//public class UlsanData{
//	String title;
//	String addr;
//	String xpos;
//	String ypos;
//	String info;
//	String traffic;
//	String tel;
//	String regdt;
//	
//	public UlsanData(String title, String addr,	String xpos, String ypos, String info, String traffic, String tel, String regdt){
//		this.title=title;
//		this.addr=addr;
//		this.xpos=xpos;
//		this.ypos=ypos;
//		this.info=info;
//		this.traffic=traffic;
//		this.tel=tel;
//		this.regdt=regdt;
//	}
//	
//	public void showInfo(){
//		System.out.println("------------------------------------");
//		System.out.println(title);
//		System.out.println(addr);
//		System.out.println(xpos);
//		System.out.println(ypos);
//		System.out.println(info);
//		System.out.println(traffic);
//		System.out.println(tel);
//		System.out.println(regdt);
//		System.out.println("------------------------------------");
//	}
//	
//	public String returnJson(){
//		return "{'title':"+ title + ", 'xpos' : "+xpos + ", 'ypos' : "+ypos +"}";
//	}
//}