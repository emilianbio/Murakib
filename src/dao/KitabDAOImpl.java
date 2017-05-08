package dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import araclar.Genel;
import forms.Donem;
import forms.KitabAbone;
import forms.KitabOkuma;
@Repository
public class KitabDAOImpl implements KitabDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IslemDAO islemDAO;

	@Override
	public Long donemIciOkunanSayfaSayisi(Donem donem,KitabAbone kitabAbone) {
		Long sonuc=0L;
		Criteria criteriaKitabOkuma=sessionFactory.getCurrentSession().createCriteria(KitabOkuma.class);
		if(donem!=null) criteriaKitabOkuma.add(Restrictions.ge("tarih",donem.getBaslamaTarihi()));
		if(donem!=null) criteriaKitabOkuma.add(Restrictions.le("tarih",donem.getBitisTarihi()));
		criteriaKitabOkuma.add(Restrictions.eq("kitabAbone.id", kitabAbone.getId()));
		List listKitabOkuma = criteriaKitabOkuma.list();
		if(listKitabOkuma!=null && listKitabOkuma.size()>0) {
			Iterator<KitabOkuma> iteratorKitabOkuma=listKitabOkuma.iterator();
			while (iteratorKitabOkuma.hasNext()){
				KitabOkuma kitabOkuma=iteratorKitabOkuma.next();
				Short sonSayfa=0,ilkSayfa=0;
				if(kitabOkuma.getSonSayfa()!=null) sonSayfa = kitabOkuma.getSonSayfa();
				if(kitabOkuma.getIlkSayfa()!=null) ilkSayfa = kitabOkuma.getIlkSayfa();
				sonuc= (sonuc + (sonSayfa-ilkSayfa));
			}	
		}
		return sonuc;
	}

	@Override
	public List kitabOkumaListesi(KitabAbone kitabAbone,Donem donem,Integer kayitSayisi,Integer ilkKayitSirasi) {
		Criteria criteriaKitabOkuma=sessionFactory.getCurrentSession().createCriteria(KitabOkuma.class);
		criteriaKitabOkuma.add(Restrictions.eq("kitabAbone", kitabAbone));
		if(donem!=null) {
			criteriaKitabOkuma.add(Restrictions.ge("tarih", donem.getBaslamaTarihi()));
			criteriaKitabOkuma.add(Restrictions.le("tarih", donem.getBitisTarihi()));
		}
		if(kayitSayisi!=null){
			criteriaKitabOkuma.setMaxResults(kayitSayisi);
		}
		if(ilkKayitSirasi!=null){
			criteriaKitabOkuma.setFirstResult(ilkKayitSirasi);
		}
		criteriaKitabOkuma.addOrder(Order.desc("tarih"));
		List<KitabOkuma> listSonuc=criteriaKitabOkuma.list();
		return listSonuc;
	}
	
	@Override
	public void buguneKitabOkumaEkle(KitabAbone kitabAbone) {
		Criteria criteriaKitabOkuma=sessionFactory.getCurrentSession().createCriteria(KitabOkuma.class);
		Date bugun = Genel.sadeceTarih(new Date());
		criteriaKitabOkuma.add(Restrictions.eq("tarih", bugun));
		criteriaKitabOkuma.add(Restrictions.eq("kitabAbone", kitabAbone ));
		
		List listKitabOkuma=criteriaKitabOkuma.list();
		if(listKitabOkuma==null || listKitabOkuma.size()==0){
			KitabOkuma kitabOkuma = new KitabOkuma();
			kitabOkuma.setKitabAbone(kitabAbone);
			kitabOkuma.setTarih(bugun);
			List listSonKitabOkuma=kitabOkumaListesi(kitabAbone, null, 1,null);
			if(listSonKitabOkuma!=null && listSonKitabOkuma.size()>0) {
				KitabOkuma sonKitabOkuma=(KitabOkuma)listSonKitabOkuma.get(0);
				kitabOkuma.setKitab(sonKitabOkuma.getKitab());
				kitabOkuma.setIlkSayfa(sonKitabOkuma.getSonSayfa());
				kitabOkuma.setSonSayfa(sonKitabOkuma.getSonSayfa());
			}
			islemDAO.kaydet(kitabOkuma);
		}
	}

	@Override
	public List kitabAbonelistesi(Boolean pasif) {
		Criteria criteriaKitabAbone=sessionFactory.getCurrentSession().createCriteria(KitabAbone.class);
		if(pasif!=null)criteriaKitabAbone.add(Restrictions.eq("pasif",pasif));
		criteriaKitabAbone.add(Restrictions.le("baslamaTarihi", Genel.sadeceTarih(new Date())));
		return criteriaKitabAbone.list();
	}

	
}
