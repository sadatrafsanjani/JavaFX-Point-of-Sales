package com.rafsan.inventory.model;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.SupplierDao;
import com.rafsan.inventory.entity.Supplier;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class SupplierModel implements SupplierDao {

    private static Session session;

    @Override
    public ObservableList<Supplier> getSuppliers() {

        ObservableList<Supplier> list = FXCollections.observableArrayList();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Supplier> suppliers = session.createQuery("from Supplier").list();
        session.getTransaction().commit();
        suppliers.stream().forEach(list::add);

        return list;
    }

    @Override
    public Supplier getSupplier(long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Supplier supplier = session.get(Supplier.class, id);
        session.getTransaction().commit();

        return supplier;
    }

    @Override
    public void saveSuplier(Supplier supplier) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(supplier);
        session.getTransaction().commit();
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
    }

    @Override
    public void deleteSuplier(Supplier supplier) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Supplier s = session.get(Supplier.class, supplier.getId());
        session.delete(s);
        session.getTransaction().commit();
    }
    
    @Override
    public ObservableList<String> getNames(){

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<String> names = session.createQuery("select s.name from Supplier s", String.class).getResultList();
        ObservableList<String> list = FXCollections.observableArrayList(names);

        session.getTransaction().commit();

        return list;
    }

}
