package dao;

import java.util.Date;
import java.util.List;

import forms.EvradAbone;
import forms.Grub;
import forms.KitabAbone;
import forms.Kullanici;
public interface KullaniciDAO {
	public Kullanici kullaniciGetir(Kullanici kullanici,Boolean sifresiz);
	public Boolean kullaniciVarMi(String kullaniciIsmi);
	public List<Kullanici> kullaniciListesi(Kullanici kullanici,Grub grub,Kullanici aktifKullanici);
	public void girisEkle(Kullanici kullanici,Date tarih, String ipNo);
	public List kitabAboneligi(Long kullaniciId,Boolean hepsi);
	public List evradAboneligi(Long kullaniciId,Long grubId,Long bolumId,Boolean hepsi);
}
