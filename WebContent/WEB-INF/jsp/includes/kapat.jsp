<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table style="width: 100%;">
	<c:set var="adres" value="/anasayfa" />
	<c:if test="${!empty oncekiSayfa}">
		<c:set var="adres" value="${oncekiSayfa }" />
	</c:if>
	<tr>
		<td style="text-align: right;"><a
			href="<c:url value="${adres}" />">Kapat</a></td>
	</tr>
</table>