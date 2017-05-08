package service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import forms.Grub;
import forms.EvradAbone;
import forms.KitabAbone;
import forms.Kullanici;

public interface KullaniciServis {
	@Transactional
	public Kullanici kullaniciGetir(Kullanici kullanici,Boolean sifresiz);
	public void girisEkle(Kullanici kullanici,Date tarih, String ipNo);
	public List kitabAboneligi(Long kullaniciId,Boolean hepsi);
	public List evradAboneligi(Long kullaniciId,Long grubId,Long bolumId,Boolean hepsi);
	public List<Kullanici> kullaniciListesi(Kullanici kullanici,Grub grub,Kullanici aktifKullanici);
	public Boolean kullaniciVarMi(String kullaniciIsmi);
}
