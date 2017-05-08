package service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

public interface IslemServis {
	public void kaydet(Object nesne);
	public List<Object> listeNesne(String sorgu);
	public Integer calistir(String sorgu);
	public void sil(Long id,Class sinif);
	public Object getir(Long id,Class sinif);
}
