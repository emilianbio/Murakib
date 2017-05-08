package dao;

import java.util.List;

public interface IslemDAO {
	public void kaydet(Object nesne);
	public List<Object> listeNesne(String sorgu);
	public Integer calistir(String sorgu);
	public void sil(Long id,Class sinif);
	public Object getir(Long id,Class sinif);
}
