/**
 * 
 */
package araclar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author lenovo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// SADECE TABLOLARI OLUŞTURMAK İÇİN YAZILAN KOD

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg2.xml").buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction().commit();
		session.close();

	}
}