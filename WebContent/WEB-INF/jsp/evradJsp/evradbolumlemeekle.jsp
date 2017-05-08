
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
		<form:form method="post" action="yenibolumlemekaydet"
			commandName="eklenenEvradBolumleme">
			<table>
				<tr>
					<th class="satirBaslik"><form:label path="isim"> Evrad İsmi </form:label>
					</th>
					<td><form:input class="textBox" path="isim" /></td>
				</tr>
				<tr>
					<th class="satirBaslik"><form:label path="parcaSayisi"> Bölüm Sayısı </form:label>
					</th>
					<td><form:input class="textBox " path="parcaSayisi" /></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;"><input
						type="submit" value="Evrad Ekle" class="buyukYazi tus" /></td>
				</tr>
			</table>
			<span class="kirmizi">${sonuc}</span>
		</form:form>
	</div>

</body>
</html>