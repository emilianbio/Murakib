package service;

import java.util.Date;
import java.util.List;

import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.EvradOkuma;
import forms.Donem;
import forms.KitabAbone;
import forms.KitabOkuma;

public interface EvradServis {
	public List<EvradOkuma> evradOkumaListesi(EvradAbone evradAbone,Donem donem,Integer kayitSayisi,Integer ilkKayitSirasi);
	public List evradKisimListesi(Long evradBolumlemeId,Long evradAboneId,Long grubId);
	public void evradOkumaEkle(EvradAbone evradAbone,Date tarih);
	public void bosGunleriDoldur(EvradAbone evradAbone);
	public List evradBolumlemeListesi();
	public Long evradHulasa(EvradAbone evradAbone,Donem donem,Boolean hepsi);
	public EvradKisim okunacakEvradKismi(EvradAbone evradAbone,Date tarih);
	public List evradAbonelistesi(Boolean pasif);
}
