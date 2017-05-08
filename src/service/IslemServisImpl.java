package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.IslemDAO;

@Service
public class IslemServisImpl implements IslemServis{
	@Autowired
	private IslemDAO islemDAO;

	@Transactional
	public void kaydet(Object nesne) {
		islemDAO.kaydet(nesne);
	}

	@Transactional
	public List<Object> listeNesne(String sorgu) {
		return islemDAO.listeNesne(sorgu);
	}

	@Transactional
	public void sil(Long id, Class sinif) {
		islemDAO.sil(id, sinif);		
	}

	@Transactional
	public Object getir(Long id, Class sinif) {
		return islemDAO.getir(id, sinif);
	}

	@Transactional
	public Integer calistir(String sorgu) {
		return islemDAO.calistir(sorgu);
	}
}
