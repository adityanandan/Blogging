package com.cognizant.blog.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.blog.exception.UserException;
import com.cognizant.blog.models.Blog;
import com.cognizant.blog.models.User;
import com.cognizant.blog.repository.BlogRepository;
import com.cognizant.blog.repository.UserRepository;
import com.cognizant.blog.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Override
	public Blog postBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public Blog postBlogByUsername(Blog blog, String username) throws UserException {
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new UserException("user not present");
		}
		blog.setUser(user.get());
		return blogRepository.save(blog);
	}

	@Override
	public Blog editBlog(Blog blog) {

		return blogRepository.save(blog);
	}

	@Override
	public void deleteBlog(Blog blog) {
		blogRepository.delete(blog);

	}

	@Override
	public List<Blog> getAllBlogs() {
		return blogRepository.findAll();
	}

	@Override
	public List<Blog> getAllBlogsByUsername(String username) {
		return blogRepository.findByUserUsername(username);
	}

	@Override
	public List<Blog> getAllBlogsByCategory(String category) {
		return blogRepository.findByCategory(category);
	}

	@Override
	public List<Blog> getAllBlogsInDuration(LocalDateTime StartDate, LocalDateTime EndDate) {
		return blogRepository.findByPostDateBetween(StartDate, EndDate);
	}

	@Override
	public void deleteBlogById(String blogId) {
		blogRepository.deleteById(blogId);
	}

	@Override
	public List<Blog> getAllBlogsInCategoryDuration(String category, LocalDateTime start, LocalDateTime end) {

		return blogRepository.findByCategoryAndPostDateBetween(category, start, end);
	}

}