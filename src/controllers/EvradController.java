package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DonemServis;
import service.EvradServis;
import service.IslemServis;
import service.KullaniciServis;
import araclar.Genel;
import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.EvradOkuma;
import forms.Donem;
import forms.Grub;
import forms.KitabAbone;
import forms.Kullanici;
@Controller
public class EvradController {
//	@Autowired
//	private KullaniciServis kullaniciServis;	
	@Autowired
	private IslemServis islemServis;
	@Autowired
	private EvradServis evradServis;
	@Autowired
	private DonemServis donemServis;
	@Autowired
	private KullaniciServis kullaniciServis;

	 @RequestMapping(value = "/evradokunduokunmadi", method = RequestMethod.POST)
	 public @ResponseBody void evradOkunduOkunmadi(@RequestParam(value="evradOkumaId", required=true) Long evradOkumaId,HttpSession session,Map<String, Object> map) {
//		 if (Genel.oturumKapali(session)) return null;
		 Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		 EvradOkuma evradOkuma = (EvradOkuma)islemServis.getir(evradOkumaId, EvradOkuma.class);
		 if(evradOkuma.getKayitTarihi()==null) evradOkuma.setKayitTarihi(new Date()); else
			 evradOkuma.setKayitTarihi(null);
		 islemServis.kaydet(evradOkuma);
//		 Donem donem=(Donem) session.getAttribute("donem"); 
//		 JSONObject jsonObject=new JSONObject();
//		 Map sonuc =new HashMap<>();		 
//		 jsonObject.put("okunan",evradServis.donemIciOkunanCevsenSayisi(donem));
//		 jsonObject.put("okunmasiGereken",Genel.gunSayisi(donem));
//		 jsonObject.put("sonuc", sonuc);
//		 return jsonObject.toJSONString();
	 }
	 
	 @RequestMapping(value = "/evradokumakaydet", method = RequestMethod.POST)
	 public @ResponseBody void evradOkumaKaydet(@RequestParam(value="evradOkumaId", required=true) Long evradOkumaId,@RequestParam(value="aciklama", required=true) String aciklama,HttpSession session,Map<String, Object> map) {
//		 if (Genel.oturumKapali(session)) return null;
		 Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		 EvradOkuma evradOkuma = (EvradOkuma)islemServis.getir(evradOkumaId, EvradOkuma.class);
		 evradOkuma.setAciklama(aciklama);
		 islemServis.kaydet(evradOkuma);
	 }
	 @RequestMapping(value = "/evrad")
		public String evradlar(HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			Donem donem=(Donem)session.getAttribute("donem");
			Date bugun=Genel.sadeceTarih(new Date());
			if(donem==null) {
				donem=donemServis.gecerliDonemiGetir();
				session.setAttribute("donem", donem);
			}
			if(donem!=null){
				List listAbone=kullaniciServis.evradAboneligi(aktifKullanici.getId(),null,null,false);
				if(listAbone!=null && listAbone.size()>0){
					Iterator<EvradAbone> iterAbone=listAbone.iterator();
					ArrayList<EvradOkuma> evradOkumaListesi=new ArrayList<>();				
					while(iterAbone.hasNext()){
						EvradAbone evradAbone=iterAbone.next();
						evradServis.bosGunleriDoldur(evradAbone);
						
						evradServis.evradOkumaEkle(evradAbone, bugun);
						List okunanEvradListesi=evradServis.evradOkumaListesi(evradAbone, donem, null,null);
						evradOkumaListesi.addAll(okunanEvradListesi);
						
					}				
					model.put("evradOkumaListesi", evradOkumaListesi);
				}
			}
			model.put("donemler",donemServis.listDonemler());
			model.put("kullanici", aktifKullanici);
			return "evradJsp/evrad";
		}
	 @RequestMapping(value = "/evrad/{id}")
		public String evrad(@PathVariable("id") Long evradAboneId,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			Donem donem=(Donem)session.getAttribute("donem");
			Date bugun=Genel.sadeceTarih(new Date());
			if(donem==null) {
				donem=donemServis.gecerliDonemiGetir();
				session.setAttribute("donem", donem);
			}
//			if(donem!=null){
						EvradAbone evradAbone=(EvradAbone)islemServis.getir(evradAboneId, EvradAbone.class);
//						evradServis.bosGunleriDoldur(evradAbone);
//						evradServis.evradOkumaEkle(evradAbone, bugun);
						List okunanEvradListesi=evradServis.evradOkumaListesi(evradAbone, donem, null,null);
//			}				
			model.put("evradOkumaListesi", okunanEvradListesi);
			model.put("donemler",donemServis.listDonemler());
			model.put("kullanici", evradAbone.getKullanici());
			return "evradJsp/evrad";
		}
	 @RequestMapping(value = "/donemayarla", method = RequestMethod.POST)
	 public @ResponseBody void donemAyarla(@RequestParam(value="donemId", required=true) Long donemId,HttpSession session,Map<String, Object> map) {
//		 if (Genel.oturumKapali(session)) return null;
//		 Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		 Donem donem = (Donem)islemServis.getir(donemId, Donem.class);
		 session.setAttribute("donem", donem);
	 }
	 
	 @RequestMapping(value = "/evradbolumlemelistesi", method = RequestMethod.POST)
	 public @ResponseBody JSONObject evradBolumlemeListesi(HttpSession session) {
	 	 JSONObject jsonObject=new JSONObject();
	 	 if (!Genel.oturumKapali(session)) {
	 			List evradBolumlemeListe=evradServis.evradBolumlemeListesi();
	 			if(evradBolumlemeListe!=null && evradBolumlemeListe.size()>0){
	 				Iterator<EvradBolumleme> evradBolumlemeIterator=evradBolumlemeListe.iterator();
	 				while(evradBolumlemeIterator.hasNext()){
	 					EvradBolumleme evradBolumleme=evradBolumlemeIterator.next();
	 					jsonObject.put(evradBolumleme.getId(),evradBolumleme.getIsim());	
	 				}
	 			}
	 		}
	 		return(jsonObject);
	 }
	 @RequestMapping(value = "/evradkisimlistesi", method = RequestMethod.POST)
	 public @ResponseBody JSONObject evradKisimListesi(@RequestParam(value="evradBolumlemeId", required=true) Long evradBolumlemeId,@RequestParam(value="evradAboneId", required=false) Long evradAboneId,@RequestParam(value="grubId", required=false) Long grubId,HttpSession session) {
//	 	 System.out.println(evradBolumlemeId+" : "+evradAboneId+" : "+grubId);
		 JSONObject jsonObject=new JSONObject();
	 	 EvradAbone evradAbone=null;
	 	 if (evradAboneId!=null && grubId==null) {
	 		 evradAbone=(EvradAbone) islemServis.getir(evradAboneId,EvradAbone.class);
	 		 grubId = evradAbone.getGrub().getId();
	 	 }
		 List evradKisimListe=evradServis.evradKisimListesi(evradBolumlemeId,evradAboneId,grubId);
	 	 if(grubId!=null && grubId>0){
	 		 Grub grub=(Grub)islemServis.getir(grubId, Grub.class);
	 		 if(grub.getEvradBolumleme().getParcaSayisi()==1) evradKisimListe=evradServis.evradKisimListesi(null,null,grubId);
	 	 }		 
		 if(evradKisimListe!=null && evradKisimListe.size()>0){
			Iterator<EvradKisim> evradKisimIterator=evradKisimListe.iterator();
			while(evradKisimIterator.hasNext()){
				EvradKisim evradKisim=evradKisimIterator.next();
				jsonObject.put(evradKisim.getId(),evradKisim.getIsim());	
			}
		 }
	 	 return(jsonObject);
	 }
	 @RequestMapping(value = "/evradlistesi")
		public String evradlistesi(HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
//			Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			model.put("evradBolumlemeListesi", evradServis.evradBolumlemeListesi());
			model.put("donemler",donemServis.listDonemler());
			
			return "evradJsp/evradbolumlemeler";
		} 
	 @RequestMapping(value = "/bolumlemekaydet", method = RequestMethod.POST)
	 public @ResponseBody void bolumlemeKaydet(@RequestParam(value="bolumlemeId", required=true) Long bolumlemeId,@RequestParam(value="isim", required=true) String isim,@RequestParam(value="sayisi", required=true) Short sayisi,HttpSession session) {
//		 if (Genel.oturumKapali(session)) return null;
//		 Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		 EvradBolumleme evradBolumleme = (EvradBolumleme)islemServis.getir(bolumlemeId, EvradBolumleme.class);
		 evradBolumleme.setIsim(isim);
		 evradBolumleme.setParcaSayisi(sayisi);
//		 evradBolumleme.setParcaSuresi(suresi);
		 islemServis.kaydet(evradBolumleme);
	 }
	 @RequestMapping(value = "/kisimkaydet", method = RequestMethod.POST)
	 public @ResponseBody void kisimKaydet(@RequestParam(value="kisimId", required=true) Long kisimId,@RequestParam(value="isim", required=true) String isim,@RequestParam(value="sira", required=true) Short sira,@RequestParam(value="sayfa", required=true) String sayfa,HttpSession session) {
//		 if (Genel.oturumKapali(session)) return null;
//		 Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		 
		 EvradKisim evradKisim = (EvradKisim)islemServis.getir(kisimId, EvradKisim.class);
		 evradKisim.setIsim(isim);
		 evradKisim.setSayfaAraligi(sayfa);
		 evradKisim.setSiraNo(sira);
		 islemServis.kaydet(evradKisim);
	 }
	 @RequestMapping(value = "/bolumkisimlar/{id}")
		public String abonelikler(@PathVariable("id") Long bolumId,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			EvradBolumleme evradBolumleme=(EvradBolumleme)islemServis.getir(bolumId, EvradBolumleme.class);
			kisimlariEkle(evradBolumleme);
			List kisimListesi=evradServis.evradKisimListesi(bolumId, null, null);
//			Short kisimSayisi=0;
//			Short parcaSayisi=0;
//			if(kisimListesi!=null) kisimSayisi=(short)kisimListesi.size();
//			parcaSayisi=evradBolumleme.getParcaSayisi();
//			if(parcaSayisi>kisimSayisi){
//				for (int i = kisimSayisi+1; i <= parcaSayisi; i++) {
//					EvradKisim evradKisim=new EvradKisim();
//					evradKisim.setEvradBolumleme(evradBolumleme);
//					evradKisim.setSiraNo((short)i);
//					islemServis.kaydet(evradKisim);
//				}
//				kisimListesi=evradServis.evradKisimListesi(bolumId, null, null);
//			}
//			if(parcaSayisi<kisimSayisi){
//				for (int i = kisimSayisi; i <= parcaSayisi; i++) {
//					EvradKisim evradKisim=new EvradKisim();
//					evradKisim.setEvradBolumleme(evradBolumleme);
//					islemServis.kaydet(evradKisim);
//				}
//			}
			model.put("kisimListesi",kisimListesi);
			model.put("donemler",donemServis.listDonemler());
			return "evradJsp/kisimlar";
		 }
	 
	 
	 @RequestMapping(value = "/evradbolumlemeekle")
	 public String evradBolumlemeEkle(ModelMap model,HttpSession session) {
		 if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		 		 
		 model.put("donemler",donemServis.listDonemler());
		 Kullanici aktifKullanici=Genel.getKullanici(session);
		 if (aktifKullanici.getSelahiyet()){
			 EvradBolumleme evradBolumleme=new EvradBolumleme();
			 model.put("eklenenEvradBolumleme", evradBolumleme);
			 return "evradJsp/evradbolumlemeekle";
		 }
		 
		 return "redirect:/kullanicigirisi";
	 }
	 @RequestMapping(value = "/yenibolumlemekaydet", method = RequestMethod.POST)
		public String yeniBolumlemeKaydet(@ModelAttribute("eklenenEvradBolumleme") EvradBolumleme eklenenBolumleme,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=Genel.getKullanici(session);
			String sonuc="";	
			if(eklenenBolumleme.getParcaSayisi()==null || eklenenBolumleme.getParcaSayisi()<1) eklenenBolumleme.setParcaSayisi((short)1);
			islemServis.kaydet(eklenenBolumleme);
			kisimlariEkle(eklenenBolumleme);
			sonuc=eklenenBolumleme.getId()+" Evrad Bölümleme Eklendi.";
			model.put("eklenenEvradBolumleme", eklenenBolumleme);
			model.put("sonuc",sonuc);
			model.put("donemler",donemServis.listDonemler());
			return "evradJsp/evradbolumlemeekle";
		}
	 @RequestMapping(value = "/evradokumasil", method = RequestMethod.POST)
	 public @ResponseBody JSONObject evradOkumaSil(@RequestParam(value="okumaId", required=true) Long okumaId,HttpSession session) {
		JSONObject jsonObject=new JSONObject();
		 if (!Genel.oturumKapali(session)) {
			 jsonObject.put("silinenOkumaSayisi", islemServis.calistir("delete FROM EvradOkuma WHERE id="+okumaId));
		 }
		 return jsonObject;
	 }
	 private void kisimlariEkle(EvradBolumleme evradBolumleme){
		 List kisimListesi=evradServis.evradKisimListesi(evradBolumleme.getId(), null, null);
			Short kisimSayisi=0;
			Short parcaSayisi=0;
			if(kisimListesi!=null) kisimSayisi=(short)kisimListesi.size();
			parcaSayisi=evradBolumleme.getParcaSayisi();
			if(parcaSayisi>kisimSayisi){
				for (int i = kisimSayisi+1; i <= parcaSayisi; i++) {
					EvradKisim evradKisim=new EvradKisim();
					evradKisim.setEvradBolumleme(evradBolumleme);
					evradKisim.setSiraNo((short)i);
					evradKisim.setIsim("");
					islemServis.kaydet(evradKisim);
				}
			}
	 }
}
