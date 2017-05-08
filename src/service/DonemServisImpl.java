package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.DonemDAO;
import dao.IslemDAO;
import forms.Donem;

@Service
public class DonemServisImpl implements DonemServis{
	@Autowired
	private IslemDAO islemDAO;
	@Autowired
	private DonemDAO donemDAO;
	
	@Transactional
	public void kaydet(Object nesne) {
		islemDAO.kaydet(nesne);
	}

	@Transactional
	public List<Object> listDonemler() {
		return donemDAO.listDonemler();
	}

	@Transactional
	public  Donem gecerliDonemiGetir() {
		return donemDAO.gecerliDonemiGetir();
	}
}
