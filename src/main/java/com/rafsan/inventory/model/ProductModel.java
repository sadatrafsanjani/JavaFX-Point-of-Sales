package com.rafsan.inventory.model;

import com.rafsan.inventory.HibernateUtil;
import com.rafsan.inventory.dao.ProductDao;
import com.rafsan.inventory.entity.Product;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class ProductModel implements ProductDao {

    private static Session session;

    @Override
    public ObservableList<Product> getProducts() {

        ObservableList<Product> list = FXCollections.observableArrayList();
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product").list();
        session.getTransaction().commit();
        products.stream().forEach(list::add);

        return list;
    }

    @Override
    public Product getProduct(long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();

        return product;
    }

    @Override
    public Product getProductByName(String productName) {

        session.beginTransaction();

        Product product = session.createQuery(
                        "from Product p where p.productName = :name",
                        Product.class
                ).setParameter("name", productName)
                .uniqueResult();

        session.getTransaction().commit();

        return product;
    }

    @Override
    public void saveProduct(Product product) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
    }

    @Override
    public void updateProduct(Product product) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product p = session.get(Product.class, product.getId());
        p.setProductName(product.getProductName());
        p.setCategory(product.getCategory());
        p.setQuantity(product.getQuantity());
        p.setPrice(product.getPrice());
        p.setDescription(product.getDescription());
        session.getTransaction().commit();
    }
    
    @Override
    public void increaseProduct(Product product){
    
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product p = session.get(Product.class, product.getId());
        p.setQuantity(product.getQuantity());
        session.getTransaction().commit();
    }
    
    @Override
    public void decreaseProduct(Product product){
    
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product p = session.get(Product.class, product.getId());
        p.setQuantity(product.getQuantity());
        session.getTransaction().commit();
    }

    @Override
    public void deleteProduct(Product product) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product p = session.get(Product.class, product.getId());
        session.delete(p);
        session.getTransaction().commit();
    }
    
    @Override
    public ObservableList<String> getProductNames(){

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<String> productNames = session.createQuery("select p.productName from Product p", String.class).getResultList();
        ObservableList<String> list = FXCollections.observableArrayList(productNames);

        session.getTransaction().commit();

        return list;
    }
}
