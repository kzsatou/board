package com.example.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.board.repository.Post;
import com.example.board.repository.PostFactory;
import com.example.board.repository.PostRepository;

@Configuration
public class BoardEditServiceImpl implements BoardEditService {
	@Autowired
	private PostRepository repository;
	// PostRepository repository = new PostRepository();

	@Override
	@Bean
	public Optional<Post> edit(String id) {
		return repository.findById(id);
	}

	public Iterable<Post> all_post() {
		return repository.findAllByOrderByUpdatedDateDesc();
	}

	public void save_db(Post form) {
		repository.saveAndFlush(PostFactory.createPost(form));
		return;
	}

	public void update_db(Optional<Post> post, Post form) {
		repository.saveAndFlush(PostFactory.updatePost(post.get(), form));
		return;
	}

	public void delete_db(Optional<Post> post) {
		repository.saveAndFlush(PostFactory.deletePost(post.get()));
		return;
	}
}
