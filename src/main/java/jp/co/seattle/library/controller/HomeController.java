package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	//書籍0件のメッセージ
		@RequestMapping(value = "/home", method = RequestMethod.GET)
		public String transitionHome(Model model) {
			List<BookInfo> bookList = booksService.getBookList();
			if(bookList.isEmpty()) {
				model.addAttribute("emptyMessage", "書籍情報はありません");
			}else{
				model.addAttribute("bookList", bookList);
			}
			return "home";
		}
	
	
	//タスク7
	//書籍検索
	@RequestMapping(value = "/searchBook", method = RequestMethod.GET)
	public String searchBook(Model model, @RequestParam(name ="searchBook") String searchBook) {
		List<BookInfo> bookList = booksService.searchBook(searchBook);
		model.addAttribute("bookList", bookList);
		return "home";
	}
	
	
	@RequestMapping(value = "/sortASC", method = RequestMethod.GET)
	public String sortBookAsc(Model model){
		List<BookInfo> bookList = booksService.sortBookListAsc();
		model.addAttribute("bookList", bookList);
		return "home";
	}
	
	@RequestMapping(value = "/sortDESC", method = RequestMethod.GET)
	public String sortBookDesc(Model model) {
		List<BookInfo> bookList = booksService.sortBookListDesc();
		model.addAttribute("bookList", bookList);
		return "home";
	}

	@RequestMapping(value = "/sortAuthor", method = RequestMethod.GET)
	public String sortBookAuthor(Model model) {
		List<BookInfo> bookList = booksService.sortBookListAuthor();
		model.addAttribute("bookList", bookList);
		return "home";
	}
	@RequestMapping(value = "/sortPublishDate", method = RequestMethod.GET)
	public String sortBookDate(Model model) {
		List<BookInfo> bookList = booksService.sortBookListDate();
		model.addAttribute("bookList", bookList);
		return "home";
	}
}