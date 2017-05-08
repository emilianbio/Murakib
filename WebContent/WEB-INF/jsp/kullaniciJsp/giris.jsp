<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.sendika" /></title>
<link href="<c:url value="/resources/resim/simge.ico" />"
	rel="shortcut icon" type="image/vnd.microsoft.icon" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/genel.css" />" />
</head>
<body>
	<br>
	<div id="divLogin" class="kutu">
		<br>
		<form:form method="post" action="kullanicionay"
			commandName="kullanici">
			<table style="margin: 0px auto">
				<tr>
					<td><form:label path="isim" class="buyukYazi">Kullanıcı İsmi</form:label></td>
					<td><form:input path="isim" class="girisTextBox" /></td>
				</tr>
				<tr>
					<td><form:label path="sifre" class="buyukYazi">Şifre</form:label></td>
					<td><form:password path="sifre" class="girisTextBox " /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Giriş" class="tamEkranTus" /></td>
				</tr>
			</table>
		</form:form>
		<br>
	</div>


</body>
</html>