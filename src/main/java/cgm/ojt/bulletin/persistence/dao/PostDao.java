package cgm.ojt.bulletin.persistence.dao;

import java.util.List;

import cgm.ojt.bulletin.persistence.entity.Post;
import cgm.ojt.bulletin.web.form.PostForm;

public interface PostDao {

	int dbSavePost(Post post);

	void dbSavePostCategory(int id, int categoryId);

	List<Post> dbGetAllPost();

	Post dbGetPostById(Integer postId);

	void dbUpdatePost(Post postObject);

	List<Post> dbGetAllSearchPost(int currentPage, int recordsPerPage, PostForm postForm);

	List<Post> dbGetAllPostBySearchInput(String post_search);
}