<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="sayfa" class="java.lang.Integer" scope="session" />
<jsp:useBean id="sayfaSayisi" class="java.lang.Integer" scope="session" />

<%
    	int baslangicSayfa = (int)(sayfa-10); 
		if (baslangicSayfa<2) baslangicSayfa=2;
		if (sayfaSayisi>1) {
			out.print("Sayfa :");
		    if (1 !=sayfa) out.print("<a onclick='sayfadakiKayitleriGetir(1) style='cursor:pointer'>1 </a>"); 
		    	else out.print("<b>1 </b>");
		    if (baslangicSayfa>2) out.print("....");
		    for (int i=baslangicSayfa;i<baslangicSayfa+19 && i<sayfaSayisi+1;i++){
		      if (i!=sayfa) out.print("<a onclick='sayfadakiKayitleriGetir("+i+") style='cursor:pointer'>"+i+"</a>"); 
		      	else out.print("<b>"+i+" </b>");
		    }
		    if (baslangicSayfa+19 < sayfaSayisi) out.print("....");
		    if (baslangicSayfa+18 < sayfaSayisi) {
		     if (sayfaSayisi!=sayfa) out.print("<a onclick='sayfadakiKayitleriGetir("+sayfaSayisi+") style='cursor:pointer'>"+sayfaSayisi+" </a>"); 
		    	else out.print("<b>"+sayfaSayisi+" /b>");
		    }
		}
    %>