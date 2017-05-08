
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
		<form:form method="post" action="yenigrubkaydet"
			commandName="eklenenGrub">
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
					<th class="satirBaslik"><form:label path="bitisTarihi"> Bitiş Tarihi </form:label>
					</th>
					<td><form:input class="textBox tarih" path="bitisTarihi" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="parcaSuresi"> Parça Süresi (Gün)</form:label>
					</th>
					<td><form:input class="kucukTextBox sadeceSayi"
							path="parcaSuresi" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="evradBolumleme.id"> Evrad </form:label>
					</th>
					<td><form:select path="evradBolumleme.id">
							<form:options items="${evradBolumlemeListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="sabit"> Sabit </form:label>
					</th>
					<td><form:checkbox path="sabit" id="sabit" class="ortaYazi" />
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;"><input
						type="submit" value="Grub Ekle" class="buyukYazi tus" /></td>
				</tr>
			</table>
			<span class="kirmizi">${sonuc}</span>
		</form:form>
	</div>
	<c:if test="${!empty grub}">
		<div class="kutu ">
			<table>
				<tr>
					<th>Başlama Tarihi</th>
					<th>Evrad</th>
					<th>Grub İsmi</th>
					<th></th>
				</tr>
				<tr>
					<td><fmt:formatDate pattern="dd.MM.yyyy"
							value="${grub.baslamaTarihi}" /></td>
					<td>${grub.evradBolumleme.isim}</td>
					<td><input type="hidden" id="hdnGrubIsim${grub.id}"
						value="${grub.isim }" /> <input id="grubIsim${grub.id}"
						class="ortaTextBox" value="${grub.isim}"
						onkeyup="grubDegisti(${grub.id})"
						onchange="grubDegisti(${grub.id})" /></td>
					<td>
						<div class="divTus gizli sag" id="divGrubKaydet${grub.id}">
							<input type="image"
								src="<c:url value="/resources/resim/disket.png" />"
								class="doldur" onclick="grubKaydet(${grub.id})" />
						</div>
						<div class="divTus gizli sag" id="divGrubVazgec${grub.id}">
							<input type="image"
								src="<c:url value="/resources/resim/carpi.png" />"
								class="doldur" onclick="grubVazgec(${grub.id})" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
</body>
</html>