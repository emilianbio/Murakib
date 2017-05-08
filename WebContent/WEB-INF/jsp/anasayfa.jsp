
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<c:url value="/resources/resim/rahle.ico" />"
	rel="shortcut icon" type="image/vnd.microsoft.icon" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/includes/ustbaslik.jsp" />
	<c:if test="${!empty kitabOkumaListesiGun }">
		<div class="kutu">
			<table>
				<tr>
					<th>Tarih</th>
					<th></th>
					<th>Kitab İsmi</th>
					<th>Başlanan</th>
					<th>Gelinen</th>
				</tr>
				<c:forEach items="${kitabOkumaListesiGun}" var="kitabOkuma"
					varStatus="no">
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
						<td><a href="/murakib/kitab/${kitabOkuma.kitabAbone.id}">${kitabOkuma.kitabAbone.aciklama}</a>
						</td>
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
						<td class="ortala"><input id="kitabSonSayfa${kitabOkuma.id}"
							name="${kitabOkuma.sonSayfa}" class="kucukTextBox sadeceSayi"
							value="${kitabOkuma.sonSayfa}"
							onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
							onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
						<td>
							<div class="divTus gizli"
								id="divKitabOkumaVazgec${kitabOkuma.id}">
								<input type="image"
									src="<c:url value="/resources/resim/carpi.png" />"
									class="doldur" onclick="kitabOkumaVazgec(${kitabOkuma.id})" />
							</div>
						</td>
						<td>
							<div class="divTus gizli"
								id="divKitabOkumaKaydet${kitabOkuma.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur"
									onclick="kitabOkumaKaydet(${kitabOkuma.id},${kitabOkuma.kitabAbone.id})" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
	<c:if test="${!empty evradOkumaListesiGun}">
		<div class="kutu">
			<table>
				<tr>
					<th>Tarih</th>
					<th>Evrad</th>
					<th colspan="2">Not</th>
				</tr>

				<c:forEach items="${evradOkumaListesiGun}" var="evradOkuma"
					varStatus="no">
					<c:remove var="sinif" />
					<c:if test="${no.count % 2 == 0}">
						<c:set var="sinif" value="ikinciSatir" />
					</c:if>
					<c:if test="${!empty evradOkuma.kayitTarihi}">
						<c:set var="sinif" value="${sinif} silik" />
					</c:if>
					<c:if test="${evradOkuma.evradAbone.id!=oncekiEvradAboneId}">
						<!-- 
					<tr class="evradBolumlemeBaslik"><td colspan="4"><c:if test="${!empty evradOkuma.evradAbone.aciklama}">${evradOkuma.evradAbone.aciklama} </c:if>(${evradOkuma.evradKisim.evradBolumleme.isim})</td> </tr>
					 -->
					</c:if>
					<tr class="${sinif}" id="evradSatir${evradOkuma.id}">
						<td><fmt:formatDate pattern="dd.MM.yyyy"
								value="${evradOkuma.tarih}" /></td>
						<td><a href="/murakib/evrad/${evradOkuma.evradAbone.id}">
								${evradOkuma.evradAbone.aciklama} <c:if
									test="${!empty evradOkuma.evradKisim.isim}">(${evradOkuma.evradKisim.isim})</c:if>
								${evradOkuma.evradKisim.sayfaAraligi}
						</a></td>
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
							<div class="divTus sol">
								<input type="image" id="btnEvradOkunduOkunmadi${evradOkuma.id}"
									src="<c:url value="${evradOkundu}" />" class="imgAktifPasif"
									onclick="evradOkunduOkunmadi(${evradOkuma.id})" />
							</div></td>
						<td>
							<div class="divTus gizli sag" id="divEvradKaydet${evradOkuma.id}">
								<input type="image"
									src="<c:url value="/resources/resim/disket.png" />"
									class="doldur" onclick="evradOkumaKaydet(${evradOkuma.id})" />
							</div>
						</td>
					</tr>
					<c:set var="oncekiEvradAboneId" value="${evradOkuma.evradAbone.id}" />
				</c:forEach>
			</table>
			<div class="divTus">
				<input type="image" id="btnGecmisGunler"
					src="<c:url value="/resources/resim/asagi.png" />"
					class="imgAktifPasif" onclick="gecmisGunler()" />
			</div>
		</div>
	</c:if>
	<div id="divGecmisGunler">
		<c:if test="${!empty kitabOkumaListesi}">
			<div id="divKitabOkuma" class="kutu ">
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
								<td colspan="2"><a
									href="/murakib/kitab/${kitabOkuma.kitabAbone.id}">${kitabOkuma.kitabAbone.aciklama}</a></td>
								<td><a href="/murakib/kitab/${kitabOkuma.kitabAbone.id}"><span
										id="spnOkunan_OkunmasiGerekenKitab${kitabOkuma.kitabAbone.id}">${kitabOkuma.ilkSayfa}/${kitabOkuma.sonSayfa}</span></a></td>
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
								<td class="ortala"><input
									id="kitabIlkSayfa${kitabOkuma.id}"
									name="${kitabOkuma.ilkSayfa}" class="kucukTextBox sadeceSayi"
									value="${kitabOkuma.ilkSayfa}"
									onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
									onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
								<td><input id="kitabSonSayfa${kitabOkuma.id}"
									name="${kitabOkuma.sonSayfa}" class="kucukTextBox sadeceSayi"
									value="${kitabOkuma.sonSayfa}"
									onkeyup="kitabOkumaDegisti(${kitabOkuma.id})"
									onchange="kitabOkumaDegisti(${kitabOkuma.id})" /></td>
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
		&nbsp;
		</c:if>
		<c:if test="${!empty evradOkumaListesi}">
			<div id="divEvradOkuma" class="kutu">
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
								<td colspan="4"><a
									href="/murakib/evrad/${evradOkuma.evradAbone.id}"> <c:if
											test="${!empty evradOkuma.evradAbone.aciklama}">${evradOkuma.evradAbone.aciklama} </c:if>
										(${evradOkuma.evradKisim.evradBolumleme.isim})
								</a></td>
							</tr>
						</c:if>
						<tr class="${sinif}" id="evradSatir${evradOkuma.id}">
							<td><fmt:formatDate pattern="dd.MM.yyyy"
									value="${evradOkuma.tarih}" /></td>
							<td>${evradOkuma.evradKisim.isim}
								${evradOkuma.evradKisim.sayfaAraligi}</td>
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
								<div class="divTus sol">
									<input type="image" id="btnEvradOkunduOkunmadi${evradOkuma.id}"
										src="<c:url value="${evradOkundu}" />" class="imgAktifPasif"
										onclick="evradOkunduOkunmadi(${evradOkuma.id})" />
								</div></td>
							<td>
								<div class="divTus gizli sag"
									id="divEvradKaydet${evradOkuma.id}">
									<input type="image"
										src="<c:url value="/resources/resim/disket.png" />"
										class="doldur" onclick="evradOkumaKaydet(${evradOkuma.id})" />
								</div>
							</td>
						</tr>
						<c:set var="oncekiEvradAboneId"
							value="${evradOkuma.evradAbone.id}" />
					</c:forEach>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>