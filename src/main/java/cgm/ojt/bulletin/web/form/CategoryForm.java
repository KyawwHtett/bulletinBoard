package cgm.ojt.bulletin.web.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import cgm.ojt.bulletin.persistence.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryForm {
	private int category_id;
	@NotEmpty
	private String category_name;
	
	private Set<Post> category_posts;
	
	public CategoryForm() {
		super();
	}
}