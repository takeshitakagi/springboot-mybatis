package com.example.demo.controller;

//git 練習

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserMapper userMapper;

	@Autowired
	public UserController(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * 一覧画面の表示
	 */
	@GetMapping // URLが/usersの時下のgetUsersメソッドが呼び出される
	public String getUsers(Model model) { // ここのmodelはなんですか？
		List<User> users = userMapper.selectAll(); // usersリストにuserの全てがselectされる、sql文はUserMapperに書かれたもの
		model.addAttribute("users", users); //list.htmlの変数usersの中に上のusersリストを入れる
		return "users/list"; // list.htmlを返す
	}

	/**
	 * 詳細画面の表示
	 */
	@GetMapping("{id}")
	public String getUser(@PathVariable int id, Model model) {
		User user = userMapper.select(id);
		model.addAttribute("user", user);
		return "users/show";
	}

	/**
	 * 新規作成画面の表示
	 */
	@GetMapping("new")
	public String getUserNew(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "users/new";
	}

	/**
	 * 新規作成画面の挿入処理
	 */
	@PostMapping
	public String postUserCreate(@ModelAttribute @Valid User user,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/new";
		}
		userMapper.insert(user);
		return "redirect:/users";
	}

	/**
	 * 編集画面の表示
	 */
	@GetMapping("{id}/edit")
	public String getUserEdit(@PathVariable int id, Model model) {
		User user = userMapper.select(id);
		model.addAttribute("user", user);
		return "users/edit";
	}

	/**
	 * 編集画面の更新処理
	 */
	@PutMapping("{id}")
	public String putUserEdit(@PathVariable int id, @ModelAttribute @Valid User user,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/edit";
		}
		user.setId(id);
		userMapper.update(user);
		return "redirect:/users";
	}

	/**
	 * 削除処理
	 */
	@DeleteMapping("{id}")
	public String deleteUser(@PathVariable int id, Model model) {
		userMapper.delete(id);
		return "redirect:/users";
	}

}
