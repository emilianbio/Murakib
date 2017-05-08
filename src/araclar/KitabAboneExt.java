package araclar;


import forms.KitabAbone;

public class KitabAboneExt extends KitabAbone{
	private Long sonuc;
	private Long gereken;
	public KitabAboneExt(){
		
	}
	public KitabAboneExt(KitabAbone kitabAbone){
		this.setId(kitabAbone.getId());
		this.setAciklama(kitabAbone.getAciklama());
		this.setBaslamaTarihi(kitabAbone.getBaslamaTarihi());
		this.setGunlukSayfaSayisi(kitabAbone.getGunlukSayfaSayisi());
		this.setKaydedenKullanici(kitabAbone.getKaydedenKullanici());
		this.setKayitTarihi(kitabAbone.getKayitTarihi());
		this.setKullanici(kitabAbone.getKullanici());
		this.setPasif(kitabAbone.getPasif());
		this.setHususi(kitabAbone.getHususi());
	}
	public Long getSonuc() {
		return sonuc;
	}
	public void setSonuc(Long sonuc) {
		this.sonuc = sonuc;
	}
	public Long getGereken() {
		return gereken;
	}
	public void setGereken(Long gereken) {
		this.gereken = gereken;
	}
	
}
