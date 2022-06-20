package cgm.ojt.bulletin.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.persistence.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostForm {
	private int post_id;

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	private int created_user_id;

	private Date created_at;

	private int updated_user_id;

	private Date updated_at;

	private int deleted_user_id;

	private Date deleted_at;

	private Set<Category> post_categories;
	
	private List<Category> categories;
	
	@NotEmpty
	String [] category;

	public PostForm() {
		super();
	}

	public PostForm(Post post) {
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