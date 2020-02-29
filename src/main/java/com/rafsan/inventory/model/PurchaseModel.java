package com.rafsan.inventory.model;

import java.util.List;

import org.hibernate.Session;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.PurchaseDao;
import com.rafsan.inventory.entity.Purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseModel implements PurchaseDao {

	private static Session session;

	@Override
	public ObservableList<Purchase> getPurchases() {

		ObservableList<Purchase> list = FXCollections.observableArrayList();

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Purchase> products = session.createQuery("from Purchase").list();
		session.getTransaction().commit();

		session.close();
		products.stream().forEach(list::add);

		return list;
	}

	@Override
	public Purchase getPurchase(long id) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Purchase purchase = session.get(Purchase.class, id);
		session.getTransaction().commit();

		session.close();

		return purchase;
	}

	@Override
	public void savePurchase(Purchase purchase) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(purchase);
		session.getTransaction().commit();

		session.close();
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Purchase p = session.get(Purchase.class, purchase.getId());
		p.setProduct(purchase.getProduct());
		p.setSupplier(purchase.getSupplier());
		p.setQuantity(purchase.getQuantity());
		p.setDate(purchase.getDate());
		session.getTransaction().commit();

		session.close();
	}

	@Override
	public void deletePurchase(Purchase purchase) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Purchase p = session.get(Purchase.class, purchase.getId());
		session.delete(p);
		session.getTransaction().commit();

		session.close();
	}

}
