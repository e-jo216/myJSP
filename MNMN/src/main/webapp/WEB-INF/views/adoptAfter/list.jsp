<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입양후기 게시판목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_adoptAfter.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_footer.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div id="container"><br>
	<span id="title">찾았냥</span>
	<span id="title3">${count}개의 글</span>
	<span id="title2">입양후기 게시판</span>
	<div class="list-space align-right">
		<c:if test="${!empty user_num && user_grade=='2'}">
			<input type="button" value="글쓰기" id="write-bt" onclick="location.href='${pageContext.request.contextPath}/adoptAfter/writeForm.do'">
		</c:if>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		등록된 게시물이 없습니다.
	</div>
	</c:if>
	<c:if test="${count > 0 }">
	<div class="row">
    <c:forEach var="adoptAfter" items="${list}" varStatus="after">
	<div class="col-sm-12 col-md-6 col-xl-4">
		<div class="card">
		<div style="text-align: center;">
		<i class="fas fa-circle fa-xs" style="color: #E8F0F2; border: 1px solid gray; border-radius: 50%;"></i>
		</div>
		<img src="${pageContext.request.contextPath}/upload/${adoptAfter.after_photo}" class="afterpet-img">
		<div class="card-body" style="padding: 7px;">
			<h1 class="tag"><a href="afterDetail.do?after_num=${adoptAfter.after_num}" id="afterDetail_title">${adoptAfter.after_title}</a></h1>
		    <c:set var="content2" value="${adoptAfter.after_content}"/>
		    <span id="content" class="card-text">
		    <a href="afterDetail.do?after_num=${adoptAfter.after_num}" id="afterDetail_content">
		    <c:if test="${fn:length(content2)<40}">${content2}</c:if>
		    <c:if test="${fn:length(content2)>40}">${fn:substring(content2,0,40)}...</c:if>
		    </a>
		    </span>
		    <span id="listID_DATE"> ${adoptAfter.member_id} ${adoptAfter.after_date}</span>
		</div><%--card-body --%>
		</div><%--card --%>
	</div>
    </c:forEach>
    </div>
	</c:if>
	<%-- 검색기능 --%>
	<form id="search_form" action="list.do" method="get">
		<ul class="search">
			<li class="search2">
				<select name="keyfield" class="search3">
					<option value="1">제목</option>
					<option value="2">작성자</option>
					<option value="3">내용</option>
				</select>
				<input type="search" size="16" name="keyword" id="keyword" style="outline: none; resize: none;">
				<input type="submit" value="찾기" class="search4">
			</li>
		</ul>
	</form>
	<%-- 검색기능 끝 --%>
	<div class="align-center">
		${pagingHtml}
	</div>
	</div>
		<!-- footer 시작 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<!-- footer 끝 -->
</div>
</body>
</html>