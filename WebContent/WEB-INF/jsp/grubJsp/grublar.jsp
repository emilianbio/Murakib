
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.sendika" /></title>
<link href="<c:url value="/resources/resim/simge.ico" />"
	rel="shortcut icon" type="image/vnd.microsoft.icon" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/includes/ustbaslik.jsp" />
	<div class="kutu">
		<form:form method="post" action="grublistele" commandName="arananGrub">
			<form:hidden path="grub.id" />
			<table>
				<tr>
					<th class="satirBaslik"><form:label path="isim"> Grub İsmi </form:label>
					</th>
					<td><form:input class="textBox" path="isim" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="baslamaTarihi"> Başlama Tarihi </form:label>
					</th>
					<td><form:input class="textBox tarih" path="baslamaTarihi" />
					</td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="evradBolumleme"> Evrad </form:label>
					</th>
					<td><form:select path="evradBolumleme.id">
							<form:option value="0">Hepsi</form:option>
							<form:options items="${evradBolumlemeListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;"><input
						type="submit" value="Ara" class="buyukYazi tus" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	<c:if test="${!empty grubListesi}">
		<div class="kutu ">
			<table>
				<tr>
					<th>Başlama Tarihi</th>
					<th>Bitiş Tarihi</th>
					<th>Süre</th>
					<th>Evrad</th>
					<th>Grub İsmi</th>
					<th>Sbt</th>
					<th></th>
				</tr>

				<c:forEach items="${grubListesi}" var="grub" varStatus="no">
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>

					<tr class="${sinif}">
						<td><fmt:formatDate pattern="dd.MM.yyyy"
								value="${grub.baslamaTarihi}" var="baslamaTarihi" /> <input
							type="hidden" id="hdnBaslama${grub.id}" value="${baslamaTarihi}" />
							<input id="baslama${grub.id}" class="tarih"
							value="${baslamaTarihi}" onkeyup="grubDegisti(${grub.id})"
							onchange="grubDegisti(${grub.id})" /></td>
						<td><fmt:formatDate pattern="dd.MM.yyyy"
								value="${grub.bitisTarihi}" var="bitisTarihi" /> <input
							type="hidden" id="hdnBitis${grub.id}" value="${bitisTarihi}" />
							<input id="bitis${grub.id}" class="tarih" value="${bitisTarihi}"
							onkeyup="grubDegisti(${grub.id})"
							onchange="grubDegisti(${grub.id})" /></td>
						<td><input type="hidden" id="hdnSure${grub.id}"
							value="${grub.parcaSuresi }" /> <input id="sure${grub.id}"
							class="kucukTextBox sadeceSayi" value="${grub.parcaSuresi}"
							onkeyup="grubDegisti(${grub.id})"
							onchange="grubDegisti(${grub.id})" /></td>
						<td>${grub.evradBolumleme.isim}</td>
						<td><input type="hidden" id="hdnGrubIsim${grub.id}"
							value="${grub.isim }" /> <input id="grubIsim${grub.id}"
							class="textBox" value="${grub.isim}"
							onkeyup="grubDegisti(${grub.id})"
							onchange="grubDegisti(${grub.id})" /></td>
						<td><c:set var="grubSabit" value="/resources/resim/beyaz.png" />
							<c:if test="${grub.sabit}">
								<c:set var="grubSabit" value="/resources/resim/tamam.png" />
							</c:if>
							<div class="divTus">
								<input type="image" id="btnSabit${grub.id}"
									src="<c:url value="${grubSabit}" />" class="imgAktifPasif"
									onclick="grubSabit(${grub.id})" />
							</div></td>
						<td>
							<div class="divTus sol">
								<input type="image"
									src="<c:url value="/resources/resim/rahle.png" />"
									class="doldur"
									onclick="window.location.href='/murakib/grubabonelikler/${grub.id}'" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag" id="divGrubVazgec${grub.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="grubVazgec(${grub.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag" id="divGrubKaydet${grub.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur" onclick="grubKaydet(${grub.id})" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</body>
</html>