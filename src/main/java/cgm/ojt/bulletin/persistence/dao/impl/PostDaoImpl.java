package cgm.ojt.bulletin.persistence.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cgm.ojt.bulletin.persistence.dao.PostDao;
import cgm.ojt.bulletin.persistence.entity.Post;

@Repository
@Transactional
public class PostDaoImpl implements PostDao {
	@Autowired
	private SessionFactory sessionFactory;

	JdbcTemplate jdbc;

	@Override
	public int dbSavePost(Post post) {
		return (int) this.sessionFactory.getCurrentSession().save(post);
	}

	@Override
	public void dbSavePostCategory(int id, int categoryId) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> dbGetAllPost() {
		String postQuery = "SELECT p FROM Post p";
		Query queryUserList = this.sessionFactory.getCurrentSession().createQuery(postQuery);
		List<Post> postList = queryUserList.getResultList();
		return postList;
	}

//	@Override
//	public void dbSavePostCategory(int id, int categoryId) {
//		String insertHql = "INSERT INTO post_categories(post_id,category_id) VALUES('"+id+"',"+categoryId+"')";
//	}
}