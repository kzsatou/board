package com.example.board.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.board.repository.Post;

@Service
public interface BoardEditService {
	/** 編集 */
	Optional<Post> edit(String id);

	/** 投稿一覧 コントローラー層かも */
	Iterable<Post> all_post();

	/** 登録する コントローラー層かも */
	void save_db(Post form);

	/** 更新する コントローラー層かも */
	void update_db(Optional<Post> post, Post form);

	/** 削除する */
	void delete_db(Optional<Post> post);
}
