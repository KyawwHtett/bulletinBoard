package cgm.ojt.bulletin.persistence.dao;

import java.util.List;

import cgm.ojt.bulletin.persistence.entity.Post;

public interface PostDao {

	int dbSavePost(Post post);

	void dbSavePostCategory(int id, int categoryId);

	List<Post> dbGetAllPost();
}