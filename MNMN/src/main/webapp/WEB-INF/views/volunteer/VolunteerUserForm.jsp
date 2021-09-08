<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>힘드냥 도울개</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var dateChecked = 0;
		
		//날짜 인원 초과 체크
		$("#date_check").click(function(){
			if($("#date").val().trim() == ""){
				alert("날짜를 선택하세요");
				$("date").focus();
				$("date").val("");
				return;
			}
			
			if($("#time").val().trim() == ""){
				alert("날짜를 선택하세요");
				$("time").focus();
				$("time").val("");
				return;
			}
			
			$("#message_date".text="");
			
			$.ajax({
				url:"checkDateFull.do",
				type:"post",
				data:{date:$("#date").val()},
				datatype:"json",
				cache:false,
				timeout:30000,
				success:function(param){
					
				},
				error:function(){
					dateChecked = 0;
					alert("네트워크 오류 발생");
				}
			});
		});
		
		$("#volunteer_form #date".keydown(function(){
			dateChecked = 0;
			$("#message_date").text("");
		});
		
		$('#register_form').submit(function(){
			if($('#date').val().trim()==''){
				alert('날짜를 입력하세요!');
				$('#date').focus();
				$('#date').val('');
				return false;
			}
			if($('#time').val().trim()==''){
				alert('시간을 입력하세요!');
				$('#time').focus();
				$('#time').val('');
				return false;
			}
			if(dateChecked == 0){
				alert('해당 날짜에 봉사가 가능한지 확인해주세요!');
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>봉사활동 신청</h2>
	<form id="volunteer_form" action="volunteerUser.do" method="post">
		<ul>	
		<li>
			<label for="date">봉사 신청일</label>
			<input type="date" name="date" id="date">
			<input type="button" value="봉사가능확인" id="date_check">
			<span id="message_date"></span>
		</li>
		<li>
			<label for="time">봉사 시작 시간</label>
			<input type="number" id="time" name="time" min="9" max="18" required>
		</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="신청">
			<input type="button" value="홈으로" 
			        onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</form>
</div>
</body>
</html>