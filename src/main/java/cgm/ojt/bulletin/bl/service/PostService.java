package cgm.ojt.bulletin.bl.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cgm.ojt.bulletin.bl.dto.PostDto;
import cgm.ojt.bulletin.web.form.PostForm;

public interface PostService {
	void doSavePost(PostDto postDto, String postImgPath) throws IOException;

	List<PostDto> doGetAllPost() throws IOException;

	PostDto doGetPostById(Integer postId) throws IOException;

	void doUpdatePost(PostDto postDto, String postImgPath) throws IOException;

	List<PostDto> doGetAllSearchPost(int currentPage, int recordsPerPage, PostForm postForm) throws IOException;

	List<PostDto> doGetAllPostBySearchInput(String post_search);

	void doDownloadAllPost(HttpServletResponse response) throws IOException;
}