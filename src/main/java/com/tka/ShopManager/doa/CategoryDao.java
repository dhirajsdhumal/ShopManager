package com.tka.ShopManager.doa;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.ShopManager.entity.Category;

@Repository
public class CategoryDao {

	@Autowired
	SessionFactory factory;

	public List<Category> getallCategory(int page,int size) {

		Session session = null;
		Transaction tx = null;
		List<Category> list = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			String hql = "from Category";
			Query<Category> query = session.createQuery(hql, Category.class);

			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);

			list = query.getResultList();
			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	public String addCategory(Category c) {
		Session session = null;
		Transaction tx = null;

		String msg = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.persist(c);
			tx.commit();
			msg = "Category is added successfully";
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return msg;
	}

	public Category getParticularCategoryID(int id) {
		Session session = null;
		Transaction tx = null;
		Category category = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			category = session.get(Category.class, id);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return category;
	}

	public String updateCategory(Category c, int id) {
		Session session = null;
		Transaction tx = null;

		String msg = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			Category category = session.get(Category.class, id);
			category.setName(c.getName());
			category.setDescription(c.getDescription());
			session.merge(category);

			tx.commit();
			msg = "Category is Update successfully";
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return msg;
	}

	public String deleteCategory(int id) {
		Session session = null;
		Transaction tx = null;

		String msg = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			Category category = session.get(Category.class, id);

			session.remove(category);
			tx.commit();
			msg = "Category is Deleted";
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return msg;
	}

}
