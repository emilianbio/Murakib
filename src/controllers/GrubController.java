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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DonemServis;
import service.EvradServis;
import service.GrubServis;
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
public class GrubController {
//	@Autowired
//	private KullaniciServis kullaniciServis;	
	@Autowired
	private IslemServis islemServis;
	@Autowired
	private EvradServis evradServis;
	@Autowired
	private DonemServis donemServis;
	@Autowired
	private GrubServis grubServis;
	
	 @RequestMapping(value = "/grubkaydet", method = RequestMethod.POST)
	 public @ResponseBody JSONObject grubKaydet(@RequestParam(value="grubId", required=true) Long grubId,@RequestParam(value="isim", required=true) String isim,@RequestParam(value="baslama", required=true) String baslama,@RequestParam(value="bitis", required=true) String bitis,@RequestParam(value="sure", required=true) Short sure,HttpSession session) {
//		 if (Genel.oturumKapali(session)) return null;
//		 Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
		 JSONObject jsonObject=new JSONObject();
		 Grub grub=grubServis.grubGuncelle(grubId,isim,baslama,bitis,sure);
		 jsonObject.put("grub",grub );
		 return jsonObject;
	 }
	 @RequestMapping(value = "/grublar")
		public String grublar(HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=Genel.getKullanici(session);
			Grub arananGrub=new Grub();
			arananGrub.setGrub(aktifKullanici.getGrub());
			model.put("arananGrub",arananGrub);
			
			model.put("grubListesi",grubServis.grubListesi(arananGrub));
			
			
			model.put("evradBolumlemeListesi", evradServis.evradBolumlemeListesi());
			model.put("donemler",donemServis.listDonemler());
			return "grubJsp/grublar";
		}	
	 @RequestMapping(value = "/grublistele", method = RequestMethod.POST)
		public String grubListele(@ModelAttribute("arananGrub") Grub arananGrub,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			model.put("arananGrub", arananGrub);
			model.put("grubListesi",grubServis.grubListesi(arananGrub));
			model.put("evradBolumlemeListesi", evradServis.evradBolumlemeListesi());
			model.put("donemler",donemServis.listDonemler());
			return "grubJsp/grublar";
		}
	 @RequestMapping(value = "/grubekle")
	 public String grubEkle(ModelMap model,HttpSession session) {
		 if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		 
		 model.put("evradBolumlemeListesi", evradServis.evradBolumlemeListesi());
		 model.put("donemler",donemServis.listDonemler());
		 Kullanici aktifKullanici=Genel.getKullanici(session);
		 if (aktifKullanici.getSelahiyet()){
			 Grub grub=new Grub();
			 grub.setParcaSuresi((short)7);
			 grub.setBaslamaTarihi(new Date());
			 model.put("eklenenGrub", grub);
			 return "grubJsp/grubekle";
		 }
		 
		 return "redirect:/kullanicigirisi";
	 }
	 @RequestMapping(value = "/yenigrubkaydet", method = RequestMethod.POST)
		public String yenigGrubKaydet(@ModelAttribute("eklenenGrub") Grub eklenenGrub,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=Genel.getKullanici(session);
			String sonuc="";
			if(eklenenGrub.getBaslamaTarihi()==null) {
				sonuc= "Başlama Tarihi Boş Olamaz.";
			} else {
				eklenenGrub.setGrub(aktifKullanici.getGrub());	
				if(eklenenGrub.getParcaSuresi()==null ||eklenenGrub.getParcaSuresi()<1) eklenenGrub.setParcaSuresi((short)7);
				islemServis.kaydet(eklenenGrub);
				eklenenGrub = (Grub) islemServis.getir(eklenenGrub.getId(), Grub.class);
				sonuc=eklenenGrub.getId()+" Grub Eklendi.";
				model.put("grub", eklenenGrub);
			}
			model.put("sonuc",sonuc);
			model.put("eklenenGrub", eklenenGrub);
			model.put("evradBolumlemeListesi", evradServis.evradBolumlemeListesi());
			model.put("donemler",donemServis.listDonemler());
			return "grubJsp/grubekle";
		}
	 @RequestMapping(value = "/grubsabit", method = RequestMethod.POST)
	 public @ResponseBody void grubSabit(@RequestParam(value="grubId", required=true) Long grubId,HttpSession session) {
			 Grub grub=(Grub)islemServis.getir(grubId, Grub.class);
			 if(grub!=null){
				 if(grub.getSabit()==null) grub.setSabit(false);
				 grub.setSabit(!grub.getSabit());
			 	 islemServis.kaydet(grub);
			 }
//		 }
	 }
}
