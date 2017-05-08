
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
	<div id="divKitabOkuma" class="kutu ">
		Kullanıcı : ${kullanici.isim}
		<table>
			<tr>
				<th>Tarih</th>
				<th>Kitab İsmi</th>
				<th>Başlanan Sayfa</th>
				<th>Gelinen Sayfa</th>
			</tr>
			<c:forEach items="${kitabOkumaListesi}" var="kitabOkuma"
				varStatus="no">
				<c:if test="${kitabOkuma.kitabAbone.id != sonKitabAboneId}">
					<tr class="bolumBaslik">
						<td colspan="2">${kitabOkuma.kitabAbone.aciklama}</td>
						<td><span
							id="spnOkunan_OkunmasiGerekenKitab${kitabOkuma.kitabAbone.id}">${kitabOkuma.ilkSayfa}/${kitabOkuma.sonSayfa}</span></td>
					</tr>
				</c:if>
				<c:if test="${kitabOkuma.kitabAbone.id == sonKitabAboneId}">
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>
					<c:if
						test="${kitabOkuma.sonSayfa-kitabOkuma.ilkSayfa >= kitabOkuma.kitabAbone.gunlukSayfaSayisi}">
						<c:set var="sinif" value="${sinif} silik" />
					</c:if>

					<tr class="${sinif}" id="trKitabOkuma${kitabOkuma.id}">
						<td><fmt:formatDate pattern="dd.MM.yyyy"
								value="${kitabOkuma.tarih}" /></td>
						<td><input id="kitab${kitabOkuma.id}"
							name="${kitabOkuma.kitab}" class="textBox"
							value="${kitabOkuma.kitab}"
							onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
							onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
						<td class="ortala"><input id="kitabIlkSayfa${kitabOkuma.id}"
							name="${kitabOkuma.ilkSayfa}" class="kucukTextBox sadeceSayi"
							value="${kitabOkuma.ilkSayfa}"
							onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
							onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
						<td><input id="kitabSonSayfa${kitabOkuma.id}"
							name="${kitabOkuma.sonSayfa}" class="kucukTextBox sadeceSayi"
							value="${kitabOkuma.sonSayfa}"
							onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
							onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
						<td><c:if test="${empty yetkisiz}">
								<div class="divTus" title="Okuma Kaydını Sil">
									<input type="image"
										src="<c:url value="/resources/resim/cop.png" />"
										class="doldur" onclick="kitabOkumaSil(${kitabOkuma.id})" />
								</div>
							</c:if></td>
						<td>
							<div class="divTus gizli sag"
								id="divKitabOkumaVazgec${kitabOkuma.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="kitabOkumaVazgec(${kitabOkuma.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag"
								id="divKitabOkumaKaydet${kitabOkuma.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur"
									onclick="kitabOkumaKaydet(${kitabOkuma.id},${kitabOkuma.kitabAbone.id})" />
							</div>
						</td>
					</tr>
				</c:if>
				<c:set var="sonKitabAboneId" value="${kitabOkuma.kitabAbone.id}" />
			</c:forEach>
		</table>
	</div>
</body>
</html>