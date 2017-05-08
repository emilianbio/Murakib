package dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import araclar.Genel;
import forms.Donem;
import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.EvradOkuma;
import forms.Grub;
@Repository
public class EvradDAOImpl implements EvradDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IslemDAO islemDAO;

	@Override
	public List<EvradOkuma> evradOkumaListesi(EvradAbone evradAbone,Donem donem,Integer kayitSayisi,Integer ilkKayitSirasi) {
		Criteria criteriaEvradOkuma=sessionFactory.getCurrentSession().createCriteria(EvradOkuma.class);
		criteriaEvradOkuma.add(Restrictions.eq("evradAbone", evradAbone));
		if(donem!=null) {
			criteriaEvradOkuma.add(Restrictions.ge("tarih", donem.getBaslamaTarihi()));
			criteriaEvradOkuma.add(Restrictions.le("tarih", donem.getBitisTarihi()));
		}
		if(kayitSayisi!=null){
			criteriaEvradOkuma.setMaxResults(kayitSayisi);
		}
		if(ilkKayitSirasi!=null){
			criteriaEvradOkuma.setFirstResult(ilkKayitSirasi);
		}
		criteriaEvradOkuma.addOrder(Order.desc("tarih"));
		List<EvradOkuma> listSonuc=criteriaEvradOkuma.list();
		return listSonuc;
	}

	@Override
	public List evradKisimListesi(Long evradBolumlemeId,Long evradAboneId,Long grubId) {
		Criteria criteriaEvradKisim=sessionFactory.getCurrentSession().createCriteria(EvradKisim.class);
		criteriaEvradKisim.add(Restrictions.eq("evradBolumleme.id", evradBolumlemeId));
		
		if(grubId!=null && grubId>0){
			DetachedCriteria  dtCriteriaEvradAbone = DetachedCriteria.forClass(EvradAbone.class);
			dtCriteriaEvradAbone.setProjection(Property.forName("evradKisim.id"));
			if(evradAboneId!=null) dtCriteriaEvradAbone.add(Restrictions.ne("id", evradAboneId));
			if(grubId!=null) dtCriteriaEvradAbone.add(Restrictions.eq("grub.id", grubId));
			dtCriteriaEvradAbone.add(Restrictions.eq("pasif", false));
			criteriaEvradKisim.add(Subqueries.propertyNotIn("id", dtCriteriaEvradAbone));
		}
		criteriaEvradKisim.addOrder(Order.asc("siraNo"));
		return criteriaEvradKisim.list();
//		Criterion abone= Restrictions.and(Restrictions.eq("A", "X"), 
//		           Restrictions.in("B", Arrays.asList("X","Y")));
//		Criterion rest2= Restrictions.and(Restrictions.eq("A", "Y"), 
//		           Restrictions.eq("B", "Z"));
//		criteria.add(Restrictions.or(rest1, rest2));
//		return islemDAO.listeNesne("from EvradKisim where evradBolumleme.id="+evradBolumleme.getId()+" order by siraNo");
	}

	@Override
	public void evradOkumaEkle(EvradAbone evradAbone,Date tarih) {
		Criteria criteriaEvradOkuma=sessionFactory.getCurrentSession().createCriteria(EvradOkuma.class);
		Date okumaTarihi = Genel.neZamanOkunacak(evradAbone, tarih);
		if(Genel.evradOkunacakMi(evradAbone, okumaTarihi)){
			criteriaEvradOkuma.add(Restrictions.eq("tarih", okumaTarihi));
			criteriaEvradOkuma.add(Restrictions.eq("evradAbone", evradAbone ));
			
			List listEvradOkuma=criteriaEvradOkuma.list();
			if(listEvradOkuma==null || listEvradOkuma.size()==0){
				EvradOkuma evradOkuma = new EvradOkuma();
				evradOkuma.setEvradAbone(evradAbone);
				evradOkuma.setTarih(okumaTarihi);
				evradOkuma.setEvradKisim(okunacakEvradKismi(evradAbone,okumaTarihi));
				islemDAO.kaydet(evradOkuma);
			}
		}
	}
	@Override
	public EvradKisim okunacakEvradKismi(EvradAbone evradAbone,Date tarih){
		if((evradAbone.getGrub()!=null && evradAbone.getGrub().getSabit()!=null && evradAbone.getGrub().getSabit()) || (evradAbone.getSabit()!=null && evradAbone.getSabit())){
			return evradAbone.getEvradKisim();
		}
		Short sonrakiEvradSiraNo=1;
		Short evradParcaSayisi=evradAbone.getEvradKisim().getEvradBolumleme().getParcaSayisi();
		if(evradParcaSayisi>1){
			Date baslamaTarihi=evradAbone.getBaslamaTarihi();
			if(evradAbone.getGrub()!=null) baslamaTarihi=evradAbone.getGrub().getBaslamaTarihi();
			Short evradParcaSuresi=Genel.evradParcaSuresi(evradAbone);
			tarih = Genel.neZamanOkunacak(evradAbone, tarih);
			if(evradAbone.getEvradKisim().getSiraNo()>1) baslamaTarihi=Genel.gunEkle(baslamaTarihi, (evradAbone.getEvradKisim().getSiraNo()-1)*(-evradParcaSuresi));
			Long okunanParcaSayisi = ((Genel.gunFarki(tarih, baslamaTarihi))/evradParcaSuresi)+1;
			sonrakiEvradSiraNo=(short)(okunanParcaSayisi % evradParcaSayisi);
			if(sonrakiEvradSiraNo==0) sonrakiEvradSiraNo = evradParcaSayisi;
		}
		if(sonrakiEvradSiraNo<0) sonrakiEvradSiraNo=(short)(evradParcaSayisi+sonrakiEvradSiraNo);
		return evradKisimGetir(evradAbone, sonrakiEvradSiraNo);
	}

	@Override
	public void bosGunleriDoldur(EvradAbone evradAbone) {
		Date gun=Genel.evradBaslamaTarihi(evradAbone);
		Date buGun=new Date();
		while (buGun.after(gun)) {
			evradOkumaEkle(evradAbone, gun);
			gun = Genel.gunEkle(gun,1);	
		}
	}
	
	
	private EvradKisim evradKisimGetir(EvradAbone evradAbone,Short siraNo){
		Criteria criteriaEvradKisim=sessionFactory.getCurrentSession().createCriteria(EvradKisim.class);
		criteriaEvradKisim.add(Restrictions.eq("evradBolumleme", evradAbone.getEvradKisim().getEvradBolumleme()));
		criteriaEvradKisim.add(Restrictions.eq("siraNo", siraNo));
		return (EvradKisim)criteriaEvradKisim.list().get(0);
	}
	
	@Override
	public List evradBolumlemeListesi() {
		return islemDAO.listeNesne("from EvradBolumleme order by id ASC");
	}

	@Override
	public Long evradHulasa(EvradAbone evradAbone, Donem donem,
			Boolean hepsi) {
		Criteria criteriaEvradOkuma=sessionFactory.getCurrentSession().createCriteria(EvradOkuma.class);
		criteriaEvradOkuma.add(Restrictions.eq("evradAbone", evradAbone));
		if(donem!=null) {
			criteriaEvradOkuma.add(Restrictions.ge("tarih", donem.getBaslamaTarihi()));
			criteriaEvradOkuma.add(Restrictions.le("tarih", donem.getBitisTarihi()));
		}
		criteriaEvradOkuma.setProjection(Projections.rowCount());
		if(!hepsi)criteriaEvradOkuma.add(Restrictions.isNotNull("kayitTarihi")); 
		return (Long)criteriaEvradOkuma.list().get(0);
	}

	@Override
	public List evradAbonelistesi(Boolean pasif) {
		Criteria criteriaEvradAbone=sessionFactory.getCurrentSession().createCriteria(EvradAbone.class);
		if(pasif!=null) criteriaEvradAbone.add(Restrictions.eq("pasif", pasif));
		Date bugun=Genel.sadeceTarih(new Date());
		criteriaEvradAbone.add(Restrictions.le("baslamaTarihi",bugun));
		
		DetachedCriteria dtCriteriaGrub=DetachedCriteria.forClass(Grub.class);
		dtCriteriaGrub.setProjection(Property.forName("id"));
		dtCriteriaGrub.add(Restrictions.le("baslamaTarihi", bugun));
		dtCriteriaGrub.add(Restrictions.ge("bitisTarihi", bugun));
		criteriaEvradAbone.add(Subqueries.propertyNotIn("grub.id", dtCriteriaGrub));
		
		
		return criteriaEvradAbone.list();
	}
}