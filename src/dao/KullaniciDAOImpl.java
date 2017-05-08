package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import jxl.write.DateTime;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import forms.EvradAbone;
import forms.EvradBolumleme;
import forms.EvradKisim;
import forms.Girisler;
import forms.Grub;
import forms.KitabAbone;
import forms.Kullanici;

@Repository
// @PersistenceContext(type=PersistenceContextType.EXTENDED)
public class KullaniciDAOImpl implements KullaniciDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private IslemDAO islemDAO;

	// @Autowired
	// private KullaniciDAO kullaniciDAO;

	@Override
	public Kullanici kullaniciGetir(Kullanici kullanici, Boolean sifresiz) {
		Kullanici sonuc = null;
		if ((kullanici.getSifre() == null) == sifresiz) {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Kullanici.class);
			kullanici.setPasif(false);
			criteria.add(Example.create(kullanici));
			List<Kullanici> liste = criteria.list();
			if (liste != null && liste.size() > 0) {
				return liste.get(0);
			}
		}
		return null;
	}

	@Override
	public void girisEkle(Kullanici kullanici, Date tarih, String ipNo) {
		Girisler girisler = new Girisler();
		girisler.setKullanici(kullanici);
		girisler.setTarih(tarih);
		girisler.setIpNo(ipNo);
		islemDAO.kaydet(girisler);
	}

	@Override
	public List kitabAboneligi(Long kullaniciId, Boolean hepsi) {
		Criteria criteriaKitabAbone = sessionFactory.getCurrentSession()
				.createCriteria(KitabAbone.class);
		criteriaKitabAbone.add(Restrictions.eq("kullanici.id", kullaniciId));
		criteriaKitabAbone.add(Restrictions.le("baslamaTarihi", new Date()));

		if (hepsi == null || !hepsi)
			criteriaKitabAbone.add(Restrictions.eq("pasif", false));
		List listSonuc = criteriaKitabAbone.list();
		if (listSonuc != null && listSonuc.size() > 0) {
			// KitabAbone kitabAbone = (KitabAbone) listSonuc.get(0);
			return listSonuc;
		}
		return null;
	}

	@Override
	public List evradAboneligi(Long kullaniciId, Long grubId, Long bolumId,
			Boolean hepsi) {

		Criteria criteriaEvradAbone = sessionFactory.getCurrentSession()
				.createCriteria(EvradAbone.class);
		Kullanici kullanici = null;
		criteriaEvradAbone.addOrder(Order.asc("id"));
		// criteriaEvradAbone.addOrder(Order.asc("grub.id"));
		// criteriaEvradAbone.addOrder(Order.asc("evradKisim.id"));
		if (kullaniciId != null) {
			if (bolumId == null)
				criteriaEvradAbone.add(Restrictions.eq("kullanici.id",
						kullaniciId));
			else
				kullanici = (Kullanici) islemDAO.getir(kullaniciId,
						Kullanici.class);
		}
		// System.out.println("***kul: "+kullaniciId+" grub : "+grubId+" bol: "+bolumId+" kul: "+kullanici.getGrub().getId());
		if (grubId != null)
			criteriaEvradAbone.add(Restrictions.eq("grub.id", grubId));
		if (bolumId != null) {
			DetachedCriteria dtCriteriaKisim = DetachedCriteria
					.forClass(EvradKisim.class);
			dtCriteriaKisim.setProjection(Property.forName("id"));
			dtCriteriaKisim.add(Restrictions.eq("evradBolumleme.id", bolumId));

			DetachedCriteria dtCriteriaKullanici = DetachedCriteria
					.forClass(Kullanici.class);
			dtCriteriaKullanici.setProjection(Property.forName("id"));
			dtCriteriaKullanici.add(Restrictions.eq("grub.id", kullanici
					.getGrub().getId()));

			criteriaEvradAbone.add(Subqueries.propertyIn("evradKisim.id",
					dtCriteriaKisim));
			criteriaEvradAbone.add(Subqueries.propertyIn("kullanici.id",
					dtCriteriaKullanici));
		}

		if (hepsi == null || !hepsi) {
			criteriaEvradAbone.add(Restrictions.eq("pasif", false));
			criteriaEvradAbone
					.add(Restrictions.le("baslamaTarihi", new Date()));
		}

		return criteriaEvradAbone.list();
	}

	@Override
	public List<Kullanici> kullaniciListesi(Kullanici kullanici, Grub grub,
			Kullanici aktifKullanici) {
		if (aktifKullanici != null) {
			if (aktifKullanici.getSelahiyet() == null)
				aktifKullanici.setSelahiyet(false);
			if (!aktifKullanici.getSelahiyet()) {
				ArrayList<Kullanici> sonuc = new ArrayList<>();
				sonuc.add(aktifKullanici);
				return sonuc;
			}
		}
		Criteria criteriaKullanici = sessionFactory.getCurrentSession()
				.createCriteria(Kullanici.class);

		criteriaKullanici.add(Restrictions.eq("grub.id", grub.getId()));
		if (kullanici != null) {
			if (kullanici.getIsim() != null) {
				String isim = kullanici.getIsim().trim();
				if (!isim.equals(""))
					criteriaKullanici.add(Restrictions.ilike("isim", "%" + isim
							+ "%"));
			}
			if (kullanici.getEmail() != null) {
				String email = kullanici.getEmail().trim();
				if (!email.equals(""))
					criteriaKullanici.add(Restrictions.ilike("email", "%"
							+ email + "%"));
			}
			if (kullanici.getTelefon() != null) {
				String telefon = kullanici.getTelefon().trim();
				if (!telefon.equals(""))
					criteriaKullanici.add(Restrictions.ilike("telefon", "%"
							+ telefon + "%"));
			}
		} else
			criteriaKullanici.add(Restrictions.eq("pasif", false));
		criteriaKullanici.addOrder(Order.asc("isim"));

		return criteriaKullanici.list();
	}

	@Override
	public Boolean kullaniciVarMi(String kullaniciIsmi) {
		Criteria criteriaKullanici = sessionFactory.getCurrentSession()
				.createCriteria(Kullanici.class);
		criteriaKullanici.add(Restrictions.eq("isim", kullaniciIsmi));
		List sonucList = criteriaKullanici.list();
		return (sonucList != null && sonucList.size() > 0);
	}
}
