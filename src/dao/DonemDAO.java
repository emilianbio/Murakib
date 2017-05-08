package dao;

import java.util.List;

import forms.Donem;

public interface DonemDAO {
	public void kaydet(Object nesne);
	public List<Object> listDonemler();
	public  Donem gecerliDonemiGetir();
}
