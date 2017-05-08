
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.sendika" /></title>
<link href="<c:url value="/resources/resim/simge.ico" />"
	rel="shortcut icon" type="image/vnd.microsoft.icon" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/includes/ustbaslik.jsp" />

	<div class="kutu ">
		<table>
			<tr>
				<th>İsim</th>
				<th>Parça Sayısı</th>
				<th colspan="2"></th>
			</tr>

			<c:forEach items="${evradBolumlemeListesi}" var="evradBolumleme"
				varStatus="no">
				<c:remove var="sinif" />
				<c:if test="${no.count % 2 == 0}">
					<c:set var="sinif" value="ikinciSatir" />
				</c:if>
				<tr class="${sinif}" id="evradBolumlemeSatir${evradBolumleme.id}">
					<td><input type="hidden" id="hdnEvradIsim${evradBolumleme.id}"
						value="${evradBolumleme.isim}"> <input
						id="evradIsim${evradBolumleme.id}" class="textBox"
						value="${evradBolumleme.isim}"
						onkeyup="evradBolumlemeDegisti(${evradBolumleme.id})"
						onchange="evradBolumlemeDegisti(${evradBolumleme.id})" /></td>
					<td><input type="hidden"
						id="hdnEvradParcaSayisi${evradBolumleme.id}"
						value="${evradBolumleme.parcaSayisi}"> <input
						id="evradParcaSayisi${evradBolumleme.id}" class="kucukTextBox"
						value="${evradBolumleme.parcaSayisi}"
						onkeyup="evradBolumlemeDegisti(${evradBolumleme.id})"
						onchange="evradBolumlemeDegisti(${evradBolumleme.id})" /></td>
					<td>
						<div class="divTus sol">
							<input type="image"
								src="<c:url value="/resources/resim/rahle.png" />"
								class="doldur"
								onclick="window.location.href='/murakib/bolumabonelikler/${evradBolumleme.id}'" />
						</div>
					</td>
					<td>
						<div class="divTus sol">
							<input type="image"
								src="<c:url value="/resources/resim/liste.png" />"
								class="doldur"
								onclick="window.location.href='/murakib/bolumkisimlar/${evradBolumleme.id}'" />
						</div>
					</td>
					<td>
						<div class="divTus gizli sag"
							id="divBolumlemeVazgec${evradBolumleme.id}">
							<input type="image"
								src="<c:url value="/resources/resim/carpi.png" />"
								class="doldur" onclick="bolumlemeVazgec(${evradBolumleme.id})" />
						</div>
					</td>
					<td>
						<div class="divTus gizli sag"
							id="divBolumlemeKaydet${evradBolumleme.id}">
							<input type="image"
								src="<c:url value="/resources/resim/disket.png" />"
								class="doldur" onclick="bolumlemeKaydet(${evradBolumleme.id})" />
						</div>
					</td>
			</c:forEach>
		</table>
	</div>

</body>
</html>