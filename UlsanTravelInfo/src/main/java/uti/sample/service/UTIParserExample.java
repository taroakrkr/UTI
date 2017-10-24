//package uti.sample.service;
//
//import java.io.BufferedInputStream;
//import java.net.URL;
//import java.util.ArrayList;
// 
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
// 
//public class UTIParserExample {
// 
//    public final static String PHARM_URL = "http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList";
//    public final static String KEY = "YZX4VtJSuD9EuHqXyW5TaWtk9awBvo6bOOcFb%2F7q26SQeVJO1quNsHFdDUfr8vq0m3E0phC7sTCsV8r65xVzJw%3D%3D";
// 
//    public UTIParserExample() {
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
//        ArrayList<String> list = new ArrayList<String>();
//        
//        String t_nm = null;
//        while (event_type != XmlPullParser.END_DOCUMENT) {
//            if (event_type == XmlPullParser.START_TAG) {
//                tag = xpp.getName();
//            } else if (event_type == XmlPullParser.TEXT) {
//                /**
//                 * 이름을 가져온다
//                 */
//                if(tag.equals("ulsanscenesTitle")){
//                    t_nm = xpp.getText();
//                }
//            } else if (event_type == XmlPullParser.END_TAG) {
//                tag = xpp.getName();
//                if (tag.equals("list")) { //data 라는 곳에 list로 나누어져 있으므로 list값을 하나의 데이터라 생각하고 하나식 긁어 오면 각각
//                    list.add(t_nm);
//                }
//            }
// 
//            event_type = xpp.next();
//        }
//        printList(list);
//    }
//    
//    /**
//     * 결과 값을 출력해본다.
//     * @param list
//     */
//    private void printList(ArrayList<String> list){
//        for(String entity : list){
//            System.out.println(entity);
//        }
//        
//        
//    }
//    
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
//        new UTIParserExample();
//    }
// 
//}