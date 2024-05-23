package com.cognizant.blog.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.blog.models.Blog;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
	
	List<Blog> findByUserUsername(String username);
	List<Blog> findByCategory(String category);
	List<Blog> findByPostDateBetween(LocalDateTime StartDate, LocalDateTime EndDate);
	List<Blog> findByCategoryAndPostDateBetween(String category, LocalDateTime start, LocalDateTime end);
	

}
