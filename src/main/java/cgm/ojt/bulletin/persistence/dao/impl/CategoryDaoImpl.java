package cgm.ojt.bulletin.persistence.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgm.ojt.bulletin.persistence.dao.CategoryDao;
import cgm.ojt.bulletin.persistence.entity.Category;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void dbSaveCategory(Category category) {
		this.sessionFactory.getCurrentSession().save(category);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> dbGetAllCategory() {
		return this.sessionFactory.getCurrentSession().createQuery("SELECT c FROM Category c").list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Category dbGetCategoryById(Integer categoryId) {
		String categoryHqlQuery = "SELECT c FROM Category c where c.category_id = :id";
		Query queryCategoryById = this.sessionFactory.getCurrentSession().createQuery(categoryHqlQuery);
		queryCategoryById.setParameter("id", categoryId);

		Category category = (Category) queryCategoryById.uniqueResult();
		return category;
	}

	@Override
	public void dbUpdateCategory(Category category) {
		this.sessionFactory.getCurrentSession().update(category);
	}
}