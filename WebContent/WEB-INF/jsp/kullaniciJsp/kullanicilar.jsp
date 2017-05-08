
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
	<c:if test="${userInfo.selahiyet}">
		<div class="kutu">
			<form:form method="post" action="kullanicilistele"
				commandName="arananKullanici">
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
							value="Ara" class="buyukYazi tus" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</c:if>
	<c:if test="${!empty kullaniciListesi}">
		<div id="divKullaniciListesi" class="kutu ">
			<table>
				<tr>
					<th>Kullanıcı İsmi</th>
					<th>Şifre</th>
					<th>Telefon</th>
					<th>Mail</th>
					<c:if test="${userInfo.extSelahiyet}">
						<th>Slhyt</th>
					</c:if>
					<th>Psf</th>
				</tr>

				<c:forEach items="${kullaniciListesi}" var="kullanici"
					varStatus="no">
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>
					<c:if test="${kullanici.pasif}">
						<c:set var="sinif" value="${sinif} silik" />
					</c:if>

					<tr class="${sinif}" id="kullaniciSatir${kullanici.id}">

						<td><input id="kullaniciIsim${kullanici.id}"
							name="${kullanici.isim}" class="ortaTextBox"
							value="${kullanici.isim}"
							onkeyup="kullaniciDegisti(${kullanici.id})"
							onchange="kullaniciDegisti(${kullanici.id})" /></td>
						<td><c:set var="editlenemez" value="" /> <c:if
								test="${!userInfo.extSelahiyet && kullanici.extSelahiyet}">
								<c:set var="editlenemez" value="disabled" />
							</c:if> <input type="hidden" id="hdnSifre${kullanici.id}"
							value="${kullanici.sifre}"> <input type="password"
							id="sifre${kullanici.id}" class="ortaTextBox"
							value="${kullanici.sifre}"
							onkeyup="kullaniciDegisti(${kullanici.id})"
							onchange="kullaniciDegisti(${kullanici.id})" ${editlenemez} /></td>
						<td><input id="kullaniciTelefon${kullanici.id}"
							name="${kullanici.telefon}" class="ortaTextBox"
							value="${kullanici.telefon}"
							onkeyup="kullaniciDegisti(${kullanici.id})"
							onchange="kullaniciDegisti(${kullanici.id})" /></td>
						<td><input id="kullaniciMail${kullanici.id}"
							name="${kullanici.email}" class="textBox devam"
							value="${kullanici.email}"
							onkeyup="kullaniciDegisti(${kullanici.id})"
							onchange="kullaniciDegisti(${kullanici.id})" /></td>
						<c:if test="${userInfo.extSelahiyet}">
							<td><c:set var="selahiyet"
									value="/resources/resim/tamam.png" /> <c:if
									test="${!kullanici.selahiyet}">
									<c:set var="selahiyet" value="/resources/resim/beyaz.png" />
								</c:if>
								<div class="divTus sol">
									<input type="image" id="btnSelahiyet${kullanici.id}"
										src="<c:url value="${selahiyet}" />" class="imgAktifPasif"
										onclick="kullaniciSelahiyet(${kullanici.id})" />
								</div></td>
						</c:if>
						<td><c:set var="kullaniciAktifPasif"
								value="/resources/resim/tamam.png" /> <c:if
								test="${!kullanici.pasif}">
								<c:set var="kullaniciAktifPasif"
									value="/resources/resim/beyaz.png" />
							</c:if>
							<div class="divTus sol">
								<input type="image" id="btnKullaniciAktifPasif${kullanici.id}"
									src="<c:url value="${kullaniciAktifPasif}" />"
									class="imgAktifPasif"
									onclick="kullaniciAktifPasif(${kullanici.id})" />
							</div></td>
						<td>
							<div class="divTus sol"
								id="divKullaniciAbonelikler${kullanici.id}">
								<input type="image"
									src="<c:url value="/resources/resim/rahle.png" />"
									class="doldur"
									onclick="window.location.href='/murakib/abonelikler/${kullanici.id}'" />
							</div>
						</td>
						<td><c:if test="${userInfo.selahiyet}">
								<div class="divTus" title="Kullanıcıyı Sil">
									<input type="image"
										src="<c:url value="/resources/resim/cop.png" />"
										class="doldur" onclick="kullaniciSil(${kullanici.id})" />
								</div>
							</c:if></td>
						<td>
							<div class="divTus gizli sag"
								id="divKullaniciKaydet${kullanici.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur" onclick="kullaniciKaydet(${kullanici.id})" />
							</div>
							<div class="divTus gizli sag"
								id="divKullaniciVazgec${kullanici.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="kullaniciVazgec(${kullanici.id})" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</body>
</html>