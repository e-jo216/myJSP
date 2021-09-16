<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:if test="${check==false}">
	<script>
		alert('관리자 권한이 없습니다.');
		history.go(-1);
	</script>
</c:if> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>후원 신청자</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_donList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_footer.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-container">
	<span class="page-name">후원자</span>
	<div class="list-container">
		<div class="top">
			<div class="btn-container">
				<input type="button" value="목록" class="cancel btns" onclick="location.href='listVolunteer.do'">
				<input type="button" value="마이페이지" class="home btns" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
			</div>
		</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		후원자가 없습니다.
	</div>
	</c:if>
	<c:if test="${count > 0}">
	<div class="tb-container">
		<table>
			<thead class="tb-header">
				<tr>
					<th>후원자</th>
					<th>생년월일</th>
					<th>주소</th>
					<th>전화번호</th>
					<th>문자 수신</th>
					<th>메일주소</th>
					<th>메일 수신</th>
					<th>후원 종류</th>
					<th>후원 금액</th>
					<th>은행</th>
					<th>계좌번호</th>
					<th>예금주명</th>
				</tr>
			</thead>
			<tbody class="tb-content">
			<c:forEach var="donation" items="${list}">
			<tr>
				<td>${donation.name}</td>
				<td>${donation.birth}</td>
				<td>${donation.addr}</td>
				<td>${donation.tel}</td>
				<td>
				<c:if test="${donation.tel_rec == 0}">거부</c:if>
				<c:if test="${donation.tel_rec == 1}">수신</c:if>
				</td>
				<td>${donation.mail}</td>
				<td>
				<c:if test="${donation.mail_rec == 0}">거부</c:if>
				<c:if test="${donation.mail_rec == 1}">수신</c:if>
				</td>
				<td>
				<c:if test="${donation.routine == 0}">일시후원</c:if>
				<c:if test="${donation.routine == 1}">정기후원</c:if>
				</td>
				<td>${donation.money}</td>
				<td>${donation.bank}</td>
				<td>${donation.account}</td>
				<td>${donation.bank_name}</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
	</div>
	</div>
	<!-- footer 시작 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<!-- footer 끝 -->
</div>
</body>
</html>