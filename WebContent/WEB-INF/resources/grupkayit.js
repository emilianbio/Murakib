var jq = jQuery.noConflict();

jq(function() {
	jq("#divGrupKayit").draggable({handle:"#grupKayitBaslik"});
//	jq("#divAnlikMesaj").draggable({handle:"#mesajBaslik"});
//	jq("#divAnlikMesaj").resizable({ handles: 'n, e, s, w' });
});

function closeDialog(){
	jq("#divGrupKayit").hide("slow");
	jq("#divGrupKayitlar table").html('<tr><th>&nbsp;</th><th>Muhatap</th><th>Telefon</th><th>Konu</th><th>Detay</th><th>Yapılan</th><th>Sonuç</th></tr>');
//	jq("body").on("load","aralikliTazele(3000);");
}
function loadDialog(kayitId){
	jq.ajax({
	    type: "POST",
	    url: "/notal/grupkayit",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kayitId:kayitId},
	    success:function(gelen){
	    	if(gelen.length>1){
	    		grupKayitlariYaz(gelen);
	    		grupKayitlariGoster();
	    	} else alert("Bu Kayıtla İlişkili Başka Kayıt Yok...");
	    }
	});
	
}
function grupKayitlariGoster(){
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = jq("#divGrupKayit").height();
	var popupWidth = jq("#divGrupKayit").width();
	jq("#divGrupKayit").css({
		"position": "absolute",
		"position": "fixed",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
//	jq("body").on("load","");
	jq("#divGrupKayit").show("slow");
//	jq("#tblGrup").contextMenu({selector: 'tr',items: menuler()});
}
function grupKayitlariYaz(gelen){
	
	for (var i=0; i<gelen.length; i++){
		var kayit=gelen[i];
		var sinif="";
		if(kayit.arsive) sinif =' opacity:0.40;';
		if(kayit.ertele) sinif +=' color:green;';
		if(kayit.takipEt) sinif +=' color:blue;';
		if(kayit.bekliyor) sinif +=' color:red;';
		if(i%2==0) sinif +=' background-color:azure;';
		var eklenecek='<tr style=" ' + sinif+' "  id="'+kayit.id+'" class="'+kayit.id+'">';
		eklenecek +=' <td><input type="button" value="'+kayit.id+'" onclick="kaydidegistir('+kayit.id+')" /></td>';
		eklenecek +=' <td>'+kayit.muhatap+'</td>';
		eklenecek +=' <td>'+kayit.telefon+'</td>';
		eklenecek +=' <td>'+kayit.konu+'</td>';
		eklenecek +=' <td>'+kayit.detay+'</td>';
		eklenecek +=' <td>'+kayit.yapilan+'</td>';
		eklenecek +='<td>'+kayit.sonuc+'</td></tr>' ;
		jq("#divGrupKayitlar table").append(eklenecek);
	}
}		    

function kaydidegistir(kayitId){
	window.location='/notal/kayitdegistir/'+kayitId;
}