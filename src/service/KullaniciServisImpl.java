package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import forms.Grub;
import dao.KullaniciDAO;
import forms.KitabAbone;
import forms.Kullanici;
@Service
public class KullaniciServisImpl implements KullaniciServis{

	@Autowired
	KullaniciDAO kullaniciDAO;
	
	@Transactional
	public Kullanici kullaniciGetir(Kullanici kullanici,Boolean sifresiz) {
		return kullaniciDAO.kullaniciGetir(kullanici, sifresiz);
		
	}

	@Transactional
	public void girisEkle(Kullanici kullanici, Date tarih, String ipNo) {
		kullaniciDAO.girisEkle(kullanici, tarih, ipNo);
	}

	@Transactional
	public List kitabAboneligi(Long kullaniciId,Boolean hepsi) {
		return kullaniciDAO.kitabAboneligi(kullaniciId,hepsi);
	}

	@Transactional
	public List evradAboneligi(Long kullanici,Long grubId,Long bolumId,Boolean hepsi) {
		return kullaniciDAO.evradAboneligi(kullanici,grubId,bolumId,hepsi);
	}

	@Transactional
	public List<Kullanici> kullaniciListesi(Kullanici kullanici,Grub grub,Kullanici aktifKullanici) {
		return kullaniciDAO.kullaniciListesi(kullanici,grub,aktifKullanici);
	}

	@Transactional
	public Boolean kullaniciVarMi(String kullaniciIsmi) {
		return kullaniciDAO.kullaniciVarMi(kullaniciIsmi);
	}
}
