package service;

import java.util.List;

import forms.Donem;
import forms.KitabAbone;
import forms.KitabOkuma;

public interface KitabServis {
	public Long donemIciOkunanSayfaSayisi(Donem donem,KitabAbone kitabAbone);
	public List kitabOkumaListesi(KitabAbone kitabAbone,Donem donem,Integer kayitSayisi,Integer ilkKayitSirasi);
	public void buguneKitabOkumaEkle(KitabAbone kitabAbone);
	public List kitabAbonelistesi(Boolean pasif);
}
