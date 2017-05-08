var jq = jQuery.noConflict();
var musbet="/murakib/resources/resim/tamam.png";
var menfi="/murakib/resources/resim/beyaz.png";

jq(function(){
  	if(mobilMi()) {
  		jq("body").css("font-size","2em");
//  		jq("input.checkbox").css("width","1em");
//  		jq("input.checkbox").css("height","1em");  		
  	}	
  	genislikAyarla();
  	jq(window).resize(function() {
  		genislikAyarla();
  	});
  	jq(".tarih").datetimeEntry({datetimeFormat: 'D.O.Y'});
  	jq(".sadeceSayi").keyup(function(){
  		if (this.value.match(/[^0-9]/g)) this.value = this.value.replace(/[^0-9]/g,'');
  	});
});

function mobilMi(){
	return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent); 
}
function genislikAyarla(){
  	var pencereGenislik=jq(window).width() / parseFloat(jq("body").css("font-size"));
	var menuGenislik = parseInt(jq("#nvMenu").css("width")) / parseFloat(jq("body").css("font-size"));
	var deger = pencereGenislik-menuGenislik -0.2;
	if(deger<1) deger=1;
	jq("#slctDonemler").css("max-width",deger+"em");
}
function xmlHttp(){
  var xmlhttp;
        if (window.XMLHttpRequest) {    // IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();            
         } else {    // IE6, IE5
             xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
         }
        
  return xmlhttp;        
}

function sifreDegistir() {
	if(jq("#txtYeniSifre1").val()=='0'){jq('#sonuc').html("Şifreler 0 Olamaz...");} else{
		if (jq("#txtYeniSifre1").val()==jq("#txtYeniSifre").val()){
		jq.ajax({
		    type: "POST",
		    url: "/uygulama/sifrekaydet",
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		    dataType: "JSON",
		    data: jq("#frmSifreDegistir").serialize(),
		    success: function(gelen) {
		    	jq(location).attr('href','/uygulama/anasayfa');
		    	alert(gelen.sonuc);
		    	//jq("#sonuc").replaceWith('<span id="sonuc">'+gelen+'</span>');
		    }/*,
		    error: function (xhr, textStatus, errorThrown) {
		    	jq(location).attr('href','/uygulama/sifredegistir');
		        //jq("#sonuc").html(xhr.responseText);
		    }

			jq.post("/uygulama/sifrekaydet",{sifre:jq("#txtGecerliSifre").val(),yeniSifre:jq("#txtYeniSifre1").val()},function(gelen){
						alert("veri gitti");
						alert(gelen);
						jq("#sonuc").replaceWith('<span id="sonuc">'+gelen+'</span>');*/
		});
	} else jq('#sonuc').html("Yeni Şifreler Uyumsuz...");
	}
}
function sifreSifirla(yetkiliId) {
		jq.ajax({
		    type: "POST",
		    url: "/uygulama/sifresifirla",
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		    dataType: "JSON",
		    data: {yetkiliId:yetkiliId},
		    success: function(gelen) {
		    	alert(gelen.sonuc);
		    }/*,
		    error: function (xhr, textStatus, errorThrown) {		    	
		    	alert("Hata: "+xhr.responseText);
		    }*/
		});
}

function kitabOkumaDegisti(idsi){
	elementKitab = jq("#kitab"+idsi);
	elementIlkSayfa = jq("#kitabIlkSayfa"+idsi);
	elementSonSayfa = jq("#kitabSonSayfa"+idsi);
	
	if((elementKitab.val()!=elementKitab.attr("name")) || (elementIlkSayfa.val()!=elementIlkSayfa.attr("name"))||(elementSonSayfa.val()!=elementSonSayfa.attr("name")))
		tuslariAc(idsi, "KitabOkuma"); else tuslariKapat(idsi, "KitabOkuma");
}
function evradOkumaDegisti(idsi){
	elementAciklama = jq("#evradAciklama"+idsi);
	
	if(elementAciklama.val()!=elementAciklama.attr("name")){
			jq("#divEvradKaydet"+idsi).show("slow");
		} else {
			evradTuslariniKapat(idsi);
		}
}
function grubDegisti(idsi){
	var hdnGrubIsim = jq("#hdnGrubIsim"+idsi);
	var grubIsim=jq("#grubIsim"+idsi);
	var hdnBaslama = jq("#hdnBaslama"+idsi);
	var baslama=jq("#baslama"+idsi);
	var hdnBitis = jq("#hdnBitis"+idsi);
	var bitis=jq("#bitis"+idsi);
	var hdnSure = jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	if((hdnGrubIsim.val()!=grubIsim.val())||(hdnBaslama.val()!=baslama.val())||(hdnBitis.val()!=bitis.val())||(hdnSure.val()!=sure.val())) tuslariAc(idsi,"Grub"); else tuslariKapat(idsi,"Grub");
}
function kitabAboneDegisti(idsi){
	var hdnAboneBaslama = jq("#hdnKitabAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#kitabAboneBaslama"+idsi);
	var hdnAboneSayfaSayisi=jq("#hdnKitabAboneSayfaSayisi"+idsi);
	var inptAboneSayfaSayisi=jq("#kitabAboneSayfaSayisi"+idsi);
	var hdnKitabAboneAciklama=jq("#hdnKitabAboneAciklama"+idsi);
	var inptKitabAboneAciklama=jq("#kitabAboneAciklama"+idsi);
	if((hdnAboneBaslama.val()!=inptAboneBaslama.val())||(hdnAboneSayfaSayisi.val()!=inptAboneSayfaSayisi.val())||(hdnKitabAboneAciklama.val()!=inptKitabAboneAciklama.val()))
			tuslariAc(idsi,"KitabAbone"); else tuslariKapat(idsi,"KitabAbone");
}
function evradAboneDegisti(idsi){	
	var hdnAboneBaslama = jq("#hdnEvradAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#evradAboneBaslama"+idsi);
	var hdnSure=jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	var hdnAciklama= jq("#hdnEvradAboneAciklama"+idsi);
	var aciklama = jq("#evradAboneAciklama"+idsi);
	var evradKismiDegisti=false;
	
	if((hdnSure.val()!=sure.val())||(hdnAboneBaslama.val()!=inptAboneBaslama.val())||evradKismiDegisti||(hdnAciklama.val()!=aciklama.val()))
			tuslariAc(idsi, "EvradAbone"); else tuslariKapat(idsi, "EvradAbone");
}

function kullaniciDegisti(idsi){
	var elementIsim = jq("#kullaniciIsim"+idsi);
	var elementTelefon = jq("#kullaniciTelefon"+idsi);
	var elementMail = jq("#kullaniciMail"+idsi);
	var sifre=jq("#sifre"+idsi);
	var hdnSifre=jq("#hdnSifre"+idsi);
	if((hdnSifre.val()!=sifre.val())||(elementIsim.val()!=elementIsim.attr("name"))||(elementTelefon.val()!=elementTelefon.attr("name"))||(elementMail.val()!=elementMail.attr("name")))
		tuslariAc(idsi, "Kullanici"); else tuslariKapat(idsi, "Kullanici");
	
}

function evradKisimDegisti(idsi){
	var isim = jq("#kisimIsim"+idsi);
	var hdnIsim = jq("#hdnKisimIsim"+idsi);
	var siraNo = jq("#siraNo"+idsi);
	var hdnSiraNo = jq("#hdnSiraNo"+idsi);
	var sayfa = jq("#sayfa"+idsi);
	var hdnSayfa = jq("#hdnSayfa"+idsi);
	
	if((isim.val()!=hdnIsim.val())||(siraNo.val()!=hdnSiraNo.val())||(sayfa.val()!=hdnSayfa.val()))
		tuslariAc(idsi, "Kisim");else tuslariKapat(idsi, "Kisim");
	
}
function kisimVazgec(idsi){
	var isim = jq("#kisimIsim"+idsi);
	var hdnIsim = jq("#hdnKisimIsim"+idsi);
	var siraNo = jq("#siraNo"+idsi);
	var hdnSiraNo = jq("#hdnSiraNo"+idsi);
	var sayfa = jq("#sayfa"+idsi);
	var hdnSayfa = jq("#hdnSayfa"+idsi);
	
	isim.val(hdnIsim.val());
	siraNo.val(hdnSiraNo.val());
	sayfa.val(hdnSayfa.val());
	tuslariKapat(idsi, "Kisim");
}
function kisimKaydet(idsi) {
	var isim = jq("#kisimIsim"+idsi);
	var hdnIsim = jq("#hdnKisimIsim"+idsi);
	var siraNo = jq("#siraNo"+idsi);
	var hdnSiraNo = jq("#hdnSiraNo"+idsi);
	var sayfa = jq("#sayfa"+idsi);
	var hdnSayfa = jq("#hdnSayfa"+idsi);
	
	hdnIsim.val(isim.val());
	hdnSiraNo.val(siraNo.val());
	hdnSayfa.val(sayfa.val());
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kisimkaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kisimId:idsi,isim:isim.val(),sira:siraNo.val(),sayfa:sayfa.val()}
	});
	tuslariKapat(idsi, "Kisim"); 
}

function evradBolumlemeDegisti(idsi){
	var isim = jq("#evradIsim"+idsi);
	var hdnIsim = jq("#hdnEvradIsim"+idsi);
	var evradParcaSayisi = jq("#evradParcaSayisi"+idsi);
	var hdnEvradParcaSayisi = jq("#hdnEvradParcaSayisi"+idsi);
	var hdnEvradSuresi = jq("#hdnEvradSuresi"+idsi);
	
	if((isim.val()!=hdnIsim.val())||(evradParcaSayisi.val()!=hdnEvradParcaSayisi.val()))
		tuslariAc(idsi, "Bolumleme"); else tuslariKapat(idsi, "Bolumleme");(idsi);
}
function bolumlemeVazgec(idsi){
	var isim = jq("#evradIsim"+idsi);
	var hdnIsim = jq("#hdnEvradIsim"+idsi);
	var evradParcaSayisi = jq("#evradParcaSayisi"+idsi);
	var hdnEvradParcaSayisi = jq("#hdnEvradParcaSayisi"+idsi);
	
	
	isim.val(hdnIsim.val());
	evradParcaSayisi.val(hdnEvradParcaSayisi.val());
	tuslariKapat(idsi, "Bolumleme");
}
function bolumlemeKaydet(idsi) {
	var isim = jq("#evradIsim"+idsi);
	var hdnIsim = jq("#hdnEvradIsim"+idsi);
	var evradParcaSayisi = jq("#evradParcaSayisi"+idsi);
	var hdnEvradParcaSayisi = jq("#hdnEvradParcaSayisi"+idsi);
	
	
	hdnIsim.val(isim.val());
	hdnEvradParcaSayisi.val(evradParcaSayisi.val());
	
	jq.ajax({
	    type: "POST",
	    url: "/murakib/bolumlemekaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {bolumlemeId:idsi,isim:isim.val(),sayisi:evradParcaSayisi.val()}
	});
	tuslariKapat(idsi, "Bolumleme"); 
}
function kitabOkumaVazgec(idsi){
	elementKitab = jq("#kitab"+idsi);
	elementIlkSayfa = jq("#kitabIlkSayfa"+idsi);
	elementSonSayfa = jq("#kitabSonSayfa"+idsi);
	elementKitab.val(elementKitab.attr("name"));
	elementIlkSayfa.val(elementIlkSayfa.attr("name"));
	elementSonSayfa.val(elementSonSayfa.attr("name"));
	tuslariKapat(idsi, "KitabOkuma");
}
function grubVazgec(idsi){
	var hdnGrubIsim = jq("#hdnGrubIsim"+idsi);
	var grubIsim=jq("#grubIsim"+idsi);
	var hdnBaslama = jq("#hdnBaslama"+idsi);
	var baslama=jq("#baslama"+idsi);
	var hdnBitis = jq("#hdnBitis"+idsi);
	var bitis=jq("#bitis"+idsi);
	var hdnSure = jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	grubIsim.val(hdnGrubIsim.val());
	baslama.val(hdnBaslama.val());
	bitis.val(hdnBitis.val());
	sure.val(hdnSure.val());
	tuslariKapat(idsi,"Grub");
}
function kitabAboneVazgec(idsi){
	var hdnAboneBaslama = jq("#hdnKitabAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#kitabAboneBaslama"+idsi);
	var hdnAboneSayfaSayisi=jq("#hdnKitabAboneSayfaSayisi"+idsi);
	var inptAboneSayfaSayisi=jq("#kitabAboneSayfaSayisi"+idsi);
	var hdnKitabAboneAciklama=jq("#hdnKitabAboneAciklama"+idsi);
	var inptKitabAboneAciklama=jq("#kitabAboneAciklama"+idsi);
	inptAboneBaslama.val(hdnAboneBaslama.val());
	inptAboneSayfaSayisi.val(hdnAboneSayfaSayisi.val());
	inptKitabAboneAciklama.val(hdnKitabAboneAciklama.val());
	tuslariKapat(idsi, "KitabAbone");
}
function evradAboneVazgec(idsi){
	var hdnAboneBaslama = jq("#hdnEvradAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#evradAboneBaslama"+idsi);
	var hdnSure=jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	var hdnAciklama= jq("#hdnEvradAboneAciklama"+idsi);
	var aciklama = jq("#evradAboneAciklama"+idsi);
	
	inptAboneBaslama.val(hdnAboneBaslama.val());
	sure.val(hdnSure.val());
	aciklama.val(hdnAciklama.val());
	tuslariKapat(idsi, "EvradAbone");
}
function kullaniciVazgec(idsi){
	var elementSelect = jq("#slctKullaniciGrub"+idsi);
	var elementIsim = jq("#kullaniciIsim"+idsi);
	var elementTelefon = jq("#kullaniciTelefon"+idsi);
	var elementMail = jq("#kullaniciMail"+idsi);
	var sifre=jq("#sifre"+idsi);
	var hdnSifre=jq("#hdnSifre"+idsi);
	elementSelect.val(elementSelect.attr("name"));
	elementIsim.val(elementIsim.attr("name"));
	elementTelefon.val(elementTelefon.attr("name"));
	elementMail.val(elementMail.attr("name"));
	sifre.val(hdnSifre.val());
	tuslariKapat(idsi, "Kullanici");
}
function kitabOkumaKaydet(idsi,aboneIdsi) {
	var elementKitab = jq("#kitab"+idsi);
	var elementIlkSayfa = jq("#kitabIlkSayfa"+idsi);
	var elementSonSayfa = jq("#kitabSonSayfa"+idsi);
	if(parseInt(elementIlkSayfa.val()) > parseInt(elementSonSayfa.val())){
		alert("Gelinen Sayfa Numarası Başlanan Sayfa Numarasından Küçük Olmamalı");
		return;
	}
	elementKitab.attr("name",elementKitab.val());
	elementIlkSayfa.attr("name",elementIlkSayfa.val());
	elementSonSayfa.attr("name",elementSonSayfa.val());
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kitabokumakaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kitabOkumaId:idsi,kitab:elementKitab.val(),ilkSayfa:elementIlkSayfa.val(),sonSayfa:elementSonSayfa.val()},
	    success: function(gelen) {
	    	jq("#spnOkunan_OkunmasiGerekenKitab"+aboneIdsi).html(gelen.okunan+"/"+gelen.okunmasiGereken);
	    	if((parseInt(elementSonSayfa.val()) - parseInt(elementIlkSayfa.val()))>=parseInt(gelen.gunlukSayfa)){
	    		siliklestir("#trKitabOkuma"+idsi);
	    	} else {
	    		siliklesmeyiKaldir("#trKitabOkuma"+idsi);
	    	}
	    }
	
	});
	tuslariKapat(idsi, "KitabOkuma");
}
function kitabAboneKaydet(idsi) {
	var hdnAboneBaslama = jq("#hdnKitabAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#kitabAboneBaslama"+idsi);
	var hdnAboneSayfaSayisi=jq("#hdnKitabAboneSayfaSayisi"+idsi);
	var inptAboneSayfaSayisi=jq("#kitabAboneSayfaSayisi"+idsi);
	var hdnKitabAboneAciklama=jq("#hdnKitabAboneAciklama"+idsi);
	var inptKitabAboneAciklama=jq("#kitabAboneAciklama"+idsi);
	
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kitababonekaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kitabAboneId:idsi,baslama:inptAboneBaslama.val(),sayfaSayisi:inptAboneSayfaSayisi.val(),aciklama:inptKitabAboneAciklama.val()}
	});
	hdnAboneBaslama.val(inptAboneBaslama.val());
	hdnAboneSayfaSayisi.val(inptAboneSayfaSayisi.val());
	hdnKitabAboneAciklama.val(inptKitabAboneAciklama.val());
	tuslariKapat(idsi, "KitabAbone");
}
function evradAboneKaydet(idsi) {
	var hdnAboneBaslama = jq("#hdnEvradAboneBaslama"+idsi);
	var inptAboneBaslama = jq("#evradAboneBaslama"+idsi);
	var hdnSure=jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	var hdnAciklama= jq("#hdnEvradAboneAciklama"+idsi);
	var aciklama = jq("#evradAboneAciklama"+idsi);
	hdnAboneBaslama.val(inptAboneBaslama.val());
	hdnAciklama.val(aciklama.val());
	hdnSure.val(sure.val());
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradabonekaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {evradAboneId:idsi,baslama:hdnAboneBaslama.val(),aciklama:aciklama.val(),sure:sure.val()}
	});
	
	tuslariKapat(idsi, "EvradAbone"); 
}
function kullaniciKaydet(idsi) {
	var elementIsim = jq("#kullaniciIsim"+idsi);
	var elementTelefon = jq("#kullaniciTelefon"+idsi);
	var elementMail = jq("#kullaniciMail"+idsi);
	var sifre=jq("#sifre"+idsi);
	var hdnSifre=jq("#hdnSifre"+idsi);
	elementIsim.attr("name",elementIsim.val());
	elementTelefon.attr("name",elementTelefon.val());
	elementMail.attr("name",elementMail.val());
	hdnSifre.val(sifre.val());
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kullanicikaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kullaniciId:idsi,isim:elementIsim.val(),telefon:elementTelefon.val(),mail:elementMail.val(),sifre:sifre.val()}
	});
	tuslariKapat(idsi, "Kullanici");
}
function grubKaydet(idsi) {
	var hdnGrubIsim = jq("#hdnGrubIsim"+idsi);
	var grubIsim=jq("#grubIsim"+idsi);
	var hdnBaslama = jq("#hdnBaslama"+idsi);
	var baslama=jq("#baslama"+idsi);
	var hdnBitis = jq("#hdnBitis"+idsi);
	var bitis=jq("#bitis"+idsi);
	var hdnSure = jq("#hdnSure"+idsi);
	var sure=jq("#sure"+idsi);
	jq.ajax({
	    type: "POST",
	    url: "/murakib/grubkaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {grubId:idsi,isim:grubIsim.val(),baslama:baslama.val(),bitis:bitis.val(),sure:sure.val()},
	    success:function(gelen){
	    	hdnGrubIsim.val(gelen.grub.isim);
	    	hdnBaslama.val(tarihFormat(gelen.grub.baslamaTarihi));	    	
	    	hdnBitis.val(tarihFormat(gelen.grub.bitisTarihi));
	    	hdnSure.val(gelen.grub.parcaSuresi);
	    	grubVazgec(idsi);
//	    	alert(gelen.grub.baslamaTarihi + "formatted :  "+tarihFormat(baslama));
	    }
	
	});
//	tuslariKapat(idsi,"Grub");
}
function tuslariKapat(idsi,isim){
	jq("#div"+isim+"Kaydet"+idsi).hide("slow");
	jq("#div"+isim+"Vazgec"+idsi).hide("slow");
}
function tuslariAc(idsi,isim){
	jq("#div"+isim+"Kaydet"+idsi).show("slow");
	jq("#div"+isim+"Vazgec"+idsi).show("slow");
}


function evradTuslariniKapat(idsi){
//	jq("#btnEvradKaydet"+idsi).hide("slow");
	jq("#divEvradKaydet"+idsi).hide("slow");
}

function evradOkunduOkunmadi(idsi) {
	elementTus = jq("#btnEvradOkunduOkunmadi"+idsi);
	elementSatir=jq("#evradSatir"+idsi);
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
		siliklestir("#evradSatir"+idsi);
	} else {
		elementTus.attr("src",menfi);
		siliklesmeyiKaldir("#evradSatir"+idsi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradokunduokunmadi",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {evradOkumaId:idsi}
//	    success: function(gelen) {
//	    	jq("#spnOkunan_OkunmasiGerekenCevsen").html(gelen.okunan+"/"+gelen.okunmasiGereken);
//	    }
	
	});
}
function kullaniciSelahiyet(idsi) {
	elementTus = jq("#btnSelahiyet"+idsi);
	
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
	} else {
		elementTus.attr("src",menfi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/selahiyet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kullaniciId:idsi}
//	    success: function(gelen) {
//	    	jq("#spnOkunan_OkunmasiGerekenCevsen").html(gelen.okunan+"/"+gelen.okunmasiGereken);
//	    }
	
	});
}
function kitabAboneHususi(idsi) {
	elementTus = jq("#btnKitabAboneHususi"+idsi);
	
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
	} else {
		elementTus.attr("src",menfi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kitababonehususi",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {aboneId:idsi}	
	});
}
function evradAboneSabit(idsi){
	if(idsi==null) {
		alert("Sabit olup olmamasını değiştiremezsiniz.");
		return;
	}
	var elementTus = jq("#btnEvradAboneSabit"+idsi);
	
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
	} else {
		elementTus.attr("src",menfi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradabonesabit",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {aboneId:idsi}	
	});
}

function grubSabit(idsi){
	var elementTus = jq("#btnSabit"+idsi);
	
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
	} else {
		elementTus.attr("src",menfi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/grubsabit",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {grubId:idsi}	
	});
}

function evradAboneHususi(idsi) {
	elementTus = jq("#btnEvradAboneHususi"+idsi);
	
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
	} else {
		elementTus.attr("src",menfi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradabonehususi",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {aboneId:idsi}	
	});
}
function kullaniciAktifPasif(idsi) {
	elementTus = jq("#btnKullaniciAktifPasif"+idsi);
	elementSatir=jq("#kullaniciSatir"+idsi);
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
		siliklestir("#kullaniciSatir"+idsi);
	} else {
		elementTus.attr("src",menfi);
		siliklesmeyiKaldir("#kullaniciSatir"+idsi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kullaniciaktifpasif",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {kullaniciId:idsi}
	});
}
function kitabAboneAktifPasif(idsi) {
	elementTus = jq("#btnKitabAboneAktifPasif"+idsi);
	elementSatir=jq("#kitabAboneSatir"+idsi);
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
		siliklestir("#kitabAboneSatir"+idsi);
	} else {
		elementTus.attr("src",menfi);
		siliklesmeyiKaldir("#kitabAboneSatir"+idsi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/kitababoneaktifpasif",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {aboneId:idsi}
	});
}

function evradAboneAktifPasif(idsi) {
	elementTus = jq("#btnEvradAboneAktifPasif"+idsi);
	elementSatir=jq("#evradAboneSatir"+idsi);
	if(elementTus.attr("src")==menfi) {
		elementTus.attr("src",musbet);
		siliklestir("#evradAboneSatir"+idsi);
	} else {
		elementTus.attr("src",menfi);
		siliklesmeyiKaldir("#evradAboneSatir"+idsi);
	}
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradaboneaktifpasif",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {aboneId:idsi}
	});
}
function evradOkumaKaydet(idsi) {
	elementAciklama = jq("#evradAciklama"+idsi);
	
	elementAciklama.attr("name",elementAciklama.val());
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradokumakaydet",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {evradOkumaId:idsi,aciklama:elementAciklama.val()}
	});
	evradTuslariniKapat(idsi);
}
function donemAyarla(idsi) {
	jq.ajax({
	    type: "POST",
	    url: "/murakib/donemayarla",
	    dataType :"JSON",
	    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
	    data: {donemId:idsi}
	});
}

function selectEvradBolumlemeHazirla(evradAboneId,evradBolumlemeId){
//	jq("#divPopBekleMesaj").fadeIn("slow");
	jq("#slctEvradAboneGrub"+evradAboneId).val(0);
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradbolumlemelistesi",
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    dataType: "JSON",	    
	    success: function(gelen) {	    	
	    	jq('#tdEvradBolumleme'+evradAboneId).html('<select />' );
	    	var select = jq("#tdEvradBolumleme"+evradAboneId+" select");
	    	if(select.prop) {
	    		var options = select.prop('options');
	    	}else {
	    	    var options = select.attr('options');
	    	}
	    	jq('option', select).remove();
	    	jq.each(gelen, function(id, isim) {
    		    options[options.length] = new Option(isim, id);
    		});
	    	select.val(evradBolumlemeId);
	    }
	});
	}
function selectEvradKisimHazirla(containerIsmi,selectIsmi,evradAboneId,evradBolumlemeId,evradKisimId){
//	jq("#slctEvradAboneGrub"+evradAboneId).val(0);
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradkisimlistesi",
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    dataType: "JSON",
	    data: {evradBolumlemeId:evradBolumlemeId,evradAboneId:evradAboneId},
	    success: function(gelen) {
	    	jq('#'+containerIsmi).html('<select id="'+selectIsmi+'" onchange="evradAboneDegisti('+evradAboneId+')"/>' );
	    	var select = jq("#"+containerIsmi+" select");
	    	if(select.prop) {
	    		var options = select.prop('options');
	    	}else {
	    	    var options = select.attr('options');
	    	}
	    	jq('option', select).remove();
	    	jq.each(gelen, function(id, isim) {
    		    options[options.length] = new Option(isim, id);
    		});
	    	if(evradKisimId!=null)select.val(evradKisimId);
	    }	        
	});
}

function grubSecildi(grubId){
	if(grubId>0) {
		jq.ajax({
		    type: "POST",
		    url: "/murakib/grubunevradi",
		    dataType :"JSON",
		    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
		    data: {grubId:grubId},
		    success: function(gelen) {
		    	jq("#slctBolumleme").val(gelen.evradBolumlemeId);
		    	jq("#grubBolumlemeId").val(gelen.evradBolumlemeId);
		    	jq("#parcaSuresi").val(gelen.grubParcaSuresi);
		    	jq("#baslamaTarihi").val(gelen.grubBaslama);
		    	evradKisimHazirla();
		    }
		});
		jq("#parcaSuresi").prop("disabled","disabled");
		jq("#sabit").prop("disabled","disabled");
	} else {
		evradKisimHazirla();
		jq("#parcaSuresi").removeAttr('disabled');
		jq("#sabit").removeAttr('disabled');
	}
}
function evradSecildi(evradId){
	if(evradId!=jq("#grubBolumlemeId").val()) jq("#grubBolumlemeId").val("0");
	jq("#slctGrub").val(0);
	jq("#parcaSuresi").removeAttr('disabled');
	jq("#sabit").removeAttr('disabled');
	evradKisimHazirla();
}
function evradKisimHazirla(){
//	jq("#slctEvradAboneGrub"+evradAboneId).val(0);
	var evradBolumlemeId=jq("#slctBolumleme").val();
	var grubId=jq("#slctGrub").val();	
	jq.ajax({
	    type: "POST",
	    url: "/murakib/evradkisimlistesi",
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    dataType: "JSON",
	    data: {evradBolumlemeId:evradBolumlemeId,grubId:grubId},
	    success: function(gelen) {
	    	var select = jq("#slctKisim");
	    	if(select.prop) {
	    		var options = select.prop('options');
	    	}else {
	    	    var options = select.attr('options');
	    	}
	    	jq('option', select).remove();
	    	jq.each(gelen, function(id, isim) {
    		    options[options.length] = new Option(isim, id);
    		});
	    }	        
	});
	}
function gecmisGunler(){
	var tus=jq("#btnGecmisGunler");
	jq("#divGecmisGunler").toggle(1000);
	if(tus.attr("src")=="/murakib/resources/resim/asagi.png") {
		tus.attr("src","/murakib/resources/resim/yukari.png"); 
	}else {
		tus.attr("src","/murakib/resources/resim/asagi.png");
	}
}
function tarihFormat(tarih){
	if(tarih==null) return null;
	var date=new Date(tarih);	
	var gun=date.getDate();
	if(gun<10) gun="0"+gun;
	var ay=date.getMonth()+1;
	if(ay<10) ay="0"+ay;
	return (gun+"."+ay+"."+date.getFullYear());
}
function kitabAboneSil(idsi){
	if(confirm("Bu Kitab Aboneliği Tüm Okumalarla Beraber Silinecek")) {
		jq.ajax({
		    type: "POST",
		    url: "/murakib/kitababonesil",
		    dataType :"JSON",
		    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
		    data: {kitabAboneId:idsi},
		    success: function(gelen) {
		    	alert(gelen.silinenOkumaSayisi + " Adet Okuma Silindi");
		    	jq("#kitabAboneSatir"+idsi).remove();
		    },
		    error: function (xhr, textStatus, errorThrown) {		    	
		        alert(xhr.responseText);}
		});
	}
}
	function evradAboneSil(idsi){
		if(confirm("Bu Evrad Aboneliği Tüm Okumalarla Beraber Silinecek")) {
			jq.ajax({
			    type: "POST",
			    url: "/murakib/evradabonesil",
			    dataType :"JSON",
			    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
			    data: {evradAboneId:idsi},
			    success: function(gelen) {
			    	alert(gelen.silinenOkumaSayisi + " Adet Okuma ile Beraber Abonelik Silindi");
			    	jq("#evradAboneSatir"+idsi).remove();
			    },
			    error: function (xhr, textStatus, errorThrown) {		    	
			        alert(xhr.responseText);}
			});
		}
	}
	function kullaniciSil(idsi){
		if(confirm("Bu Kullanıcı Tüm Kayıtlarıyla Beraber Silinecek")) {
			if(confirm("Kullanıcı Silindiğinde Kullanıcıyla İlgili Tüm Kayıtlar Silinmektedir")) {
				jq.ajax({
				    type: "POST",
				    url: "/murakib/kullanicisil",
				    dataType :"JSON",
				    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
				    data: {kullaniciId:idsi},
				    success: function(gelen) {
				    	alert(gelen.silinenOkumaSayisi + " Adet Okuma İle Beraber Kullanıcı Silindi");
				    	jq("#kullaniciSatir"+idsi).remove();
				    },
				    error: function (xhr, textStatus, errorThrown) {		    	
				        alert(xhr.responseText);}
				});
			}
		}
	}
	function kitabOkumaSil(idsi){
		if(confirm("Bu Okuma Silinecek")) {
			jq.ajax({
			    type: "POST",
			    url: "/murakib/kitabokumasil",
			    dataType :"JSON",
			    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
			    data: {okumaId:idsi},
			    success: function(gelen) {
			    	alert(gelen.silinenOkumaSayisi + " Adet Kitab Okuma Silindi");
			    	jq("#trKitabOkuma"+idsi).remove();
			    },
			    error: function (xhr, textStatus, errorThrown) {		    	
			        alert(xhr.responseText);}
			});
		}
	}
	function evradOkumaSil(idsi){
		if(confirm("Bu Okuma Silinecek")) {
			jq.ajax({
			    type: "POST",
			    url: "/murakib/evradokumasil",
			    dataType :"JSON",
			    contentType: "application/x-www-form-urlencoded;charset=UTF-8",	    
			    data: {okumaId:idsi},
			    success: function(gelen) {
			    	alert(gelen.silinenOkumaSayisi + " Adet Evrad Okuma Silindi");
			    	jq("#evradSatir"+idsi).remove();
			    },
			    error: function (xhr, textStatus, errorThrown) {		    	
			        alert(xhr.responseText);}
			});
		}
	}
	
	function siliklestir(elementId){
		jq(elementId).css("color","#9fa29e");
		jq(elementId+" a").css("color","#9fa29e");
		jq(elementId+" input").css("color","#9fa29e");
	} 
	function siliklesmeyiKaldir(elementId) {
		jq(elementId).css("color","black");
		jq(elementId+" a").css("color","blue");
		jq(elementId+" input").css("color","black");
	}
	
//	function cikis() {
//		jq.ajax({
//		    type: "GET",
//		    url: "/murakib/kullanicicikisi",
//		    dataType :"JSON",
//		    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
//		    success: function(gelen) {
//		    	window.self.close();
//		    	return false;
//		    },
//		    error: function (xhr, textStatus, errorThrown) {		    	
//		        alert(xhr.responseText);
//		    }
//		});  
//    }
