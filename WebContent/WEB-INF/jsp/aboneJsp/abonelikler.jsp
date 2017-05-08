
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

	<c:if test="${!empty kitabAboneListesi}">

		<div class="kutu ">
			<table>
				<tr>
					<th>Kullanıcı İsmi</th>
					<th>Başlama Tarihi</th>
					<th>Gn.S.S.</th>
					<th>Not</th>
					<th>Hülasa</th>
					<th>Hss</th>
					<th>Psf</th>
				</tr>

				<c:forEach items="${kitabAboneListesi}" var="kitabAbone"
					varStatus="no">
					<c:remove var="yetkili" />
					<c:if
						test="${!userInfo.selahiyet && kitabAbone.kaydedenKullanici.id != userInfo.id}">
						<c:set var="yetkili" value="disabled" />
					</c:if>
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>
					<c:if test="${kitabAbone.pasif}">
						<c:set var="sinif" value="${sinif} silik" />
					</c:if>
					<fmt:formatDate pattern="dd.MM.yyyy"
						value="${kitabAbone.baslamaTarihi}" var="aboneBaslamaTarihi" />
					<tr class="${sinif}" id="kitabAboneSatir${kitabAbone.id}">
						<td><a href="/murakib/abonelikler/${kitabAbone.kullanici.id}">${kitabAbone.kullanici.isim}
						</a></td>
						<td><input type="hidden" value="${aboneBaslamaTarihi}"
							id="hdnKitabAboneBaslama${kitabAbone.id}" /> <input
							id="kitabAboneBaslama${kitabAbone.id}" class="tarih"
							value="${aboneBaslamaTarihi}"
							onkeyup="kitabAboneDegisti(${kitabAbone.id})"
							onchange="kitabAboneDegisti(${kitabAbone.id})" ${yetkili} /></td>
						<td><input type="hidden"
							value="${kitabAbone.gunlukSayfaSayisi}"
							id="hdnKitabAboneSayfaSayisi${kitabAbone.id}" /> <input
							id="kitabAboneSayfaSayisi${kitabAbone.id}"
							class="kucukTextBox sadeceSayi"
							value="${kitabAbone.gunlukSayfaSayisi}"
							onkeyup="kitabAboneDegisti(${kitabAbone.id})"
							onchange="kitabAboneDegisti(${kitabAbone.id})" ${yetkili} /></td>
						<td><input type="hidden" value="${kitabAbone.aciklama}"
							id="hdnKitabAboneAciklama${kitabAbone.id}" /> <input
							id="kitabAboneAciklama${kitabAbone.id}" class="textBox"
							value="${kitabAbone.aciklama}"
							onkeyup="kitabAboneDegisti(${kitabAbone.id})"
							onchange="kitabAboneDegisti(${kitabAbone.id})" /></td>
						<td><a href="/murakib/kitab/${kitabAbone.id}">
								${kitabAbone.sonuc}/${kitabAbone.gereken} </a></td>
						<td><c:if test="${empty yetkili}">
								<c:set var="hususi" value="/resources/resim/tamam.png" />
								<c:if test="${!kitabAbone.hususi}">
									<c:set var="hususi" value="/resources/resim/beyaz.png" />
								</c:if>
								<div class="divTus">
									<input type="image" id="btnKitabAboneHususi${kitabAbone.id}"
										src="<c:url value="${hususi}" />" class="imgAktifPasif"
										onclick="kitabAboneHususi(${kitabAbone.id})" />
								</div>
							</c:if></td>
						<td><c:if test="${empty yetkili}">
								<c:set var="kitabAboneAktifPasif"
									value="/resources/resim/tamam.png" />
								<c:if test="${!kitabAbone.pasif}">
									<c:set var="kitabAboneAktifPasif"
										value="/resources/resim/beyaz.png" />
								</c:if>
								<div class="divTus">
									<input type="image"
										id="btnKitabAboneAktifPasif${kitabAbone.id}"
										src="<c:url value="${kitabAboneAktifPasif}" />"
										class="imgAktifPasif"
										onclick="kitabAboneAktifPasif(${kitabAbone.id})" />
								</div>
							</c:if></td>
						<td>
							<div class="divTus">
								<input type="image"
									src="<c:url value="/resources/resim/cop.png" />" class="doldur"
									onclick="kitabAboneSil(${kitabAbone.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag"
								id="divKitabAboneVazgec${kitabAbone.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="kitabAboneVazgec(${kitabAbone.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag"
								id="divKitabAboneKaydet${kitabAbone.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur" onclick="kitabAboneKaydet(${kitabAbone.id})" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

	<c:if test="${!empty evradAboneListesi}">
		<div class="kutu ">

			<table>
				<tr>
					<th>Kullanıcı İsmi</th>
					<th>Başlama Tarihi</th>
					<th>Grub</th>
					<th>Evrad İsmi</th>
					<th>Okunacak</th>
					<th>Süre</th>
					<th>Not</th>
					<th>Hülasa</th>
					<th>Hss</th>
					<th>Psf</th>
					<th>Sbt</th>
				</tr>

				<c:forEach items="${evradAboneListesi}" var="evradAbone"
					varStatus="no">
					<c:remove var="yetkisiz" />
					<c:if
						test="${!userInfo.selahiyet && evradAbone.kaydedenKullanici.id != userInfo.id}">
						<c:set var="yetkisiz" value="disabled" />
					</c:if>
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>
					<c:if test="${evradAbone.pasif}">
						<c:set var="sinif" value="${sinif} silik" />
					</c:if>
					<fmt:formatDate pattern="dd.MM.yyyy"
						value="${evradAbone.baslamaTarihi}" var="aboneBaslamaTarihi" />
					<tr class="${sinif}" id="evradAboneSatir${evradAbone.id}">
						<td><a href="/murakib/abonelikler/${evradAbone.kullanici.id}">${evradAbone.kullanici.isim}</a>
						</td>
						<td><input type="hidden" value="${aboneBaslamaTarihi}"
							id="hdnEvradAboneBaslama${evradAbone.id}" /> <input
							id="evradAboneBaslama${evradAbone.id}" class="tarih"
							value="${aboneBaslamaTarihi}"
							onkeyup="evradAboneDegisti(${evradAbone.id})"
							onchange="evradAboneDegisti(${evradAbone.id})" ${yetkisiz} /></td>
						<td><c:if
								test="${userInfo.selahiyet || userInfo.extSelahiyet}">
								<a href="/murakib/grubabonelikler/${evradAbone.grub.id}">${evradAbone.grub.isim}</a>
							</c:if> <c:if test="${!userInfo.selahiyet && !userInfo.extSelahiyet}">
							${evradAbone.grub.isim}
						</c:if></td>
						<td id="tdEvradBolumleme${evradAbone.id}"><a
							href="/murakib/evrad/${evradAbone.id}">${evradAbone.evradKisim.evradBolumleme.isim}</a>
						</td>
						<td><input type="hidden" value="${evradAbone.evradKisim.id}"
							id="hdnEvradKisim${evradAbone.id}" /> <span
							id="spnEvradKisim${evradAbone.id}"> <c:if
									test="${evradAbone.evradKisim.evradBolumleme.parcaSayisi>1}">
									<span
										onclick="selectEvradKisimHazirla('spnEvradKisim${evradAbone.id}','evradKisim${evradAbone.id}'${evradAbone.id},${evradAbone.evradKisim.evradBolumleme.id},${evradAbone.evradKisim.id})">${evradAbone.evradKisim.isim}</span>
								</c:if>
						</span></td>
						<td><input type="hidden" value="${evradAbone.parcaSuresi}"
							id="hdnSure${evradAbone.id}" /> <input id="sure${evradAbone.id}"
							class="kucukTextBox sadeceSayi" value="${evradAbone.parcaSuresi}"
							onkeyup="evradAboneDegisti(${evradAbone.id})"
							onchange="evradAboneDegisti(${evradAbone.id})" /></td>
						<td><input type="hidden" value="${evradAbone.aciklama}"
							id="hdnEvradAboneAciklama${evradAbone.id}" /> <input
							id="evradAboneAciklama${evradAbone.id}" class="textBox"
							value="${evradAbone.aciklama}"
							onkeyup="evradAboneDegisti(${evradAbone.id})"
							onchange="evradAboneDegisti(${evradAbone.id})" /></td>
						<td><a href="/murakib/bosgunleridoldur/${evradAbone.id}">${evradAbone.sonuc}/${evradAbone.gereken}</a>
						</td>
						<td><c:if test="${empty yetkisiz}">
								<c:set var="hususi" value="/resources/resim/tamam.png" />
								<c:if test="${!evradAbone.hususi}">
									<c:set var="hususi" value="/resources/resim/beyaz.png" />
								</c:if>
								<div class="divTus">
									<input type="image" id="btnEvradAboneHususi${evradAbone.id}"
										src="<c:url value="${hususi}" />" class="imgAktifPasif"
										onclick="evradAboneHususi(${evradAbone.id})" />
								</div>
							</c:if></td>
						<td><c:if test="${empty yetkisiz}">
								<c:set var="evradAboneAktifPasif"
									value="/resources/resim/tamam.png" />
								<c:if test="${!evradAbone.pasif}">
									<c:set var="evradAboneAktifPasif"
										value="/resources/resim/beyaz.png" />
								</c:if>
								<div class="divTus">
									<input type="image"
										id="btnEvradAboneAktifPasif${evradAbone.id}"
										src="<c:url value="${evradAboneAktifPasif}" />"
										class="imgAktifPasif"
										onclick="evradAboneAktifPasif(${evradAbone.id})" />
								</div>
							</c:if></td>
						<td><c:set var="evradAboneSabit"
								value="/resources/resim/beyaz.png" /> <c:if
								test="${!empty evradAbone.grub}">
								<c:if test="${evradAbone.grub.sabit}">
									<c:set var="evradAboneSabit" value="/resources/resim/tamam.png" />
								</c:if>
								<c:remove var="aboneId" />
							</c:if> <c:if test="${empty evradAbone.grub}">
								<c:if test="${evradAbone.sabit}">
									<c:set var="evradAboneSabit" value="/resources/resim/tamam.png" />
								</c:if>
								<c:if test="${empty yetkisiz}">
									<c:set var="aboneId" value="${evradAbone.id}" />
								</c:if>
							</c:if>
							<div class="divTus">
								<input type="image" id="btnEvradAboneSabit${evradAbone.id}"
									src="<c:url value="${evradAboneSabit}" />"
									class="imgAktifPasif" onclick="evradAboneSabit(${aboneId})" />
							</div></td>
						<td><c:if test="${empty yetkisiz}">
								<div class="divTus">
									<input type="image"
										src="<c:url value="/resources/resim/cop.png" />"
										class="doldur" onclick="evradAboneSil(${evradAbone.id})" />
								</div>
							</c:if></td>
						<td>
							<div class="divTus gizli sag"
								id="divEvradAboneVazgec${evradAbone.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="evradAboneVazgec(${evradAbone.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli sag"
								id="divEvradAboneKaydet${evradAbone.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur" onclick="evradAboneKaydet(${evradAbone.id})" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</body>
</html>