package controllers;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
import araclar.Genel;
import forms.Donem;
import forms.EvradAbone;
import forms.EvradKisim;
import forms.KitabAbone;
import forms.KitabOkuma;
import forms.Kullanici;
@Controller
public class YonetimController {
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

	@RequestMapping(value = "/yonetim")
	public String yonetim(HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici kullanici=Genel.getKullanici(session);
		model.put("donemler",donemServis.listDonemler());
		return "yonetimJsp/yonetim";
	}
	
	
	@RequestMapping(value = "/evradisleri")
	public String evradIsleri(HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici kullanici=Genel.getKullanici(session);
		
		model.put("donemler",donemServis.listDonemler());
		return "yonetimJsp/evradisleri";
	}
	@RequestMapping(value = "/raporisleri")
	public String raporIsleri(HttpSession session, ModelMap model){
		if (Genel.oturumKapali(session)) return "redirect:/kullanicigirisi";
		Kullanici kullanici=Genel.getKullanici(session);
		
		model.put("donemler",donemServis.listDonemler());
		return "yonetimJsp/raporisleri";
	}
	}
