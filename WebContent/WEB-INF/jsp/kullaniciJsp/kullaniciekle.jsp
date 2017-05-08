
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
	<div id="divMenu" class="kutu">
		<form:form method="post" action="yenikullanicikaydet"
			commandName="eklenenKullanici">
			<form:hidden path="id" />
			<table>
				<tr>
					<th class="satirBaslik"><form:label path="isim"> Kullanıcı İsmi </form:label>
					</th>
					<td><form:input class="textBox" path="isim" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="telefon"> Telefon No </form:label>
					</th>
					<td><form:input class="textBox" path="telefon" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="email"> Mail Adresi </form:label>
					</th>
					<td><form:input class="textBox" path="email" /></td>
				</tr>
				<tr>
					<td>
						<!-- 
				<form:checkbox path="pasif"/>Pasif</td>
				 -->
					<td style="text-align: right;"><input type="submit"
						value="Ekle" class="buyukYazi tus" /></td>
				</tr>
			</table>
			<span class="kirmizi">${sonuc}</span>
		</form:form>
	</div>
	<c:if test="${!empty kullanici}">
		<div class="kutu ">
			<table>
				<tr>
					<th>Kullanıcı İsmi</th>
					<th>Telefon</th>
					<th>Mail</th>
					<th>Pasif</th>
				</tr>
				<c:remove var="sinif" />
				<c:if test="${kullanici.pasif}">
					<c:set var="sinif" value="${sinif} silik" />
				</c:if>
				<tr class="${sinif}" id="kullaniciSatir${kullanici.id}">
					<td><input id="kullaniciIsim${kullanici.id}"
						name="${kullanici.isim}" class="textBox" value="${kullanici.isim}"
						onkeyup="kullaniciDegisti(${kullanici.id})"
						onchange="kullaniciDegisti(${kullanici.id})" /></td>
					<td><input id="kullaniciTelefon${kullanici.id}"
						name="${kullanici.telefon}" class="textBox"
						value="${kullanici.telefon}"
						onkeyup="kullaniciDegisti(${kullanici.id})"
						onchange="kullaniciDegisti(${kullanici.id})" /></td>
					<td><input id="kullaniciMail${kullanici.id}"
						name="${kullanici.email}" class="textBox"
						value="${kullanici.email}"
						onkeyup="kullaniciDegisti(${kullanici.id})"
						onchange="kullaniciDegisti(${kullanici.id})" /></td>
					<td><c:set var="kullaniciAktifPasif"
							value="/resources/resim/tamam.png" /> <c:if
							test="${!kullanici.pasif}">
							<c:set var="kullaniciAktifPasif"
								value="/resources/resim/beyaz.png" />
						</c:if>
						<div class="divTus">
							<input type="image" id="btnKullaniciAktifPasif${kullanici.id}"
								src="<c:url value="${kullaniciAktifPasif}" />"
								class="imgAktifPasif"
								onclick="kullaniciAktifPasif(${kullanici.id})" />
						</div></td>
					<td>
						<div class="divTus gizli sag"
							id="divKullaniciVazgec${kullanici.id}">
							<input type="image"
								src="<c:url value="/resources/resim/carpi.png" />"
								class="doldur" onclick="kullaniciVazgec(${kullanici.id})" />
						</div>
					</td>
					<td>
						<div class="divTus gizli sag"
							id="divKullaniciKaydet${kullanici.id}">
							<input type="image"
								src="<c:url value="/resources/resim/disket.png" />"
								class="doldur" onclick="kullaniciKaydet(${kullanici.id})" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
</body>
</html>