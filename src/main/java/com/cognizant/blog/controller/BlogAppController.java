package com.cognizant.blog.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.blog.exception.ResourceAlreadyPresentException;
import com.cognizant.blog.exception.UserException;
import com.cognizant.blog.helper.UserResponse;
import com.cognizant.blog.messaging.ProducerService;
import com.cognizant.blog.models.Blog;
import com.cognizant.blog.models.User;
import com.cognizant.blog.service.BlogService;
import com.cognizant.blog.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/blogsite")
public class BlogAppController {

	@Autowired
	UserService userService;

	@Autowired
	BlogService blogService;

	@Autowired
	ProducerService producerService;

	Logger logger = LoggerFactory.getLogger(BlogAppController.class);

	@PostMapping("/user/register")
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		producerService.sendMessage("Registration request received for user: " + user.getUsername());
		try {
			User createUser = userService.createUser(user);
			return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		} catch (ResourceAlreadyPresentException message) {
			return new ResponseEntity<>(message.getMessage(), HttpStatus.CONFLICT);

		}
	}

	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody User user) {

		producerService.sendMessage("Login request received for user: " + user.getUsername());
		try {

			return new ResponseEntity<>(userService.loginUser(user.getUsername(), user.getPassword()), HttpStatus.OK);

		} catch (Exception e) {
			UserResponse userResponse = new UserResponse();
			userResponse.setLoginStatus("username does not exist");
			logger.info("User {} does not exist", user.getUsername());
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
	}

	@ResponseBody
	@GetMapping("/{username}/forgot")
	public Map<String, String> forgotPassword(@PathVariable("username") String username) {
		producerService.sendMessage("Forgot Password request received with username: " + username);
		return new HashMap<>(userService.forgotPassword(username));

	}

	@ResponseBody
	@PostMapping("/reset")
	public Map<String, String> resetUserPassword(@RequestBody User user) {
		producerService.sendMessage("Registration request received for user: " + user.getUsername());
		return new HashMap<>(userService.resetPassword(user.getUsername(), user.getPassword()));
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Blog>> getAllBlogs() {
		producerService.sendMessage("Received request to send all blog data.");
		return new ResponseEntity<>(blogService.getAllBlogs(), HttpStatus.OK);
	}

	@GetMapping("/users/all")
	public ResponseEntity<List<User>> getAllUsers() {
		producerService.sendMessage("Received request to send all user data.");
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/user/search/{username}")
	public ResponseEntity<List<User>> searchUser(@PathVariable("username") String username) {
		return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<List<Blog>> getAllBlogsByUser(@PathVariable("username") String username) {
		return new ResponseEntity<>(blogService.getAllBlogsByUsername(username), HttpStatus.OK);
	}
	
	@GetMapping("/blogs/info/{category}")
	public ResponseEntity<List<Blog>> getAllBlogsBycategory(@PathVariable("category") String category) {
		return new ResponseEntity<>(blogService.getAllBlogsByCategory(category), HttpStatus.OK);
	}


	@PostMapping("/{username}/add")
	public ResponseEntity<Object> postBlogByUser(@PathVariable("username") String username, @RequestBody Blog blog) {
		try {
			Blog postBlogByUsername = blogService.postBlogByUsername(blog, username);
			return new ResponseEntity<>(postBlogByUsername, HttpStatus.OK);
		} catch (UserException message) {
			return new ResponseEntity<>(message.getMessage(), HttpStatus.CONFLICT);

		}

	}

	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<Blog> updateBlogByUser(@PathVariable("username") String username,
			@PathVariable("id") String blogId, @RequestBody Blog blog) {
		return new ResponseEntity<>(blogService.editBlog(blog), HttpStatus.OK);
	}

	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<HttpStatus> deleteBlogByUser(@PathVariable("username") String username,
			@PathVariable("id") String blogId) {
		blogService.deleteBlogById(blogId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/blogs/get/{start}/{end}")
	public ResponseEntity<List<Blog>> getAllBlogsByDuration(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		return new ResponseEntity<>(blogService.getAllBlogsInDuration(start, end), HttpStatus.OK);
	}
	
	@GetMapping("/blogs/get/{category}/{start}/{end}")
	public ResponseEntity<List<Blog>> getAllBlogsByCategoryDuration(@PathVariable("category") String category,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		return new ResponseEntity<>(blogService.getAllBlogsInCategoryDuration(category,start, end), HttpStatus.OK);
	}
	


}
