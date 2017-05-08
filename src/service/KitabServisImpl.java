package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.DonemDAO;
import dao.IslemDAO;
import dao.KitabDAO;
import forms.Donem;
import forms.KitabAbone;
import forms.KitabOkuma;

@Service
public class KitabServisImpl implements KitabServis{
//	@Autowired
//	private IslemDAO islemDAO;
	@Autowired
	private KitabDAO kitabDAO;
		
	@Transactional
	public Long donemIciOkunanSayfaSayisi(Donem donem,KitabAbone kitabAbone) {
		return kitabDAO.donemIciOkunanSayfaSayisi(donem,kitabAbone);
	}

	@Transactional
	public List kitabOkumaListesi(KitabAbone kitabAbone,
			Donem donem, Integer kayitSayisi,Integer ilkKayitSirasi) {
		return kitabDAO.kitabOkumaListesi(kitabAbone, donem, kayitSayisi,ilkKayitSirasi);
	}

	@Transactional
	public void buguneKitabOkumaEkle(KitabAbone kitabAbone) {
		kitabDAO.buguneKitabOkumaEkle(kitabAbone);
	}

	@Transactional
	public List kitabAbonelistesi(Boolean pasif) {
		return kitabDAO.kitabAbonelistesi(pasif);
	}
}
