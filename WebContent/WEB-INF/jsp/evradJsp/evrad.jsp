
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

	<div id="divEvradOkuma" class="kutu ">
		Kullanıcı : ${kullanici.isim}
		<table>
			<tr>
				<th>Tarih</th>
				<th>Evrad</th>
				<th colspan="2">Not</th>
			</tr>

			<c:forEach items="${evradOkumaListesi}" var="evradOkuma"
				varStatus="no">
				<c:remove var="sinif" />
				<c:if test="${no.count % 2 == 0}">
					<c:set var="sinif" value="ikinciSatir" />
				</c:if>
				<c:if test="${!empty evradOkuma.kayitTarihi}">
					<c:set var="sinif" value="${sinif} silik" />
				</c:if>
				<c:if test="${evradOkuma.evradAbone.id!=oncekiEvradAboneId}">
					<tr class="evradBolumlemeBaslik">
						<td colspan="4"><c:if
								test="${!empty evradOkuma.evradAbone.aciklama}">${evradOkuma.evradAbone.aciklama} </c:if>(${evradOkuma.evradKisim.evradBolumleme.isim})</td>
					</tr>
				</c:if>
				<tr class="${sinif}" id="evradSatir${evradOkuma.id}">
					<td><fmt:formatDate pattern="dd.MM.yyyy"
							value="${evradOkuma.tarih}" /></td>
					<td>${evradOkuma.evradAbone.aciklama}<c:if
							test="${!empty evradOkuma.evradKisim.isim}">(${evradOkuma.evradKisim.isim})</c:if>
						${evradOkuma.evradKisim.sayfaAraligi}
					</td>
					<td><input id="evradAciklama${evradOkuma.id}"
						name="${evradOkuma.aciklama}" class="textBox"
						value="${evradOkuma.aciklama}"
						onkeyup="evradOkumaDegisti(${evradOkuma.id})"
						onchange="evradOkumaDegisti(${evradOkuma.id})" /></td>
					<td><c:set var="evradOkundu"
							value="/resources/resim/tamam.png" /> <c:if
							test="${empty evradOkuma.kayitTarihi}">
							<c:set var="evradOkundu" value="/resources/resim/beyaz.png" />
						</c:if>
						<div class="divTus sag">
							<input type="image" id="btnEvradOkunduOkunmadi${evradOkuma.id}"
								src="<c:url value="${evradOkundu}" />" class="imgAktifPasif"
								onclick="evradOkunduOkunmadi(${evradOkuma.id})" />
						</div></td>
					<td><c:if test="${empty yetkisiz}">
							<div class="divTus" title="Okuma Kaydını Sil">
								<input type="image"
									src="<c:url value="/resources/resim/cop.png" />" class="doldur"
									onclick="evradOkumaSil(${evradOkuma.id})" />
							</div>
						</c:if></td>
					<td>
						<div class="divTus gizli sol" id="divEvradKaydet${evradOkuma.id}">
							<input type="image" id="btnEvradKaydet${evradOkuma.id}"
								src="<c:url value="/resources/resim/disket.png" />"
								class="doldur" onclick="evradOkumaKaydet(${evradOkuma.id})" />
						</div>
					</td>
				</tr>
				<c:set var="oncekiEvradAboneId" value="${evradOkuma.evradAbone.id}" />
			</c:forEach>
		</table>
	</div>

</body>
</html>