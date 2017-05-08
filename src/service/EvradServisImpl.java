package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.EvradDAO;
import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.EvradOkuma;
import forms.Donem;

@Service
public class EvradServisImpl implements EvradServis{
//	@Autowired
//	private IslemDAO islemDAO;
	@Autowired
	private EvradDAO evradDAO;
		
	
	@Transactional
	public List<EvradOkuma> evradOkumaListesi(EvradAbone evradAbone,Donem donem,Integer kayitSayisi,Integer ilkKayitSirasi) {
		return evradDAO.evradOkumaListesi(evradAbone, donem, kayitSayisi,ilkKayitSirasi);
	}

	@Transactional
	public List evradKisimListesi(Long evradBolumlemeId,Long evradAboneId,Long grubId) {
		return evradDAO.evradKisimListesi(evradBolumlemeId,evradAboneId,grubId);
	}

	@Transactional
	public void evradOkumaEkle(EvradAbone evradAbone,Date tarih) {
		evradDAO.evradOkumaEkle(evradAbone, tarih);
	}

	@Transactional
	public void bosGunleriDoldur(EvradAbone evradAbone) {
		evradDAO.bosGunleriDoldur(evradAbone);
	}

	@Transactional
	public List evradBolumlemeListesi() {
		return evradDAO.evradBolumlemeListesi();
	}

	@Transactional
	public Long evradHulasa(EvradAbone evradAbone, Donem donem, Boolean hepsi) {
		return evradDAO.evradHulasa(evradAbone, donem, hepsi);
	}

	@Transactional
	public EvradKisim okunacakEvradKismi(EvradAbone evradAbone, Date tarih) {
		return evradDAO.okunacakEvradKismi(evradAbone, tarih);
	}

	@Transactional
	public List evradAbonelistesi(Boolean pasif) {
		return evradDAO.evradAbonelistesi(false);
	}

	
}
