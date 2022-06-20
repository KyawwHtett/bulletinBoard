package cgm.ojt.bulletin.persistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
import cgm.ojt.bulletin.web.form.CategoryForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int category_id;
	@Column(name = "category_name")
	private String category_name;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "post_categories")
	Set<Post> category_posts;

	public Category() {
		super();
	}

	public Category(CategoryForm categoryForm) {
		super();
		this.category_id = categoryForm.getCategory_id();
		this.category_name = categoryForm.getCategory_name();
		this.category_posts = categoryForm.getCategory_posts();
	}

	public Category(CategoryDto categoryDto) {
		super();
		this.category_id = categoryDto.getCategory_id();
		this.category_name = categoryDto.getCategory_name();
		this.category_posts = categoryDto.getCategory_posts();
	}
//	@OneToMany(mappedBy = "category")
//    Set<Post_Category> category_posts;
}