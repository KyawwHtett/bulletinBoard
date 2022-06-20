package cgm.ojt.bulletin.bl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.dto.PostDto;
import cgm.ojt.bulletin.bl.service.PostService;
import cgm.ojt.bulletin.persistence.dao.CategoryDao;
import cgm.ojt.bulletin.persistence.dao.PostDao;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.persistence.entity.Post;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void doSavePost(PostDto postDto) {
		this.postDao.dbSavePost(this.getPostObject(postDto));
//		for (String categoryId : postDto.getCategory()) {
//			categoryId = categoryId.replaceAll("[^\\d.]", "");
//			this.postDao.dbSavePostCategory(id, Integer.parseInt(categoryId));
//		}
		
		
//		List<Category> categoryList = this.categoryService.doGetAllCategory();

//		for (Category category : categoryList) {
//			categoryListMap.put(category.getCategory_id(), category.getCategory_name());
//		}
	}

	private Post getPostObject(PostDto postDto) {
		Set<Category> categoryListMap = new HashSet<Category>();
		List<Category> categoryList = this.categoryDao.dbGetAllCategory();
		
		for (String categoryId : postDto.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");
			System.out.println(categoryId);
			
			for (Category category : categoryList) {
				if (Integer.parseInt(categoryId) == category.getCategory_id()) {
					categoryListMap.add(category);
				}
			}
		}
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setCreated_user_id(1);
		post.setCreated_at(new Date());
		post.setPost_categories(categoryListMap);
		return post;
	}

	@Override
	public List<PostDto> doGetAllPost() {
		List<Post> listPost = this.postDao.dbGetAllPost();
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		for (Post post : listPost) {
			PostDto postDto = new PostDto();
			postDto.setPost_id(post.getPost_id());
			postDto.setTitle(post.getTitle());
			postDto.setDescription(post.getDescription());
			postDto.setPost_categories(post.getPost_categories());
			postDtoList.add(postDto);
		}
		return postDtoList;
	}
}