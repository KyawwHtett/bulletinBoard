package cgm.ojt.bulletin.bl.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.persistence.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostDto {
	private int post_id;
	private String title;
	private String description;
	private int created_user_id;
	private Date created_at;
	private int updated_user_id;
	private Date updated_at;
	private int deleted_user_id;
	private Date deleted_at;
	private Set<Category> post_categories;
	private List<Category> categories;
	String[] category;
	
	public PostDto() {
		super();
	}

	public PostDto(Post post) {
		super();
		this.post_id = post.getPost_id();
		this.title = post.getTitle();
		this.description = post.getDescription();
		this.created_user_id = post.getCreated_user_id();
		this.created_at = post.getCreated_at();
		this.updated_user_id = post.getUpdated_user_id();
		this.updated_at = post.getUpdated_at();
		this.deleted_user_id = post.getDeleted_user_id();
		this.deleted_at = post.getDeleted_at();
		this.post_categories = post.getPost_categories();
	}
}