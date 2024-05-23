package com.cognizant.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import com.cognizant.blog.exception.UserException;
import com.cognizant.blog.models.Blog;


public interface BlogService {
	
	Blog postBlog(Blog Blog);

	Blog postBlogByUsername(Blog Blog, String username) throws UserException;

	Blog editBlog(Blog Blog);

	void deleteBlog(Blog Blog);

	List<Blog> getAllBlogs();

	List<Blog> getAllBlogsByUsername(String username);
	
	List<Blog> getAllBlogsByCategory(String category);
	
	List<Blog> getAllBlogsInDuration(LocalDateTime StartDate, LocalDateTime EndDate);

	void deleteBlogById(String BlogId);

	List<Blog> getAllBlogsInCategoryDuration(String category, LocalDateTime start, LocalDateTime end);


}
