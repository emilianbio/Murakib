package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.GrubDAO;
import dao.IslemDAO;
import forms.Grub;

@Service
public class GrubServisImpl implements GrubServis{
	@Autowired
	private IslemDAO islemDAO;
	@Autowired
	private GrubDAO grubDAO;
	
	@Transactional
	public List<Object> grubListesi(Grub ustGrub) {
		return grubDAO.grubListesi(ustGrub);
	}

	@Transactional
	public Grub grubGuncelle(Long grubId, String isim, String baslama,
			String bitis, Short sure) {
		return grubDAO.grubGuncelle(grubId, isim, baslama, bitis, sure);
	}
}
