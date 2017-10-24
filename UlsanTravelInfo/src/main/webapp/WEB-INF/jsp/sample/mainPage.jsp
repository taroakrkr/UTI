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
		<input type="submit" value="울산12경도" id="ulsan12"/>
		<input type="submit" value="무료와이파이존" id="ulsanPubwifi"/>
	</div>
	
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#ulsan12").on("click", function(e){e.preventDefault();setButton("http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList","ulsanscenes");})
        });
        $(document).ready(function(){
            $("#ulsanPubwifi").on("click", function(e){e.preventDefault();setButton("http://data.ulsan.go.kr/rest/ulsanpubwifi/getUlsanpubwifiList","ulsanpubwifi");})  
        });
         
        
      //------------------------------------------------------------------------------------------------------------------------------------
        function setButton(requestUrl,firstName){
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
					addMarker(xy.title,new google.maps.LatLng(xy.ypos,xy.xpos));
				}
        }
		//------------------------------------------------------------------------------------------------------------------------------------

		var map;
     	var markers = [];
     	
        function initMap() {
			map = new google.maps.Map(document.getElementById('googleMap'), {
            	zoom: 11,
            	center:new google.maps.LatLng(35.53889, 129.31667)//울산좌표
          	});
// 			var coordinate = new google.maps.LatLng(35.53889,129.31667);//마크할 좌표
// 			addMarkers(coordinate, map);
			
        }

// 		function addMarkers(coordinate, resultsMap){
// 			var marker = new google.maps.Marker({
// 				map: resultsMap,
// 				position: coordinate
// 			});
// // 			marker.setMap(null);//마커지우는법
// 		}
	
		function addMarker(title,location) {
	        var marker = new google.maps.Marker({
	          position: location,
	          map: map,
	          title: title
	        });
	        markers.push(marker);
	      }

	      // Sets the map on all markers in the array.
	      function setMapOnAll(map) {
	        for (var i = 0; i < markers.length; i++) {
	          markers[i].setMap(map);
	        }
	      }

	      // Removes the markers from the map, but keeps them in the array.
	      function clearMarkers() {
	        setMapOnAll(null);
	      }

	      // Shows any markers currently in the array.
	      function showMarkers() {
	        setMapOnAll(map);
	      }

	      // Deletes all markers in the array by removing references to them.
	      function deleteMarkers() {
	        clearMarkers();
	        markers = [];
	      }
    </script>
</body>
</html>
