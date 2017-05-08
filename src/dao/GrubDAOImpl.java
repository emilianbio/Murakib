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
import forms.EvradAbone;
import forms.Grub;
@Repository
public class GrubDAOImpl implements GrubDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IslemDAO islemDAO;
	@Autowired
	private KullaniciDAO kullaniciDAO;
	@Autowired
	private EvradDAO evradDAO;

	@Override
	public List<Object> grubListesi(Grub grub) {
//		System.out.println(grub.getId());
		Criteria criteriaGrub=sessionFactory.getCurrentSession().createCriteria(Grub.class);
		criteriaGrub.add(Restrictions.eq("grub.id", grub.getGrub().getId()));
		if (grub.getIsim()!=null){
			String isim=grub.getIsim().trim();
			if (!isim.equals("")) criteriaGrub.add(Restrictions.ilike("isim", "%"+isim+"%"));
		}
		if(grub.getBaslamaTarihi()!=null){
			criteriaGrub.add(Restrictions.eq("baslamaTarihi", grub.getBaslamaTarihi()));
		}
		if(grub.getEvradBolumleme()!=null && grub.getEvradBolumleme().getId()>0){
			criteriaGrub.add(Restrictions.eq("evradBolumleme.id", grub.getEvradBolumleme().getId()));
		}
		criteriaGrub.addOrder(Order.asc("id"));
		return criteriaGrub.list();
	}

	@Override
	public Grub grubGuncelle(Long grubId, String isim, String baslama,
			String bitis, Short sure) {
		Grub grub=(Grub)islemDAO.getir(grubId, Grub.class);
//		if(grub!=null && grub.getSabit()) return grub;
		Date yeniBaslama=Genel.tariheCevir(baslama);
		if(!grub.getBaslamaTarihi().equals(yeniBaslama) && (grub.getSabit()==null || !grub.getSabit())){
			List abonelikler=kullaniciDAO.evradAboneligi(null, grubId, null, true);
		    if(abonelikler!=null && abonelikler.size()>0){
				Iterator<EvradAbone> iteratorAbone=abonelikler.iterator();
				while (iteratorAbone.hasNext()) {
					EvradAbone evradAbone=iteratorAbone.next();
					if(evradAbone.getEvradKisim().getEvradBolumleme().getParcaSayisi()>1){
						evradDAO.bosGunleriDoldur(evradAbone);
						evradAbone.setEvradKisim(evradDAO.okunacakEvradKismi(evradAbone, yeniBaslama));
						islemDAO.kaydet(evradAbone);
					}
				}
			}
		}
		grub.setIsim(isim);
		if(baslama!=null)grub.setBaslamaTarihi(yeniBaslama);
		grub.setBitisTarihi(Genel.tariheCevir(bitis));
		if(sure!=null && sure>0) grub.setParcaSuresi(sure);
		islemDAO.kaydet(grub);
		return grub;
	}
}
