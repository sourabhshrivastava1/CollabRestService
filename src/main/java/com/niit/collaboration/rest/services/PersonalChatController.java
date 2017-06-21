package com.niit.collaboration.rest.services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Collaboration.DAO.ChatDAO;
import com.niit.Collaboration.DAO.UserDAO;
import com.niit.Collaboration.model.Chat;
import com.niit.Collaboration.model.User;

@RestController
public class PersonalChatController {
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Chat chat;
	
	@Autowired
	private ChatDAO chatDAO;

	@Autowired
	private HttpSession session;
	
	@GetMapping("chat/{friend_id}")
	public ResponseEntity<List<Chat>> getAllchat(@PathVariable("friend_id") String friend_id) {
		user = (User) session.getAttribute("user");
		String userID = user.getId();
		List<Chat> chatList = chatDAO.getChatByFriend(userID,friend_id);

		return new ResponseEntity<List<Chat>>(chatList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "chating/save", method = RequestMethod.POST)
	public ResponseEntity<Chat> createBlog(@RequestBody Chat chat) {

		user = (User) session.getAttribute("user");
		chat.setUser_id(user.getId());
		chat.setDate_time(new Date());  
		//blog.setUser_id(user.getId());
		
			if (chatDAO.save(chat) == true) {
				System.out.println("chat saved successs-----");
				chat.setErrorCode("200");
				chat.setErrorMessage("Chat Created");
			} else {
				System.out.println("chat saved failed-----");
				chat.setErrorCode("404");
				chat.setErrorMessage("Failed to create Chat");
			}
			return new ResponseEntity<Chat>(chat, HttpStatus.OK);
		
	}

}
