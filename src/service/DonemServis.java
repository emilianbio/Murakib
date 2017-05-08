package service;

import java.util.List;

import forms.Donem;

public interface DonemServis {
	public void kaydet(Object nesne);
	public List<Object> listDonemler();
	public  Donem gecerliDonemiGetir();
}
