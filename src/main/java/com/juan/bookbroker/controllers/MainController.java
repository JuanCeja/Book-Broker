package com.juan.bookbroker.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juan.bookbroker.models.Book;
import com.juan.bookbroker.models.LoginUser;
import com.juan.bookbroker.models.User;
import com.juan.bookbroker.services.BookService;
import com.juan.bookbroker.services.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userServ;
	@Autowired
	private BookService bookService;

////	=================================== REGISTER CONTROLLERS START ==============================

//	RENDER LOGIN AND REGISTRATION PAGE
	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}

	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
//    	RETRIEVE USER FROM SESSION
		Long userId = (Long) session.getAttribute("user_id");
//    	CHECK IF USER ID IS NULL
		if (userId == null) {
			return "redirect:/";
		} else {
//    		GO TO THE DATABASE TO RETRIEVE THE USER USING THE ID FROM SESSION
			User thisUser = userServ.findOne(userId);
			model.addAttribute("thisUser", thisUser);
			List<Book> books = bookService.allBooks();
			model.addAttribute("books", books);
			return "home.jsp";
		}
	}

//   POST METHOD FOR REGISTERING NEW USER
	@PostMapping("/register")
	public String createUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {
		userServ.register(newUser, result);
//    	CHECK FOR ERRORS IN RESULTS
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", newUser.getId());
			return "redirect:/home";
		}
	}

//	=================================== REGISTER CONTROLLERS END ==============================

//	=================================== LOGIN CONTROLLERS START ==============================

//  LOGIN USER
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult res, Model model,
			HttpSession session) {
		User user = userServ.login(newLogin, res);

		if (res.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", user.getId());
			return "redirect:/home";
		}
	}

//	=================================== LOGIN CONTROLLERS END ==============================

//  LOGOUT
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

//    ================================= BOOK CONTROLLERS START HERE ===============================

//	RENDER CREATE BOOK PAGE
	@GetMapping("/books/new")
	public String createBook(@ModelAttribute("newBook") Book newBook, Model model, HttpSession sesh) {
		Long userId = (Long) sesh.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		} else {
			User loggedInUser = userServ.findOne(userId);
			model.addAttribute("loggedInUser", loggedInUser);
			return "createBook.jsp";
		}
	}

//	POST METHOD CREATE BOOK
	@PostMapping("create/book")
	public String create (@Valid @ModelAttribute("newBook")Book book, BindingResult result, Model model, HttpSession sesh) {
		Long userId = (Long) sesh.getAttribute("user_id");
		if(userId == null) {
			return "redirect:/";
		} else {
			User loggedInUser = userServ.findOne(userId);
			model.addAttribute("loggedInUser", loggedInUser);
			if(result.hasErrors()) {
				return "createBook.jsp";
			} else {
				book.setUser(loggedInUser);
				bookService.createdBook(book);
				return "redirect:/home";
			}
		}
	}
	
//	SHOW INDIVIDUAL BOOK/INFO
	@GetMapping("/books/{id}")
	public String viewBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findBook(id);
		model.addAttribute("book", book);
		return "showBook.jsp";
	}
	
//	RENDER EDIT FORM PAGE
	@RequestMapping("/books/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findBook(id);
		model.addAttribute("editBook", book);
		return "edit.jsp";
	}
	
//	EDIT POST METHOD
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("editBook") Book editBook, BindingResult result, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("user_id");
		if (result.hasErrors()) {
			return "edit.jsp";
		} else {
			User loggedInUser = userServ.findOne(userId);
			model.addAttribute("loggedInUser", loggedInUser);
			editBook.setUser(loggedInUser);
			bookService.updateBook(editBook);
			return "redirect:/home";
		}
	}
	
//	DELETE BOOK METHOD
	@RequestMapping("/books/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id) {
		bookService.deleteBook(id);
		return "redirect:/home";
	}
	
//	BORROW BOOK
	@RequestMapping("/borrow/{id}")
	public String borrowBook(@PathVariable("id") Long id, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("user_id");
		User loggedInUser = userServ.findOne(userId);
		Book book = bookService.findBook(id);
		book.setBorrower(loggedInUser);
		model.addAttribute("book", book);
		model.addAttribute("user", loggedInUser);
		return "home.jsp";
	}

//    ================================= BOOK CONTROLLERS END HERE ===============================
}
