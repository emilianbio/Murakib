
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
		<form:form method="post" action="yenikitababonekaydet"
			commandName="eklenenKitabAbone">
			<table>
				<tr>
					<th class="satirBaslik"><form:label path="kullanici.id"> Kullanıcı </form:label>
					</th>
					<td><form:select path="kullanici.id" id="slctKullanici">
							<form:options items="${kullaniciListesi}" itemValue="id"
								itemLabel="isim" />
						</form:select></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="gunlukSayfaSayisi"> Günlük Sayfa Sayısı </form:label>
					</th>
					<td><form:input path="gunlukSayfaSayisi" class="kucukTextBox" />
					</td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="baslamaTarihi"> Başlama Tarihi </form:label>
					</th>
					<td><form:input path="baslamaTarihi" class="tarih" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="aciklama"> Not </form:label>
					</th>
					<td><form:input path="aciklama" class="textBox" /></td>
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