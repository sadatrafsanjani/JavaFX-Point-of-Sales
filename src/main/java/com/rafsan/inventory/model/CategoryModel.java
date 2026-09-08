package com.rafsan.inventory.model;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.CategoryDao;
import com.rafsan.inventory.entity.Category;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class CategoryModel implements CategoryDao {

    private static Session session;

    @Override
    public ObservableList<Category> getCategories() {

        ObservableList<Category> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Category> categories = session.createQuery("from Category").list();
        session.getTransaction().commit();
        categories.stream().forEach(list::add);

        return list;
    }

    @Override
    public Category getCategory(long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Category category = session.get(Category.class, id);
        session.getTransaction().commit();

        return category;
    }

    @Override
    public void saveCategory(Category category) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
    }

    @Override
    public void updateCategory(Category category) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Category c = session.get(Category.class, category.getId());
        c.setType(category.getType());
        c.setDescription(category.getDescription());
        session.getTransaction().commit();
    }

    @Override
    public void deleteCategory(Category category) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Category c = session.get(Category.class, category.getId());
        session.delete(c);
        session.getTransaction().commit();
    }

    @Override
    public ObservableList<String> getTypes() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<String> types = session.createQuery("select c.type from Category c", String.class).getResultList();
        ObservableList<String> list = FXCollections.observableArrayList(types);

        session.getTransaction().commit();

        return list;
    }

}
