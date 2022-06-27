package cgm.ojt.bulletin.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cgm.ojt.bulletin.bl.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private int post_id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;
	
	@Column(name = "post_img")
	private String post_img;

	@Column(name = "created_user_id")
	private int created_user_id;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_user_id")
	private int updated_user_id;

	@Column(name = "updated_at")
	private Date updated_at;

	@Column(name = "deleted_user_id")
	private int deleted_user_id;

	@Column(name = "deleted_at")
	private Date deleted_at;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "post_categories", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	Set<Category> post_categories;

	public Post() {
		super();
	}
	
	public Post(PostDto postDto) {
		super();
		this.post_id = postDto.getPost_id();
		this.title = postDto.getTitle();
		this.description = postDto.getDescription();
		this.created_user_id = postDto.getCreated_user_id();
		this.created_at = postDto.getCreated_at();
		this.updated_user_id = postDto.getUpdated_user_id();
		this.deleted_user_id = postDto.getDeleted_user_id();
		this.deleted_at = postDto.getDeleted_at();
	}
//	@OneToMany(mappedBy = "post")
//    Set<Post_Category> post_categories = new HashSet<Post_Category>();
}