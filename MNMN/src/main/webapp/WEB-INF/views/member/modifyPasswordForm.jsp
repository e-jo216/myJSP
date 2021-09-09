<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#password_form').submit(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요!');
				$('#id').focus();
				$('#id').val('');
				return false;
			}
			if($('#origin_passwd').val().trim()==''){
				alert('현재 비밀번호를 입력하세요!');
				$('#origin_passwd').focus();
				$('#origin_passwd').val('');
				return false;
			}
			if($('#passwd').val().trim()==''){
				alert('새로운 비밀번호를 입력하세요!');
				$('#passwd').focus();
				$('#passwd').val('');
				return false;
			}
			if($('#cpasswd').val().trim()==''){
				alert('새로운 비밀번호 확인을 입력하세요!');
				$('#cpasswd').focus();
				$('#cpasswd').val('');
				return false;
			}
			if($('#passwd').val()!=$('#cpasswd').val()){
				alert('새 비밀번호와 새 비밀번호 확인이 불일치 합니다.');
				$('#cpasswd').val('').focus();
				return false;
			}
		});
		
		//새 비밀번호 확인 후 새 비밀번호를 수정하는 경우 확인란 초기화
		$('#passwd').keyup(function(){
			$('#cpasswd').val('');
			$('#message_cpasswd').text('');
		});
		
		//새 비밀번호와 새 비밀번호 확인 일치 여부 확인
		$('#cpasswd').keyup(function(){
			if($('#passwd').val()==$('#cpasswd').val()){
				$('#message_cpasswd').css('color','blue').text('비밀번호 일치');
			}else{
				$('#message_cpasswd').css('color','red').text('비밀번호 불일치');
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>비밀번호 수정</h2>
	<form action="modifyPassword.do" method="post" id="password_form">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" maxlength="12">
			</li>
			<li>
				<label for="origin_passwd">현재 비밀번호</label>
				<input type="password" name="origin_passwd" id="origin_passwd" maxlength="12">
			</li>
			<li>
				<label for="passwd">새 비밀번호</label>
				<input type="password" name="passwd" id="passwd" maxlength="12">
			</li>
			<li>
				<label for="cpasswd">새 비밀번호 확인</label>
				<input type="password" name="cpasswd" id="cpasswd" maxlength="12">
				<span id="message_cpasswd"></span>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="수정">
			<input type="button" value="취소" onclick="location.href='myPage.do'">
		</div>
	</form>
</div>
</body>
</html>