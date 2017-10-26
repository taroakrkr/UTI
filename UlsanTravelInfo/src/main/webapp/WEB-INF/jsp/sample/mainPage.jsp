<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
	<h1>울산 관광 정보 시스템</h1>
	
    <div class="mapDiv" style="width: 60%; height: 550px; float: left; margin-left: 5%; margin-right: 2%; ">
		<div id="googleMap" style="width: 100%; height: 100%;"></div>
		<br>
		<p>API 콘솔 주소 =  <a href="https://console.developers.google.com/projectselector/apis/dashboard?hl=ko">https://console.developers.google.com/projectselector/apis/dashboard?hl=ko</a></p>
	</div>
	<div class="buttonDiv" style="width: 25%; height: 500px; float: right; margin-left: 2%;margin-right: 5%; text-align: center; padding-top: 2%;padding-bottom: 2%; background-color: lightblue;">
		<input type="submit" value="울산12경" id="ulsan12"/>
		<input type="submit" value="무료와이파이존" id="ulsanPubwifi"/>
		<input type="submit" value="도시공원" id="ulsanPark"/>
		<input type="submit" value="전시/박물관" id="ulsanMuseum"/>
		<input type="submit" value="농어촌체험마을" id="ruralVillage"/>
		<input type="submit" value="전통시장" id="ulsanmarket"/>
		<input type="submit" value="공중화장실" id="ulsanToilet"/>
	</div>
	
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
        	new settingButton("#ulsan12","http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList","ulsanscenes","TD");
        	new settingButton("#ulsanPubwifi","http://data.ulsan.go.kr/rest/ulsanpubwifi/getUlsanpubwifiList","ulsanpubwifi","wifi");
        	new settingButton("#ulsanPark","http://data.ulsan.go.kr/rest/ulsanpark/getUlsanParkList","ulsanpark","park");
        	new settingButton("#ulsanMuseum","http://data.ulsan.go.kr/rest/ulsanexhandmus/getUlsanexhandmusList","ulsanexhandmus","museum");
        	new settingButton("#ruralVillage","http://data.ulsan.go.kr/rest/ulsanruralvillage/getUlsanruralvillageList","ulsanruralvillage","village");
        	new settingButton("#ulsanmarket","http://data.ulsan.go.kr/rest/ulsanmarket/getUlsanmarketList","ulsanmarket","market");
        	new settingButton("#ulsanToilet","http://data.ulsan.go.kr/rest/ulsantoilet/getUlsantoiletList","toilet","toilet");
            
        });
        var icons = {
                TD: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal4/icon38.png"
                },
                wifi: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon30.png"
                },
                park: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal2/icon4.png"
                },
                museum: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal3/icon19.png"
                },
                village: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal3/icon48.png"
                },
                market: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon36.png"
                },
                toilet: {
              	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon19.png"
                }
              };
        //-----------------------------------------------------------------------------------------------------------------------
        var settingButton = function(buttonID,url,serviceID,icon){
        	var markers=this.markers=[];
        	var onOff=this.onOff=false;

        	 $(buttonID).on("click", function(e){
             	e.preventDefault();
             	if(onOff){
             		onOff=false;
             		setMapOnAll(null);
             		markers=[];
             		
           		}else{
           			onOff=true;
           			addMarkers(url,serviceID);
       			}
             });

        	 function addMarkers(requestUrl,firstName){
 	            var result=
 	            (function(temp){
 				    $.ajax({
 				        url : "dataAccess.do",
 				        dataType: "text",
 				        async: false,
 				        data: {"requestUrl" : requestUrl, "firstName":firstName},
 				        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
 				        success : function(responseData,data,returnData) {
 				        	var afterStr = returnData.responseText.split('^');
 				        	temp= afterStr;
 				        },
 				        error : function(responseData,request,status,error){
 				            console.log("에러");
 				        }
 	           		});
 				    return temp;
 	            })(result);
 	            
 				for(var i=0;i<result.length;i++){
 					var xy=JSON.parse(result[i]);
 					var marker = new google.maps.Marker({
 				          position: new google.maps.LatLng(xy.ypos,xy.xpos),
 				          map: map,
 				          title: xy.title,
 				         icon: icons[icon].icon
 				        });
 					 markers.push(marker);
 				}	
         	}
        	function setMapOnAll(map) {
				for (var i = 0; i < markers.length; i++) {
					markers[i].setMap(map);
				}
			}
        }

        function initMap() {
			map = new google.maps.Map(document.getElementById('googleMap'), {
            	zoom: 11,
            	center:new google.maps.LatLng(35.53889, 129.31667)//울산좌표
          	});
        }
        
    </script>
</body>
</html>
