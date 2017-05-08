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
	href="<c:url value="/resources/nav.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery/css/jquery.contextMenu.css "/>" />

<c:set var="yazi" value="Kullanıcı Bilgileri" />
<c:if test="${userInfo.selahiyet}">
	<c:set var="yazi" value="Kullanıcı Listesi" />
</c:if>
<div id="divUstBaslik">
	<nav class="sag" id="nvMenu">
		<ul>
			<li><a href="">Menü</a>
				<ul>
					<li><a href="/murakib/evrad">Evrad Okumalarım</a></li>
					<li><a href="/murakib/kitab">Kitab Okumalarım</a></li>
					<li><a href="/murakib/kullanicilar">${yazi}</a></li>
					<c:if test="${userInfo.selahiyet || userInfo.extSelahiyet}">
						<li><a href="/murakib/kullaniciekle">Kullanıcı Ekle</a></li>
						<li><a href="/murakib/grublar">Grub Listesi</a></li>
						<li><a href="/murakib/grubekle">Grub Ekle</a></li>
					</c:if>
					<li><a href="/murakib/kitababoneekle">Kitab Abone Ekle</a></li>
					<li><a href="/murakib/evradaboneekle">Evrad Abone Ekle</a></li>
					<c:if test="${userInfo.extSelahiyet}">
						<li><a href="/murakib/evradlistesi">Evrad Listesi</a></li>
						<li><a href="/murakib/evradbolumlemeekle">Evrad Ekle</a></li>
					</c:if>
				</ul></li>
			<li><a href="/murakib/anasayfa">AnaSayfa</a></li>
			<li><a href="/murakib/kullanicicikisi">Çıkış</a></li>
		</ul>
	</nav>
	<select id="slctDonemler" onchange="donemAyarla(this.value)">
		<c:forEach items="${donemler}" var="donem1" varStatus="no">
			<option value="${donem1.id}"
				<c:if test="${donem1.id == donem.id}"> selected </c:if>><fmt:formatDate
					pattern="dd.MM.yyyy" value="${donem1.baslamaTarihi}" /> -
				<fmt:formatDate pattern="dd.MM.yyyy" value="${donem1.bitisTarihi}" /></option>
		</c:forEach>
	</select>
</div>
