package dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IslemDAOImpl implements IslemDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void kaydet(Object nesne) {
		sessionFactory.getCurrentSession().saveOrUpdate(nesne);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> listeNesne(String sorgu) {
		return sessionFactory.getCurrentSession().createQuery(sorgu).list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void sil(Long id,Class sinif) {
		Object object = sessionFactory.getCurrentSession().load(sinif, id);
		if (null != object) {
			sessionFactory.getCurrentSession().delete(object);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getir(Long id,Class sinif) {
		//load yerine get kullanıldı getCurrentSession yeri be
		Object nesne=sessionFactory.getCurrentSession().get(sinif, id);
		return nesne;
	}
	@Override
	public Integer calistir(String sorgu) {
		return sessionFactory.getCurrentSession().createQuery(sorgu).executeUpdate();
	}
	
}
