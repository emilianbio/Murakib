package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.DonemServis;
import service.EvradServis;
import service.GrubServis;
import service.IslemServis;
import service.KitabServis;
import service.KullaniciServis;
import araclar.Genel;
import forms.Donem;
import forms.EvradAbone;
import forms.EvradOkuma;
import forms.KitabAbone;
import forms.KitabOkuma;
import forms.Kullanici;
@Controller
public class KullaniciController {
	@Autowired
	private KullaniciServis kullaniciServis;	
	@Autowired
	private IslemServis islemServis;
	@Autowired
	private DonemServis donemServis;
	@Autowired
	private KitabServis kitabServis;
	@Autowired
	private EvradServis evradServis;
	@Autowired
	private GrubServis grubServis;
	
	@RequestMapping(value = "/kullanicigirisi")
	public ModelAndView kKullaniciGirisi(Map<String, Object> map,HttpSession session){
		map.put("kullanici",new Kullanici());
		return new ModelAndView("kullaniciJsp/giris");	
	}
	@RequestMapping(value = "/kullanicicikisi")
	public ModelAndView kKullaniciCikisi(HttpSession session){
		session.removeAttribute("userInfo");
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("sonuc", true);
//		return jsonObject;
		return new ModelAndView("redirect:/kullanicigirisi");	
	}
	@RequestMapping(value = "/anasayfa")
	public String AnaSayfa(HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici kullanici=(Kullanici)session.getAttribute("userInfo");
		Donem donem=(Donem)session.getAttribute("donem");
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		Date bugun=new Date();
		if(donem!=null){
//			long donemGunSayisi=Genel.gunSayisi(donem);
//			long gunSayisi = donemGunSayisi;
			List kitabAboneListesi = kullaniciServis.kitabAboneligi(kullanici.getId(),false);
			
			if(kitabAboneListesi!=null && kitabAboneListesi.size()>0){
				Iterator<KitabAbone> iteratorKitabAbone=kitabAboneListesi.iterator();
				ArrayList<KitabOkuma> kitabOkumaListesi=new ArrayList<>();
				ArrayList<Object> kitabOkumaListesiGun = new ArrayList<>();
				while (iteratorKitabAbone.hasNext()) {
					KitabAbone kitabAbone=iteratorKitabAbone.next();
//					long abonelikGunSayisi = Genel.gunFarki(bugun, kitabAbone.getBaslamaTarihi())+1;
//					if (abonelikGunSayisi<donemGunSayisi) gunSayisi=abonelikGunSayisi;
					Long gunSayisi=Genel.kullanilacakGunSayisi(donem, kitabAbone.getBaslamaTarihi());
					kitabServis.buguneKitabOkumaEkle(kitabAbone);
					KitabOkuma kitabOkuma=new KitabOkuma();
//					kitabOkuma.setKitab("YeniBaşlık");
					kitabOkuma.setKitabAbone(kitabAbone);
					kitabOkuma.setIlkSayfa(kitabServis.donemIciOkunanSayfaSayisi(donem,kitabAbone).shortValue());
					Long okunmasiGereken=gunSayisi*kitabAbone.getGunlukSayfaSayisi();
					kitabOkuma.setSonSayfa(okunmasiGereken.shortValue());
					kitabOkumaListesi.add(kitabOkuma);
					kitabOkumaListesiGun.addAll(kitabServis.kitabOkumaListesi(kitabAbone, donem, 1,null));
					kitabOkumaListesi.addAll(kitabServis.kitabOkumaListesi(kitabAbone, donem, 3,1));
				}
				model.put("kitabOkumaListesi", kitabOkumaListesi);
				model.put("kitabOkumaListesiGun", kitabOkumaListesiGun);
			}
			List listAbone=kullaniciServis.evradAboneligi(kullanici.getId(),null,null,false);
			if(listAbone!=null && listAbone.size()>0){
				Iterator<EvradAbone> iterAbone=listAbone.iterator();
				ArrayList<EvradOkuma> evradOkumaListesi=new ArrayList<>();
				ArrayList<EvradOkuma> evradOkumaListesiGun=new ArrayList<>();
				while(iterAbone.hasNext()){
					EvradAbone evradAbone=iterAbone.next();
//					evradAbone.getClass().getSimpleName();
					evradServis.evradOkumaEkle(evradAbone, bugun);
					List okunanEvradListesi=evradServis.evradOkumaListesi(evradAbone, donem, 3,1);
					evradOkumaListesiGun.addAll(evradServis.evradOkumaListesi(evradAbone, donem, 1,null));
					evradOkumaListesi.addAll(okunanEvradListesi);
				}				
				model.put("evradOkumaListesi", evradOkumaListesi);
				model.put("evradOkumaListesiGun", evradOkumaListesiGun);
				
			}
		}
		model.put("donemler",donemServis.listDonemler());
		
		return "anasayfa";
	}
	@RequestMapping(value = "/kullanicionay", method = RequestMethod.POST)
	public ModelAndView kKullaniciOnay(@ModelAttribute("kullanici") Kullanici kullanici,HttpServletRequest request) {
		Kullanici kayitliKullanici= kullaniciServis.kullaniciGetir(kullanici,false);
		String geldigiYer="anasayfa";
//		if(geldigiYer==null) geldigiYer="anasayfa"; else request.getSession().removeAttribute("geldigiYer");
		if (kayitliKullanici==null) {
			return new ModelAndView("redirect:/kullanicigirisi"); 
		} else {
			HttpSession session=request.getSession();
			session.setAttribute("userInfo",kayitliKullanici);
			String ipNo="";
			ipNo=request.getHeader("X-Forwarded-For"); //iç ağda ise buradan null değer döner
			if(ipNo==null) ipNo=request.getRemoteAddr();
//			kullaniciServis.girisEkle(kayitliKullanici,new Date(session.getCreationTime()),ipNo);
			return new ModelAndView("redirect:"+geldigiYer);
		}
	}
	
	 @RequestMapping(value = "/kullaniciaktifpasif", method = RequestMethod.POST)
	 public @ResponseBody void kullaniciAktifPasif(@RequestParam(value="kullaniciId", required=true) Long kullaniciId,Model model,HttpSession session) {
//		 if (!Genel.oturumKapali(session)) kullaniciServis.pasifPasifDegil(yetkiliId);
		 Kullanici kullanici=(Kullanici)islemServis.getir(kullaniciId, Kullanici.class);
		 if(kullanici!=null){
			 if(kullanici.getPasif()==null) kullanici.setPasif(false);
			 kullanici.setPasif(!kullanici.getPasif());
			 islemServis.kaydet(kullanici);
		 }
	 }
	 @RequestMapping(value = "/kullanicilar")
		public String kullaniciIsleri(HttpSession session,ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=Genel.getKullanici(session);
			Kullanici kullanici=new Kullanici();
			if(session.getAttribute("arananKullanici")!=null) kullanici=(Kullanici)session.getAttribute("arananKullanici");
			model.put("kullaniciListesi",kullaniciServis.kullaniciListesi(kullanici,aktifKullanici.getGrub(),aktifKullanici));
			model.put("arananKullanici", kullanici);
			model.put("donemler",donemServis.listDonemler());
			return "kullaniciJsp/kullanicilar";
		}
		
		@RequestMapping(value = "/kullanicilistele", method = RequestMethod.POST)
		public String kullaniciListesi(@ModelAttribute("arananKullanici") Kullanici arananKullanici,HttpSession session, ModelMap model){
			if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			Kullanici aktifKullanici=Genel.getKullanici(session);
			model.put("kullaniciListesi",kullaniciServis.kullaniciListesi(arananKullanici,aktifKullanici.getGrub(),aktifKullanici));
//			model.put("grubListesi",grubServis.grubListesi(aktifKullanici.getGrub()));
			model.put("arananKullanici", arananKullanici);
			model.put("donemler",donemServis.listDonemler());
			return "kullaniciJsp/kullanicilar";
		}
		 @RequestMapping(value = "/kullanicikaydet", method = RequestMethod.POST)
		 public @ResponseBody void kullaniciKaydet(@RequestParam(value="kullaniciId", required=true) Long kullaniciId,@RequestParam(value="isim", required=true) String isim,@RequestParam(value="telefon", required=true) String telefon,@RequestParam(value="mail", required=true) String mail,@RequestParam(value="sifre", required=true) String sifre,Model model,HttpSession session) {
			 if (!Genel.oturumKapali(session)) {
				 Kullanici aktifKullanici=Genel.getKullanici(session);
				 Kullanici kullanici=(Kullanici)islemServis.getir(kullaniciId, Kullanici.class);
				 if(kullanici!=null){
					 kullanici.setEmail(mail);
					 kullanici.setIsim(isim);
					 kullanici.setTelefon(telefon);
					 if(kullanici.getExtSelahiyet()){
						 if(aktifKullanici.getExtSelahiyet()) kullanici.setSifre(sifre);
					 } else {
						 kullanici.setSifre(sifre);
					 }
					 islemServis.kaydet(kullanici);
				 }
			}
		 }
		 @RequestMapping(value = "/selahiyet", method = RequestMethod.POST)
		 public @ResponseBody void selahiyet(@RequestParam(value="kullaniciId", required=true) Long kullaniciId,Model model,HttpSession session) {
			 if (!Genel.oturumKapali(session)) {
				 Kullanici aktifKullanici=Genel.getKullanici(session);
				 if(aktifKullanici.getExtSelahiyet()) {
					 Kullanici kullanici=(Kullanici)islemServis.getir(kullaniciId, Kullanici.class);
					 if(kullanici!=null){
						 if(kullanici.getSelahiyet()==null)kullanici.setSelahiyet(false);
						 kullanici.setSelahiyet(!kullanici.getSelahiyet());
						 islemServis.kaydet(kullanici);
					 }
				 }
			 }
		 }
		 
		 @RequestMapping(value = "/kullaniciekle")
		 public String kullaniciEkle(ModelMap model,HttpSession session) {
			 if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
			 Kullanici aktifKullanici=Genel.getKullanici(session);
			 model.put("donemler",donemServis.listDonemler());
			 if (aktifKullanici.getSelahiyet()){
				 Kullanici kullanici=new Kullanici();
				 model.put("eklenenKullanici", kullanici);
				 return "kullaniciJsp/kullaniciekle";
			 }
			 return "redirect:/kullanicigirisi";
		 }
		 @RequestMapping(value = "/yenikullanicikaydet", method = RequestMethod.POST)
			public String yeniKullaniciKaydet(@ModelAttribute("kullanici") Kullanici kullanici,HttpSession session, ModelMap model){
				if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
				Kullanici aktifKullanici=Genel.getKullanici(session);
				Kullanici kullaniciEklenecek=new Kullanici();
			    kullanici.setGrub(aktifKullanici.getGrub());
			    kullaniciEklenecek.setIsim(kullanici.getIsim());
			    kullaniciEklenecek.setEmail(kullanici.getEmail());
			    kullaniciEklenecek.setTelefon(kullanici.getTelefon());
				if(!kullaniciServis.kullaniciVarMi(kullanici.getIsim())){
					kullanici.setGrub(aktifKullanici.getGrub());
					kullanici.setPasif(false);
					kullanici.setSifre("0");
					kullanici.setSelahiyet(false);
					kullanici.setExtSelahiyet(false);
					islemServis.kaydet(kullanici);				    
					model.put("kullanici", kullanici);
				    model.put("sonuc", kullanici.getId()+" Kullanıcı Eklendi.");
				} else {
					model.put("kullanici", null);
					model.put("sonuc", "Kullanıcı İsmi Zaten Var. Eklenemedi.");
				}
				model.put("eklenenKullanici", kullaniciEklenecek);
				model.put("donemler",donemServis.listDonemler());
					
				return "kullaniciJsp/kullaniciekle";
			}
		 @RequestMapping(value = "/kullanicisil", method = RequestMethod.POST)
		 public @ResponseBody JSONObject kullaniciSil(@RequestParam(value="kullaniciId", required=true) Long kullaniciId,HttpSession session) {
			 JSONObject jsonObject=new JSONObject();
			 Integer sonuc=0;
			 if (!Genel.oturumKapali(session)) {				 
				 Kullanici aktifKullanici=Genel.getKullanici(session);
				 if(aktifKullanici.getSelahiyet()){
					 Kullanici kullanici=(Kullanici)islemServis.getir(kullaniciId, Kullanici.class);
					 if(kullanici!=null){
						 islemServis.calistir("update FROM EvradOkuma set kaydedenKullanici=null WHERE kaydedenKullanici="+kullaniciId);
						 islemServis.calistir("update FROM KitabOkuma set kaydedenKullanici=null WHERE kaydedenKullanici="+kullaniciId);
						 islemServis.calistir("delete FROM Girisler WHERE kullanici="+kullaniciId);
						 sonuc = islemServis.calistir("delete FROM EvradOkuma WHERE evradAbone IN (SELECT id FROM EvradAbone WHERE kullanici="+kullaniciId+")");
						 sonuc += islemServis.calistir("delete FROM KitabOkuma WHERE kitabAbone IN (SELECT id FROM KitabAbone WHERE kullanici="+kullaniciId+")");
						 islemServis.calistir("delete FROM EvradAbone WHERE kullanici="+kullaniciId);
						 islemServis.calistir("delete FROM KitabAbone WHERE kullanici="+kullaniciId);
						 islemServis.calistir("delete FROM Kullanici WHERE id="+kullaniciId);
					 }
				 }
			 }
			 jsonObject.put("silinenOkumaSayisi", sonuc);
			 return jsonObject;
		 }
}
