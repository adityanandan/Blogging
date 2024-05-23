package com.cognizant.blog.models;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blog")
public class Blog {
	@Id
	private String id;
	@Size(min=20,max=50)
	private String blogName;
	@Size(min=20,max=50)
	private String category;
	@Size(min=1000,max=5000)
	private String article;
	@CreatedDate
	private LocalDateTime postDate;
	private User user;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBlogName() {
		return blogName;
	}
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public LocalDateTime getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Blog [id=" + id + ", blogName=" + blogName + ", category=" + category + ", article=" + article
				+ ", postDate=" + postDate + ", user=" + user + "]";
	}
	
}
