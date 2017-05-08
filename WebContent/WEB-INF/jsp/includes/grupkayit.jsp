<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="araclar.Genel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="divGrupKayit">
	<table style="width: 100%;">
		<tr id="grupKayitBaslik" class="yuvarla">
			<td>
				<div id="divDialogKapat"
					style="margin-right: 0em; float: left; cursor: pointer;"
					title="Kapat" onclick="closeDialog(0)">
					<img alt="x"
						src="<c:url value="/resources/resim/carpi_silik.png"/>"
						onmouseover="this.src='/uygulama/resources/resim/carpi.png'"
						onmouseout="this.src='/uygulama/resources/resim/carpi_silik.png'" />
				</div>
		</tr>
	</table>
	<div id="divGrupKayitlar" class="kutu anaKutu tblGrup">
		<table id="tblGrup" class="ikiYanaYasli contextMenulu">
			<tr>
				<th>&nbsp;</th>
				<th>Muhatap</th>
				<th>Telefon</th>
				<th>Konu</th>
				<th>Detay</th>
				<th>Sonuc</th>
				<th>Yapılan</th>
			</tr>
		</table>
	</div>
</div>