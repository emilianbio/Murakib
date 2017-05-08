
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
		<c:remove var="disable" />
		<c:if test="${!empty eklenenEvradAbone.grub}">
			<c:set var="disable" value="true" />
		</c:if>
		<form:form method="post" action="yenievradabonekaydet"
			commandName="eklenenEvradAbone">
			<table>
				<tr>
					<th class="satirBaslik"><form:label path="kullanici.id"> Kullanıcı </form:label>
					</th>
					<td><form:select path="kullanici.id" id="slctKullanici">
							<form:options items="${kullaniciListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<c:if test="${userInfo.selahiyet }">
					<tr>
						<th class="satirBaslik"><form:label path="grub.id"> Grub </form:label>
						</th>
						<td><form:select path="grub.id" id="slctGrub"
								onchange="grubSecildi(this.value)">
								<form:options items="${grubListesi}" itemValue="id"
									itemLabel="isim" />
							</form:select></td>
					</tr>
				</c:if>
				<tr>
					<th class="satirBaslik"><form:label
							path="grub.evradBolumleme.id"> Evrad </form:label></th>
					<td><input type="hidden" id="grubBolumlemeId"
						value="${eklenenEvradAbone.grub.evradBolumleme.id}" /> <form:select
							path="grub.evradBolumleme.id" id="slctBolumleme"
							onchange="evradSecildi(this.value)">
							<form:options items="${bolumlemeListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="evradKisim.id"> Okunacak Kısım </form:label>
					</th>
					<td><form:select path="evradKisim.id" id="slctKisim">
							<form:options items="${kisimListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="baslamaTarihi"> Başlama Tarihi </form:label>
					</th>
					<td><form:input path="baslamaTarihi" class="tarih"
							id="baslamaTarihi" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="parcaSuresi"> Parça Süresi </form:label>
					</th>
					<td><form:input path="parcaSuresi" id="parcaSuresi"
							class="kucukTextBox sadeceSayi" disabled="${disable}" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="aciklama"> Not </form:label>
					</th>
					<td><form:input path="aciklama" class="textBox" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="sabit"> Sabit </form:label>
					</th>
					<td><form:checkbox path="sabit" id="sabit" class="ortaYazi"
							disabled="${disable}" /></td>
				</tr>
				<tr>
					<td style="text-align: right;" colspan="2"><input
						type="submit" value="Ekle" class="buyukYazi tus" /></td>
				</tr>
			</table>
			<span class="kirmizi">${sonuc}</span>
		</form:form>
	</div>
	<c:if test="${!empty evradAbone}">
	</c:if>
</body>
</html>