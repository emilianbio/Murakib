
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
				<th>Sıra No</th>
				<th>İsim</th>
				<th>Sayfa Aralığı</th>
			</tr>

			<c:forEach items="${kisimListesi}" var="evradKisim" varStatus="no">
				<c:remove var="sinif" />
				<c:if test="${no.count % 2 == 0}">
					<c:set var="sinif" value="ikinciSatir" />
				</c:if>
				<tr class="${sinif}" id="evradKisimSatir${evradBolumleme.id}">
					<td><input type="hidden" id="hdnSiraNo${evradKisim.id}"
						value="${evradKisim.siraNo}"> <input
						id="siraNo${evradKisim.id}" class="kucukTextBox"
						value="${evradKisim.siraNo}"
						onkeyup="evradKisimDegisti(${evradKisim.id})"
						onchange="evradKisimDegisti(${evradKisim.id})" /></td>
					<td><input type="hidden" id="hdnKisimIsim${evradKisim.id}"
						value="${evradKisim.isim}"> <input
						id="kisimIsim${evradKisim.id}" class="textBox"
						value="${evradKisim.isim}"
						onkeyup="evradKisimDegisti(${evradKisim.id})"
						onchange="evradKisimDegisti(${evradKisim.id})" /></td>
					<td><input type="hidden" id="hdnSayfa${evradKisim.id}"
						value="${evradKisim.sayfaAraligi}"> <input
						id="sayfa${evradKisim.id}" class="ortaTextBox"
						value="${evradKisim.sayfaAraligi}"
						onkeyup="evradKisimDegisti(${evradKisim.id})"
						onchange="evradKisimDegisti(${evradKisim.id})" /></td>
					<td>
						<div class="divTus gizli sag" id="divKisimVazgec${evradKisim.id}">
							<input type="image"
								src="<c:url value="/resources/resim/carpi.png" />"
								class="doldur" onclick="kisimVazgec(${evradKisim.id})" />
						</div>
					</td>
					<td>
						<div class="divTus gizli sag" id="divKisimKaydet${evradKisim.id}">
							<input type="image"
								src="<c:url value="/resources/resim/disket.png" />"
								class="doldur" onclick="kisimKaydet(${evradKisim.id})" />
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>