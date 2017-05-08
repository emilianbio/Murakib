package dao;

import java.util.List;

import forms.Grub;

public interface GrubDAO {
	public List<Object> grubListesi(Grub ustGrub);
	public Grub grubGuncelle(Long grubId,String isim,String baslama,String bitis,Short sure);
}
