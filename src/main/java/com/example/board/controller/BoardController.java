package com.example.board.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.board.repository.Post;
import com.example.board.repository.PostFactory;
import com.example.board.repository.PostRepository;
//import com.example.board.repository.PostRepository;
//import com.example.board.service.BoardEditService;
import com.example.board.service.BoardEditServiceImpl;
import com.example.board.validation.GroupOrder;

/**
 * 掲示板のフロントコントローラー.
 */
@Controller
public class BoardController {

	/** 投稿の一覧 */
	/* インスタンスの生成,@Autowiredで別クラスを呼び出す */
	//@Autowired
	//private PostRepository repository;
	// private BoardEditService boardEditService;
	@Autowired
	private BoardEditServiceImpl boardListServiceImpl;
	// BoardEditServiceImpl boardListServiceImpl = new BoardEditServiceImpl();

	/**
	 * 一覧を表示する。
	 *
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("form", PostFactory.newPost());
		model = this.setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}

	/**
	 * 一覧を設定する。
	 *
	 * @param model モデル
	 * @return 一覧を設定したモデル
	 */
	private Model setList(Model model) {
		// Iterable<Post> list = repository.findAll();
		// Iterable<Post> list = repository.findAllByOrderByUpdatedDateDesc(); /** ビジネス層に処理を移す */
		Iterable<Post> list = boardListServiceImpl.all_post(); /** ビジネス層に処理を移す*/
		model.addAttribute("list", list);
		return model;
	}

	/**
	 * 登録する。
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST) // POST でアクセスされたリクエストを処理,内容を受け取る
	public String create(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			// repository.saveAndFlush(PostFactory.createPost(form)); /** ビジネス層で処理するかも */
			boardListServiceImpl.save_db(form); /**ビジネス層で処理する*/
			model.addAttribute("form", PostFactory.newPost());
		}
		model = this.setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}

	/**
	 * 編集する投稿を表示する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@ModelAttribute("form") Post form, Model model) {
		// Optional<Post> post = repository.findById(form.getId()); /** ビジネス層に記述 */
		Optional<Post> post = boardListServiceImpl.edit(form.getId()); /** ビジネス層で処理*/
		model.addAttribute("form", post);
		model = setList(model); /** ビジネス層に記述 */
		model.addAttribute("path", "update");
		return "layout";
	}

	/**
	 * 更新する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			// Optional<Post> post = repository.findById(form.getId()); /** ビジネス層? */
			Optional<Post> post = boardListServiceImpl.edit(form.getId()); /**ビジネス層で処理*/
			// repository.saveAndFlush(PostFactory.updatePost(post.get(), form)); /** ビジネス層? */
			boardListServiceImpl.update_db(post, form); /**ビジネス層で処理*/
		}
		// Optional<Post> post = repository.findById(form.getId());
		Optional<Post> post = boardListServiceImpl.edit(form.getId()); /**ビジネス層で処理*/
		// repository.saveAndFlush(PostFactory.updatePost(post.get(), form));
		boardListServiceImpl.update_db(post, form); /**ビジネス層で処理*/
		model.addAttribute("form", PostFactory.newPost()); // 新規の投稿で上書き
		model = setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}

	/**
	 * 削除する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute("form") Post form, Model model) {
		// Optional<Post> post = repository.findById(form.getId());
		Optional<Post> post = boardListServiceImpl.edit(form.getId()); /** ビジネス層で記述*/
		// repository.saveAndFlush(PostFactory.deletePost(post.get())); /** ビジネス層で記述 */
		boardListServiceImpl.delete_db(post); /** ビジネス層で処理*/
		model.addAttribute("form", PostFactory.newPost());
		model = setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}
}