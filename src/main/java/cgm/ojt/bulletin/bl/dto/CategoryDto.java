package cgm.ojt.bulletin.bl.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.persistence.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private int category_id;
	private String category_name;

	private Set<Post> category_posts;

	public CategoryDto() {
		super();
	}

	public CategoryDto(Category category) {
		super();
		this.category_id = category.getCategory_id();
		this.category_name = category.getCategory_name();
		this.category_posts = category.getCategory_posts();
	}
}