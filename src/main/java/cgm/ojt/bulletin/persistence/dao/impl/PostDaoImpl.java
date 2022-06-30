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
import cgm.ojt.bulletin.web.form.PostForm;

@Repository
@Transactional
public class PostDaoImpl implements PostDao {
	private static final String SELECT_POST_LIST_HQL = "SELECT p FROM Post p ";

	private static final String SELECT_POST_BY_INPUT = "WHERE (p.title LIKE :search OR p.description LIKE :search) AND p.deleted_at = null ";

	private static final String ORDER_BY_DESC_SEARCH = "ORDER BY p.post_id DESC";

	private static final String ORDER_BY_DESC = "WHERE p.deleted_at = null ORDER BY p.post_id DESC";

	@Autowired
	private SessionFactory sessionFactory;

	JdbcTemplate jdbc;

	@Override
	public int dbSavePost(Post post) {
		return (int) this.sessionFactory.getCurrentSession().save(post);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> dbGetAllPost() {
		String postQuery = "SELECT p FROM Post p WHERE p.deleted_at = null";
		Query queryUserList = this.sessionFactory.getCurrentSession().createQuery(postQuery);
		List<Post> postList = queryUserList.getResultList();
		return postList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> dbGetAllPostBySearchInput(String post_search) {
		PostForm postForm = new PostForm();
		postForm.setPost_search(post_search);
		Query queryPostList = this.dbCreateQueryList(postForm);
		List<Post> postList = queryPostList.getResultList();
		return postList;
	}

	@Override
	public Post dbGetPostById(Integer postId) {
		String postQuery = "SELECT p FROM Post p where p.post_id = :postId";
		Query queryPostById = this.sessionFactory.getCurrentSession().createQuery(postQuery);
		queryPostById.setParameter("postId", postId);
		Post result = (Post) queryPostById.getSingleResult();
		return result;
	}

	@Override
	public void dbUpdatePost(Post post) {
		Post posts = (Post) sessionFactory.getCurrentSession().load(Post.class, post.getPost_id());
		if (posts != null) {
			posts.setTitle(post.getTitle());
			posts.setDescription(post.getDescription());
			posts.setPost_img(post.getPost_img());
			posts.setPost_categories(post.getPost_categories());
			posts.setUpdated_user_id(post.getUpdated_user_id());
			posts.setUpdated_at(post.getUpdated_at());
			posts.setDeleted_at(post.getDeleted_at());
			this.sessionFactory.getCurrentSession().update(posts);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> dbGetAllSearchPost(int currentPage, int recordsPerPage, PostForm postForm) {
		int start = currentPage * recordsPerPage - recordsPerPage;
		Query queryPostList = this.dbCreateQueryList(postForm);
		queryPostList.setFirstResult(start);
		queryPostList.setMaxResults(recordsPerPage);

		List<Post> categoryList = (List<Post>) queryPostList.getResultList();

		return categoryList;
	}

	private Query dbCreateQueryList(PostForm postForm) {
		StringBuffer query = new StringBuffer(SELECT_POST_LIST_HQL);
		if (postForm.getPost_search() != null) {
			query.append(SELECT_POST_BY_INPUT);
			query.append(ORDER_BY_DESC_SEARCH);
		} else {
			query.append(ORDER_BY_DESC);
		}
		Query queryPostList = this.sessionFactory.getCurrentSession().createQuery(query.toString());
		if (postForm.getPost_search() != null) {
			queryPostList.setParameter("search", "%" + postForm.getPost_search() + "%");
		}
		return queryPostList;
	}
}