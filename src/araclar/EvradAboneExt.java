package araclar;

import forms.EvradAbone;

public class EvradAboneExt extends EvradAbone{
	private Long sonuc;
	private Long gereken;
	public EvradAboneExt(){
		
	}
	public EvradAboneExt(EvradAbone evradAbone){
		this.setId(evradAbone.getId());
		this.setAciklama(evradAbone.getAciklama());
		this.setBaslamaTarihi(evradAbone.getBaslamaTarihi());
		this.setEvradKisim(evradAbone.getEvradKisim());
		this.setGrub(evradAbone.getGrub());
		this.setKaydedenKullanici(evradAbone.getKaydedenKullanici());
		this.setKayitTarihi(evradAbone.getKayitTarihi());
		this.setKullanici(evradAbone.getKullanici());
		this.setPasif(evradAbone.getPasif());
		this.setHususi(evradAbone.getHususi());
		this.setParcaSuresi(evradAbone.getParcaSuresi());
		this.setSabit(evradAbone.getSabit());
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
