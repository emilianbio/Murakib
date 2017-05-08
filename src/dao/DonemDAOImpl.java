package dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import forms.Donem;
@Repository
public class DonemDAOImpl implements DonemDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IslemDAO islemDAO;

	@Override
	public void kaydet(Object nesne) {
		sessionFactory.getCurrentSession().saveOrUpdate(nesne);		
	}
	@Override
	public List<Object> listDonemler() {
		return islemDAO.listeNesne("from Donem order by baslamaTarihi DESC");
//		Criteria criteriaDonem = sessionFactory.getCurrentSession().createCriteria(Donem.class);
//		criteriaDonem.addOrder(Order.desc("baslamaTarihi"));
//		System.out.println(criteriaDonem.list().size());
//		return criteriaDonem.list();
	}
	@Override
	public Donem gecerliDonemiGetir() {
		Donem sonuc=null;
		Criteria criteriaDonem = sessionFactory.getCurrentSession().createCriteria(Donem.class);
		
		Date bugun=new Date();
		criteriaDonem.add(Restrictions.le("baslamaTarihi", bugun));
		criteriaDonem.add(Restrictions.ge("bitisTarihi", bugun));
		List listSonuc=criteriaDonem.list();
		if(listSonuc!=null && listSonuc.size()>0){
			sonuc=(Donem)listSonuc.get(0);
//			sonuc[0] = (bugun.getTime() - donem.getBaslamaTarihi().getTime())/(1000*60*60*24)+1;
//			sonuc[1] = (donem.getBitisTarihi().getTime() - donem.getBaslamaTarihi().getTime())/(1000*60*60*24)+1;
		}
		return sonuc;
	}
}
