package com.rafsan.inventory.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.CategoryDao;
import com.rafsan.inventory.entity.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel implements CategoryDao {

	private static Session session;

	@Override
	public ObservableList<Category> getCategories() {

		ObservableList<Category> list = FXCollections.observableArrayList();

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Category> categories = session.createQuery("from Category").list();
		session.getTransaction().commit();
		session.close();
		categories.stream().forEach(list::add);

		return list;
	}

	@Override
	public Category getCategory(long id) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Category category = session.get(Category.class, id);
		session.getTransaction().commit();
		session.close();
		return category;
	}

	@Override
	public void saveCategory(Category category) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(category);
		session.getTransaction().commit();

		session.close();
	}

	@Override
	public void updateCategory(Category category) {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Category c = session.get(Category.class, category.getId());
		c.setType(category.getType());
		c.setDescription(category.getDescription());
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void deleteCategory(Category category) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Category c = session.get(Category.class, category.getId());
		session.delete(c);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public ObservableList<String> getTypes() {

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Category.class);
		criteria.setProjection(Projections.property("type"));
		ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
		session.getTransaction().commit();
		session.close();

		return list;
	}

}
