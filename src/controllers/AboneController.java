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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import araclar.EvradAboneExt;
import araclar.Genel;
import araclar.KitabAboneExt;
import forms.Donem;
import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.Grub;
import forms.KitabAbone;
import forms.Kullanici;
@Controller
public class AboneController {
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
	
	@RequestMapping(value = "/abonelikler/{id}")
	public String abonelikler(@PathVariable("id") Long kullaniciId,HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici aktifKullanici=Genel.getKullanici(session);
		Kullanici kullanici=(Kullanici)islemServis.getir(kullaniciId, Kullanici.class);
		Donem donem=(Donem)session.getAttribute("donem");
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		model.put("kitabAboneListesi",kitabAboneGenislet(kullaniciServis.kitabAboneligi(kullaniciId,true),donem,aktifKullanici));
		model.put("evradAboneListesi", evradAboneGenislet(kullaniciServis.evradAboneligi(kullaniciId,null,null,true),donem,aktifKullanici));
//		model.put("kullanici", kullanici);
		Grub grub=new Grub();
		grub.setGrub(kullanici.getGrub());
		model.put("donemler",donemServis.listDonemler());
		model.put("grublar", grubServis.grubListesi(grub));
		model.put("evradBolumleme", evradServis.evradBolumlemeListesi());
		return "aboneJsp/abonelikler";
	 }
	@RequestMapping(value = "/bosgunleridoldur/{id}")
	public String bosGunleriDoldur(@PathVariable("id") Long evradAboneId,HttpSession session, ModelMap model){
		EvradAbone evradAbone=(EvradAbone) islemServis.getir(evradAboneId, EvradAbone.class);
		evradServis.bosGunleriDoldur(evradAbone);
		return "redirect:/abonelikler/"+evradAbone.getKullanici().getId();
	}
	@RequestMapping(value = "/grubabonelikler/{id}")
	public String grubAbonelikler(@PathVariable("id") Long grubId,HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici aktifKullanici=Genel.getKullanici(session);
		Grub grub=(Grub)islemServis.getir(grubId, Grub.class);
		Donem donem=(Donem)session.getAttribute("donem");
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		model.put("evradAboneListesi", evradAboneGenislet(kullaniciServis.evradAboneligi(null,grubId,null,true),donem,aktifKullanici));
//		model.put("kullanici", kullanici);
		model.put("donemler",donemServis.listDonemler());
		return "aboneJsp/abonelikler";
	 }
	@RequestMapping(value = "/bolumabonelikler/{id}")
	public String bolumAbonelikler(@PathVariable("id") Long bolumId,HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici aktikKullanici=Genel.getKullanici(session);
		Donem donem=(Donem)session.getAttribute("donem");
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		model.put("evradAboneListesi", evradAboneGenislet(kullaniciServis.evradAboneligi(aktikKullanici.getId() ,null,bolumId,true),donem,aktikKullanici));
//		model.put("kullanici", kullanici);
		model.put("donemler",donemServis.listDonemler());
		return "aboneJsp/abonelikler";
	 }
	@RequestMapping(value = "/kitababonekaydet", method = RequestMethod.POST)
	 public @ResponseBody void kitabAboneKaydet(@RequestParam(value="kitabAboneId", required=true) Long kitabAboneId,@RequestParam(value="baslama", required=true) String baslama,@RequestParam(value="sayfaSayisi", required=true) Short sayfaSayisi,@RequestParam(value="aciklama", required=true) String aciklama,HttpSession session,Map<String, Object> map) {
		 if (!Genel.oturumKapali(session)) {
			 Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			 KitabAbone kitabAbone = (KitabAbone)islemServis.getir(kitabAboneId, KitabAbone.class);
			 kitabAbone.setAciklama(aciklama);
			 kitabAbone.setGunlukSayfaSayisi(sayfaSayisi);
//			 System.out.println(aktifKullanici.getSelahiyet());
//			 System.out.println(kitabAbone.getKaydedenKullanici().getId()==aktifKullanici.getId());
			 if(aktifKullanici.getSelahiyet() || kitabAbone.getKaydedenKullanici().getId()==aktifKullanici.getId()){
//				 System.out.println("burda");
				 kitabAbone.setBaslamaTarihi(Genel.tariheCevir(baslama));
			 }
//			 kitabAbone.setKaydedenKullanici(aktifKullanici);
//			 kitabAbone.setKayitTarihi(new Date());
			 islemServis.kaydet(kitabAbone);			 
		 }
	 }
	@RequestMapping(value = "/evradabonekaydet", method = RequestMethod.POST)
	 public @ResponseBody void evradAboneKaydet(@RequestParam(value="evradAboneId", required=true) Long evradAboneId,@RequestParam(value="baslama", required=true) String baslama,@RequestParam(value="aciklama", required=true) String aciklama,@RequestParam(value="sure", required=true) Short sure,HttpSession session) {
		 if (!Genel.oturumKapali(session)) {
			 Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			 EvradAbone evradAbone = (EvradAbone)islemServis.getir(evradAboneId, EvradAbone.class);
			 evradAbone.setAciklama(aciklama);
			 evradAbone.setParcaSuresi(sure);
			 if(aktifKullanici.getSelahiyet() || evradAbone.getKaydedenKullanici().getId()==aktifKullanici.getId())
				 evradAbone.setBaslamaTarihi(Genel.tariheCevir(baslama));
//			 evradAbone.setKaydedenKullanici(aktifKullanici);
//			 evradAbone.setKayitTarihi(new Date());
			 islemServis.kaydet(evradAbone);			 
		 }
	 }
	@RequestMapping(value = "/kitababoneaktifpasif", method = RequestMethod.POST)
	 public @ResponseBody void kitabAboneAktifPasif(@RequestParam(value="aboneId", required=true) Long aboneId,Model model,HttpSession session) {
//		 if (!Genel.oturumKapali(session)) {
//			 Kullanici aktifKullanici=Genel.getKullanici(session);
			 KitabAbone kitabAbone=(KitabAbone)islemServis.getir(aboneId, KitabAbone.class);
			 if(kitabAbone!=null){
				 if(kitabAbone.getPasif()==null) kitabAbone.setPasif(false);
//				 kitabAbone.setKaydedenKullanici(aktifKullanici);
//				 kitabAbone.setKayitTarihi(new Date());
				 kitabAbone.setPasif(!kitabAbone.getPasif());
			 	 islemServis.kaydet(kitabAbone);
			 }
//		 }
	 }
	@RequestMapping(value = "/kitababonehususi", method = RequestMethod.POST)
	 public @ResponseBody void kitabAboneHususi(@RequestParam(value="aboneId", required=true) Long aboneId,Model model,HttpSession session) {
//		 if (!Genel.oturumKapali(session)) {
//			 Kullanici aktifKullanici=Genel.getKullanici(session);
			 KitabAbone kitabAbone=(KitabAbone)islemServis.getir(aboneId, KitabAbone.class);
			 if(kitabAbone!=null){
				 if(kitabAbone.getHususi()==null) kitabAbone.setHususi(false);
//				 kitabAbone.setKaydedenKullanici(aktifKullanici);
//				 kitabAbone.setKayitTarihi(new Date());
				 kitabAbone.setHususi(!kitabAbone.getHususi());
			 	 islemServis.kaydet(kitabAbone);
			 }
//		 }
	 }
	
	@RequestMapping(value = "/evradaboneaktifpasif", method = RequestMethod.POST)
	 public @ResponseBody void evradAboneAktifPasif(@RequestParam(value="aboneId", required=true) Long aboneId,Model model,HttpSession session) {
//		 if (!Genel.oturumKapali(session)) {
//			 Kullanici aktifKullanici=Genel.getKullanici(session);
			 EvradAbone evradAbone=(EvradAbone)islemServis.getir(aboneId, EvradAbone.class);
			 if(evradAbone!=null){
				 if(evradAbone.getPasif()==null) evradAbone.setPasif(false);
//				 evradAbone.setKaydedenKullanici(aktifKullanici);
//				 evradAbone.setKayitTarihi(new Date());
				 evradAbone.setPasif(!evradAbone.getPasif());
				 
			 	 islemServis.kaydet(evradAbone);
			 }
//		 }
	 }
	@RequestMapping(value = "/evradabonehususi", method = RequestMethod.POST)
	 public @ResponseBody void evradAboneHususi(@RequestParam(value="aboneId", required=true) Long aboneId,Model model,HttpSession session) {
//		 if (!Genel.oturumKapali(session)) {
//			 Kullanici aktifKullanici=Genel.getKullanici(session);
			 EvradAbone evradAbone=(EvradAbone)islemServis.getir(aboneId, EvradAbone.class);
			 if(evradAbone!=null){
				 if(evradAbone.getHususi()==null) evradAbone.setHususi(false);
//				 evradAbone.setKaydedenKullanici(aktifKullanici);
//				 evradAbone.setKayitTarihi(new Date());
				 evradAbone.setHususi(!evradAbone.getHususi());
			 	 islemServis.kaydet(evradAbone);
			 }
//		 }
	 }
	@RequestMapping(value = "/evradabonesabit", method = RequestMethod.POST)
	 public @ResponseBody void evradAboneSabit(@RequestParam(value="aboneId", required=true) Long aboneId,HttpSession session) {
			 EvradAbone evradAbone=(EvradAbone)islemServis.getir(aboneId, EvradAbone.class);
			 if(evradAbone!=null){
				 if(evradAbone.getSabit()==null) evradAbone.setSabit(false);
				 evradAbone.setSabit(!evradAbone.getSabit());
			 	 islemServis.kaydet(evradAbone);
			 }
//		 }
	 }
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/grubunevradi", method = RequestMethod.POST)
	 public @ResponseBody JSONObject grubunEvradi(@RequestParam(value="grubId", required=true) Long grubId,Model model,HttpSession session) {
		 JSONObject jsonObject=new JSONObject();
		 Grub grub=(Grub)islemServis.getir(grubId, Grub.class);
		 if(grub!=null){
			 jsonObject.put("evradBolumlemeId", grub.getEvradBolumleme().getId());
			 jsonObject.put("grubParcaSuresi", grub.getParcaSuresi());
			 jsonObject.put("grubBaslama", Genel.tarihStringe(grub.getBaslamaTarihi()));
		 } else jsonObject.put("evradBolumlemeId", 0);
		return jsonObject;
	 }
	@RequestMapping(value = "/evradaboneekle")
	 public ModelAndView evradAboneEkle(HttpSession session) {
		 if (Genel.oturumKapali(session)) return  new ModelAndView("redirect:/kullanicigirisi");
		 Kullanici aktifKullanici=Genel.getKullanici(session);
		 ModelMap model=evradAboneEkleHazirla(null, aktifKullanici);
//		 if (aktifKullanici.getSelahiyet()==1){
			 return new ModelAndView("aboneJsp/evradaboneekle",model);
//		 }
//		return  new ModelAndView("redirect:/kullanicigirisi");
	 }
	@RequestMapping(value = "/yenievradabonekaydet", method = RequestMethod.POST)
	public ModelAndView yeniEvradAboneKaydet(@ModelAttribute("eklenenEvradAbone") EvradAbone evradAbone,HttpSession session){
		if (Genel.oturumKapali(session)) return new ModelAndView("redirect:/kullanicigirisi");
		Kullanici aktifKullanici=Genel.getKullanici(session);
		if(evradAbone.getGrub()!=null && evradAbone.getEvradKisim()==null){
			List listeEvradKisim=evradServis.evradKisimListesi(evradAbone.getGrub().getEvradBolumleme().getId(), null, null);
			if(listeEvradKisim!=null && listeEvradKisim.size()>0) evradAbone.setEvradKisim((EvradKisim) listeEvradKisim.get(0));
		}
		evradAbone.setKaydedenKullanici(aktifKullanici);
		evradAbone.setKayitTarihi(new Date());
		if(evradAbone.getBaslamaTarihi()==null) evradAbone.setBaslamaTarihi(new Date());
		if(evradAbone.getGrub().getId()==0) evradAbone.setGrub(null);
		evradAbone.setPasif(false);
		if(evradAbone.getParcaSuresi()==null || evradAbone.getParcaSuresi()==0) evradAbone.setParcaSuresi((short)1);
		evradAbone.setHususi(!aktifKullanici.getSelahiyet());
		islemServis.kaydet(evradAbone);
		evradAbone=(EvradAbone)islemServis.getir(evradAbone.getId(), EvradAbone.class);
		evradServis.bosGunleriDoldur(evradAbone);
		ModelMap model=evradAboneEkleHazirla(evradAbone, aktifKullanici);
		model.put("sonuc", evradAbone.getId()+" Abone Eklendi");
				
		return new ModelAndView("aboneJsp/evradaboneekle",model);
	}
	
	@RequestMapping(value = "/kitababoneekle")
	 public ModelAndView kitabAboneEkle(HttpSession session) {
		 if (Genel.oturumKapali(session)) return  new ModelAndView("redirect:/kullanicigirisi");
		 Kullanici aktifKullanici=Genel.getKullanici(session);
		 ModelMap model=kitabAboneEkleHazirla(null, aktifKullanici);
//		 if (aktifKullanici.getSelahiyet()){
			 return new ModelAndView("aboneJsp/kitababoneekle",model);
//		 }
//		return  new ModelAndView("redirect:/kullanicigirisi");
	 }
	@SuppressWarnings("rawtypes")
	private ModelMap evradAboneEkleHazirla(EvradAbone evradAbone,Kullanici aktifKullanici){
		List bolumlemeListesi=evradServis.evradBolumlemeListesi();
		Long bolumlemeId = 0L;
		if(evradAbone==null){
			evradAbone=new EvradAbone();
			bolumlemeId=((EvradBolumleme)bolumlemeListesi.get(0)).getId();
			evradAbone.setParcaSuresi((short)1);
			evradAbone.setBaslamaTarihi(new Date());
		} else {
			EvradKisim evradKisim=(EvradKisim)islemServis.getir(evradAbone.getEvradKisim().getId(), EvradKisim.class);
			bolumlemeId = evradKisim.getEvradBolumleme().getId();
		}
		Grub grub1=null;
		if(evradAbone.getGrub()!=null) grub1=(Grub) islemServis.getir(evradAbone.getGrub().getId(), Grub.class);
		ModelMap model=new ModelMap();
		Grub grub = new Grub();
		grub.setGrub(aktifKullanici.getGrub());
		ArrayList<Object> grubListesi=new ArrayList<>();
		grubListesi.add(grub);
		grubListesi.addAll(grubServis.grubListesi(grub));
		if(grub1!=null) evradAbone.setParcaSuresi(grub1.getParcaSuresi());
		model.put("kullaniciListesi", kullaniciServis.kullaniciListesi(null, aktifKullanici.getGrub(),aktifKullanici));
		model.put("grubListesi",grubListesi );
		model.put("eklenenEvradAbone", evradAbone);
		model.put("bolumlemeListesi", bolumlemeListesi);
		Long grubId=null;
		if(evradAbone.getGrub()!=null) grubId=evradAbone.getGrub().getId();
		model.put("kisimListesi", evradServis.evradKisimListesi(bolumlemeId, null,grubId));
		model.put("donemler",donemServis.listDonemler());
		return model;
		
	}
	private ModelMap kitabAboneEkleHazirla(KitabAbone kitabAbone,Kullanici aktifKullanici){
		ModelMap model = new ModelMap();
		if(kitabAbone==null) {
			kitabAbone=new KitabAbone();
			kitabAbone.setBaslamaTarihi(new Date());
		}
		model.put("kullaniciListesi", kullaniciServis.kullaniciListesi(null, aktifKullanici.getGrub(),aktifKullanici));
		model.put("eklenenKitabAbone", kitabAbone);
		model.put("donemler",donemServis.listDonemler());
		return model;
		
	}
	@RequestMapping(value = "/yenikitababonekaydet", method = RequestMethod.POST)
	public ModelAndView yeniKitabAboneKaydet(@ModelAttribute("eklenenKitabAbone") KitabAbone kitabAbone,HttpSession session){
		if (Genel.oturumKapali(session)) return new ModelAndView("redirect:/kullanicigirisi");
		Kullanici aktifKullanici=Genel.getKullanici(session);
		kitabAbone.setKaydedenKullanici(aktifKullanici);
		kitabAbone.setKayitTarihi(new Date());
		if(kitabAbone.getBaslamaTarihi()==null) kitabAbone.setBaslamaTarihi(new Date());
		kitabAbone.setPasif(false);
		if(kitabAbone.getGunlukSayfaSayisi()==null) kitabAbone.setGunlukSayfaSayisi((short)5);
		if(aktifKullanici.getSelahiyet()==null)aktifKullanici.setSelahiyet(false);
		kitabAbone.setHususi(!aktifKullanici.getSelahiyet());
		islemServis.kaydet(kitabAbone);
		ModelMap model=kitabAboneEkleHazirla(kitabAbone, aktifKullanici);
		model.put("sonuc", kitabAbone.getId()+" Abone Eklendi");
		return new ModelAndView("aboneJsp/kitababoneekle",model);
	}
	private List evradAboneGenislet(List evradAboneListesi,Donem donem,Kullanici aktifKullanici){
		ArrayList<EvradAboneExt> extListe=new ArrayList<>();
		if(evradAboneListesi==null) return extListe;
		Iterator<EvradAbone> iteratorEvradAbone=evradAboneListesi.iterator();
		while(iteratorEvradAbone.hasNext()){
			EvradAbone evradAbone=iteratorEvradAbone.next();
			if(!evradAbone.getHususi() ||(evradAbone.getKullanici().getId()==aktifKullanici.getId())){
				EvradAboneExt evradAboneExt=new EvradAboneExt(evradAbone);
				evradAboneExt.setGereken(evradServis.evradHulasa(evradAboneExt, donem, true));
				evradAboneExt.setSonuc(evradServis.evradHulasa(evradAboneExt, donem, false));
				extListe.add(evradAboneExt);
			}
		}
		return extListe;
	}
	private List kitabAboneGenislet(List kitabAboneListesi,Donem donem,Kullanici aktifKullanici){
		ArrayList<KitabAboneExt> extListe=new ArrayList<>();
		if(kitabAboneListesi==null) return extListe;
		Iterator<KitabAbone> iteratorKitabAbone=kitabAboneListesi.iterator();
		while(iteratorKitabAbone.hasNext()){
			KitabAbone kitabAbone=iteratorKitabAbone.next();
			if(!kitabAbone.getHususi() ||(kitabAbone.getKullanici().getId()==aktifKullanici.getId())){
				KitabAboneExt kitabAboneExt=new KitabAboneExt(kitabAbone);
				kitabAboneExt.setSonuc(kitabServis.donemIciOkunanSayfaSayisi(donem, kitabAbone));
				kitabAboneExt.setGereken(Genel.kullanilacakGunSayisi(donem, kitabAbone.getBaslamaTarihi())*kitabAbone.getGunlukSayfaSayisi());
				extListe.add(kitabAboneExt);
			}
		}
		return extListe;
	}
	@RequestMapping(value = "/buguneokumalariekle")
	public void buguneOkumalariEkle(HttpSession session){
		Date bugun = Genel.sadeceTarih(new Date());
		List evradAboneListesi=evradServis.evradAbonelistesi(false);
		if(evradAboneListesi!=null && evradAboneListesi.size()>0){
			Iterator<EvradAbone> iteratorEvradAbone = evradAboneListesi.iterator();
			while (iteratorEvradAbone.hasNext()) {
				EvradAbone evradAbone = (EvradAbone) iteratorEvradAbone.next();
				evradServis.evradOkumaEkle(evradAbone, bugun);
			}
		}
		List kitabAboneListesi=kitabServis.kitabAbonelistesi(false);
		if(kitabAboneListesi!=null && kitabAboneListesi.size()>0){
			Iterator<KitabAbone> iteratorKitabAbone=kitabAboneListesi.iterator();
			while (iteratorKitabAbone.hasNext()) {
				KitabAbone kitabAbone = (KitabAbone) iteratorKitabAbone.next();
				kitabServis.buguneKitabOkumaEkle(kitabAbone);
			}
		}
	}
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/kitababonesil", method = RequestMethod.POST)
	 public @ResponseBody JSONObject kitabAboneSil(@RequestParam(value="kitabAboneId", required=true) Long kitabAboneId,HttpSession session) {
		JSONObject jsonObject=new JSONObject();
		Integer silinenOkumaSayisi=0;
		Integer silinenAbonelikSayisi=0;
		 if (!Genel.oturumKapali(session)) {
			 Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			 silinenOkumaSayisi = islemServis.calistir("delete FROM KitabOkuma WHERE kitabAbone="+kitabAboneId);
			 silinenAbonelikSayisi = islemServis.calistir("delete FROM KitabAbone WHERE id="+kitabAboneId);
			 jsonObject.put("silinenOkumaSayisi", silinenOkumaSayisi);
			 jsonObject.put("silinenAbonelikSayisi", silinenAbonelikSayisi);
		 }
		 return jsonObject;
	 }
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/evradabonesil", method = RequestMethod.POST)
	 public @ResponseBody JSONObject evradAboneSil(@RequestParam(value="evradAboneId", required=true) Long evradAboneId,HttpSession session) {
		JSONObject jsonObject=new JSONObject();
		Integer silinenOkumaSayisi=0;
		Integer silinenAbonelikSayisi=0;
		 if (!Genel.oturumKapali(session)) {
			 Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
			 silinenOkumaSayisi = islemServis.calistir("delete FROM EvradOkuma WHERE evradAbone="+evradAboneId);
			 silinenAbonelikSayisi = islemServis.calistir("delete FROM EvradAbone WHERE id="+evradAboneId);
			 jsonObject.put("silinenOkumaSayisi", silinenOkumaSayisi);
			 jsonObject.put("silinenAbonelikSayisi", silinenAbonelikSayisi);
		 }
		 return jsonObject;
	 }
}
