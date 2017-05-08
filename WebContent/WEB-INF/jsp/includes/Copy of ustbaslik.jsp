<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value="/resources/jquery/jquery-1.9.1.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.ui.core.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.ui.widget.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.ui.mouse.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.ui.draggable.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.ui.resizable.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.scrollTo-min.js" />"></script>
<script src="<c:url value="/resources/genel.js" />"></script>
<script src="<c:url value="/resources/popup.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.datetimeentry.js"/>"></script>
<script src="<c:url value="/resources/grupkayit.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.dateentry.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery.contextMenu.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-blink.js"/>"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/genel.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery/css/jquery.contextMenu.css "/>" />
<div id="divUstBaslik">
	<table class="ikiYanaYasli">
		<tr>
			<td><input type="button" value="Ana Sayfa" class="ortaYazi tus"
				onclick="window.location.href='/murakib/anasayfa'"></td>
			<td><a href="/murakib/evrad"> Dönem : </a> <select
				id="slctDonemler" onchange="donemAyarla(this.value)">
					<!-- 
				<option value="0">Tümü</option>
				 -->
					<c:forEach items="${donemler}" var="donem1" varStatus="no">
						<option value="${donem1.id}"
							<c:if test="${donem1.id == donem.id}"> selected </c:if>><fmt:formatDate
								pattern="dd.MM.yyyy" value="${donem1.baslamaTarihi}" /> -
							<fmt:formatDate pattern="dd.MM.yyyy"
								value="${donem1.bitisTarihi}" /></option>
					</c:forEach>
			</select></td>
			<td><input type="button" value="Kitab" class="ortaYazi tus"
				onclick="window.location.href='/murakib/kitab'"></td>
			<td><input type="button" value="Ayarlar" class="ortaYazi tus"
				onclick="window.location.href='/murakib/yonetim'"></td>
			<td><div class="divTus sag">
					<input type="image"
						src="<c:url value="/resources/resim/carpi1.png" />" class="doldur"
						onclick="window.location.href='/murakib/kullanicicikisi'" />
				</div></td>
		</tr>
	</table>
</div>