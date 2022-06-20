//package cgm.ojt.bulletin.persistence.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import cgm.ojt.bulletin.web.form.PostForm;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//@AllArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "posts_categories")
//public class Post_Category {
//	@Id
//	private int id;
//
//	@ManyToOne
//	@JoinColumn(name = "post_id")
//	Post post;
//
//	@ManyToOne
//	@JoinColumn(name = "category_id")
//	Category category;
//	
//	public Post_Category() {
//		super();
//	}
//	
//	public Post_Category(PostForm postForm) {
//		super();
//	}
//}