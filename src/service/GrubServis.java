package service;

import java.util.List;

import forms.Donem;
import forms.Grub;

public interface GrubServis {
	public List<Object> grubListesi(Grub ustGrub);
	public Grub grubGuncelle(Long grubId,String isim,String baslama,String bitis,Short sure);
}
