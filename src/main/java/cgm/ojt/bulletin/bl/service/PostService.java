package cgm.ojt.bulletin.bl.service;

import java.util.List;

import cgm.ojt.bulletin.bl.dto.PostDto;

public interface PostService {
	void doSavePost(PostDto postDto);

	List<PostDto> doGetAllPost();
}