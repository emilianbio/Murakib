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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DonemServis;
import service.EvradServis;
import service.IslemServis;
import service.KitabServis;
import service.KullaniciServis;
import araclar.Genel;
import forms.Donem;
import forms.KitabAbone;
import forms.KitabOkuma;
import forms.Kullanici;
@Controller
public class KitabController {
//	@Autowired
//	private KullaniciServis kullaniciServis;	
	@Autowired
	private IslemServis islemServis;
	@Autowired
	private EvradServis evradServis;
	@Autowired
	private DonemServis donemServis;
	@Autowired
	private KitabServis kitabServis;
	@Autowired
	private KullaniciServis kullaniciServis;

	@RequestMapping(value = "/kitabokumakaydet", method = RequestMethod.POST)
	 public @ResponseBody String kitabOkumaKaydet(@RequestParam(value="kitabOkumaId", required=true) Long kitabOkumaId,@RequestParam(value="kitab", required=true) String kitab,@RequestParam(value="ilkSayfa", required=true) Short ilkSayfa,@RequestParam(value="sonSayfa", required=true) Short sonSayfa,HttpSession session,Map<String, Object> map) {
//		 if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		 Kullanici aktifKullanici=Genel.getKullanici(session);
		 KitabOkuma kitabOkuma = (KitabOkuma)islemServis.getir(kitabOkumaId, KitabOkuma.class);
		 kitabOkuma.setKitab(kitab);
		 kitabOkuma.setIlkSayfa(ilkSayfa);
		 kitabOkuma.setSonSayfa(sonSayfa);
		 if(aktifKullanici!=null) kitabOkuma.setKaydedenKullanici(aktifKullanici);
		 kitabOkuma.setKayitTarihi(new Date());
		 islemServis.kaydet(kitabOkuma);
		 Donem donem=(Donem) session.getAttribute("donem"); 
		 JSONObject jsonObject=new JSONObject();
//		 Map sonuc =new HashMap<>();		 
		 jsonObject.put("okunan",kitabServis.donemIciOkunanSayfaSayisi(donem,kitabOkuma.getKitabAbone()));
		 jsonObject.put("okunmasiGereken",Genel.kullanilacakGunSayisi(donem, kitabOkuma.getKitabAbone().getBaslamaTarihi())*kitabOkuma.getKitabAbone().getGunlukSayfaSayisi());
		 jsonObject.put("gunlukSayfa",kitabOkuma.getKitabAbone().getGunlukSayfaSayisi());
//		 jsonObject.put("sonuc", sonuc);
		 return jsonObject.toJSONString();
	 }
	@RequestMapping(value = "/kitab")
	public String kitab(HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
		Donem donem=(Donem)session.getAttribute("donem");
		Date bugun=Genel.sadeceTarih(new Date());
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		if(donem!=null){
	//		System.out.println(donem.getBaslamaTarihi());
			long donemGunSayisi=Genel.gunSayisi(donem);
			long gunSayisi = donemGunSayisi;
			List kitabAboneListesi = kullaniciServis.kitabAboneligi(aktifKullanici.getId(),false);
			if(kitabAboneListesi!=null && kitabAboneListesi.size()>0){
				Iterator<KitabAbone> iteratorKitabAbone=kitabAboneListesi.iterator();
				ArrayList<Object> kitabOkumaListesi=new ArrayList<>();
				while (iteratorKitabAbone.hasNext()) {
					KitabAbone kitabAbone=iteratorKitabAbone.next();
					long abonelikGunSayisi = Genel.gunFarki(bugun, kitabAbone.getBaslamaTarihi())+1;
					if (abonelikGunSayisi<donemGunSayisi) gunSayisi=abonelikGunSayisi;
					kitabServis.buguneKitabOkumaEkle(kitabAbone);
					KitabOkuma kitabOkuma=new KitabOkuma();
					kitabOkuma.setKitabAbone(kitabAbone);
					kitabOkuma.setIlkSayfa(kitabServis.donemIciOkunanSayfaSayisi(donem,kitabAbone).shortValue());
					Long okunmasiGereken=gunSayisi*kitabAbone.getGunlukSayfaSayisi();
					kitabOkuma.setSonSayfa(okunmasiGereken.shortValue());
					kitabOkumaListesi.add(kitabOkuma);
					kitabOkumaListesi.addAll(kitabServis.kitabOkumaListesi(kitabAbone, donem, null,null));
				}
				model.put("kitabOkumaListesi", kitabOkumaListesi);
			}
		}
		model.put("donemler",donemServis.listDonemler());
		model.put("kullanici",aktifKullanici);
		return "kitabJsp/kitab";
	}
	
	@RequestMapping(value = "/kitab/{id}")
	public String kitabId(@PathVariable("id") Long aboneId,HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici aktifKullanici=(Kullanici)session.getAttribute("userInfo");
		Donem donem=(Donem)session.getAttribute("donem");
		Date bugun=Genel.sadeceTarih(new Date());
		if(donem==null) {
			donem=donemServis.gecerliDonemiGetir();
			session.setAttribute("donem", donem);
		}
		if(donem!=null){
			ArrayList<Object> kitabOkumaListesi=new ArrayList<>();
	//		System.out.println(donem.getBaslamaTarihi());
			long donemGunSayisi=Genel.gunSayisi(donem);
			long gunSayisi = donemGunSayisi;
			KitabAbone kitabAbone=(KitabAbone)islemServis.getir(aboneId, KitabAbone.class);
			long abonelikGunSayisi = Genel.gunFarki(bugun, kitabAbone.getBaslamaTarihi())+1;
			if (abonelikGunSayisi<donemGunSayisi) gunSayisi=abonelikGunSayisi;
			kitabServis.buguneKitabOkumaEkle(kitabAbone);
			KitabOkuma kitabOkuma=new KitabOkuma();
			kitabOkuma.setKitabAbone(kitabAbone);
			kitabOkuma.setIlkSayfa(kitabServis.donemIciOkunanSayfaSayisi(donem,kitabAbone).shortValue());
			Long okunmasiGereken=gunSayisi*kitabAbone.getGunlukSayfaSayisi();
			kitabOkuma.setSonSayfa(okunmasiGereken.shortValue());
			kitabOkumaListesi.add(kitabOkuma);
			kitabOkumaListesi.addAll(kitabServis.kitabOkumaListesi(kitabAbone, donem, null,null));			
			model.put("kitabOkumaListesi", kitabOkumaListesi);
		}
		model.put("donemler",donemServis.listDonemler());
		model.put("kullanici",aktifKullanici);
		return "kitabJsp/kitab";
	}
	@RequestMapping(value = "/kitabokumasil", method = RequestMethod.POST)
	 public @ResponseBody JSONObject kitabOkumaSil(@RequestParam(value="okumaId", required=true) Long okumaId,HttpSession session) {
		JSONObject jsonObject=new JSONObject();
		 if (!Genel.oturumKapali(session)) {
			 jsonObject.put("silinenOkumaSayisi", islemServis.calistir("delete FROM KitabOkuma WHERE id="+okumaId));
		 }
		 return jsonObject;
	 }
}
