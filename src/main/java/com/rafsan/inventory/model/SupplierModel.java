package com.rafsan.inventory.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.SupplierDao;
import com.rafsan.inventory.entity.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierModel implements SupplierDao {

	private static Session session;

	@Override
	public ObservableList<Supplier> getSuppliers() {

		ObservableList<Supplier> list = FXCollections.observableArrayList();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Supplier> suppliers = session.createQuery("from Supplier").list();
		session.getTransaction().commit();
	
		session.close();
		suppliers.stream().forEach(list::add);

		return list;
	}

	@Override
	public Supplier getSupplier(long id) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Supplier supplier = session.get(Supplier.class, id);
		session.getTransaction().commit();
	
		session.close();

		return supplier;
	}

	@Override
	public void saveSuplier(Supplier supplier) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(supplier);
		session.getTransaction().commit();
		
		session.close();
	}

	@Override
	public void updateSuplier(Supplier supplier) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Supplier s = session.get(Supplier.class, supplier.getId());
		s.setName(supplier.getName());
		s.setPhone(supplier.getPhone());
		s.setAddress(supplier.getAddress());
		session.getTransaction().commit();

		session.close();
	}

	@Override
	public void deleteSuplier(Supplier supplier) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Supplier s = session.get(Supplier.class, supplier.getId());
		session.delete(s);
		session.getTransaction().commit();

		session.close();
	}

	@Override
	public ObservableList<String> getNames() {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Supplier.class);
		criteria.setProjection(Projections.property("name"));
		ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
		session.getTransaction().commit();
		session.close();

		return list;
	}

}
