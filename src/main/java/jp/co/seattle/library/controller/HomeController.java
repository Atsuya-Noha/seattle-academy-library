package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Locale;

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
	
	
	@RequestMapping(value = "/sortBooks", method = RequestMethod.GET)
	public String sort(Model model, @RequestParam(name="sortBook") String sort) {
		if(sort.equals("sortASC")) {
			List<BookInfo> bookList = booksService.sortBookListAsc();
			model.addAttribute("bookList", bookList);
		}
		if(sort.equals("sortDESC")) {
			List<BookInfo> bookList = booksService.sortBookListDesc();
			model.addAttribute("bookList", bookList);
		}
		if(sort.equals("sortAuthor")) {
			List<BookInfo> bookList = booksService.sortBookListAuthor();
			model.addAttribute("bookList", bookList);
		}
		if(sort.equals("sortDate")) {
			List<BookInfo> bookList = booksService.sortBookListDate();
			model.addAttribute("bookList", bookList);
		}
		return "home";
	}

	/*
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
	*/
	
	//お気に入り機能
	//お気に入り追加
	@RequestMapping(value = "/favoriteBook", method = RequestMethod.GET)
	public String favoriteBook(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		//お気に入りに追加する
		booksService.favoriteBooks(bookId);
		// 一覧画面に遷移する
		return "redirect:/home";
	}
	//お気に入り削除
	@RequestMapping(value = "/unfavoriteBook", method = RequestMethod.GET)
	public String unfavoriteBook(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		//お気に入りから削除する
		booksService.unfavoriteBooks(bookId);
		// 一覧画面に遷移する
		return "redirect:/home";
	}
	
	
	
	//お気に入り一覧
//	@RequestMapping(value = "/favoriteList", method = RequestMethod.GET)
//	public String favoriteBooks(Locale locale, Model model) {
//		logger.info("fav", locale);
//		List<BookInfo> bookList = booksService.favoriteBooksList(locale);
//		model.addAttribute("bookList", bookList);
//		logger.info("favo", locale);
//		return "redirect:/home";
//	}
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	 public String favorite(Model model) {
	  List<BookInfo> bookList = booksService.Favorite();
	  model.addAttribute("bookList",bookList);
	  
	  return "home";
	  
	 }
	
	
	
	//ジャンル分け
	@RequestMapping(value = "/novel", method = RequestMethod.GET)
	 public String novel(Model model) {
	  List<BookInfo> bookList = booksService.novelList();
	  model.addAttribute("bookList",bookList);
	  return "home";
	 }
	
	@RequestMapping(value = "/comics", method = RequestMethod.GET)
	 public String comic(Model model) {
	  List<BookInfo> bookList = booksService.comicList();
	  model.addAttribute("bookList",bookList);
	  return "home";
	 }
	
	@RequestMapping(value = "/business", method = RequestMethod.GET)
	 public String business(Model model) {
	  List<BookInfo> bookList = booksService.businessList();
	  model.addAttribute("bookList",bookList);
	  return "home";
	 }
	
	@RequestMapping(value = "/technology", method = RequestMethod.GET)
	 public String technology(Model model) {
	  List<BookInfo> bookList = booksService.technologyList();
	  model.addAttribute("bookList",bookList);
	  return "home";
	 }
	
	@RequestMapping(value = "/magazine", method = RequestMethod.GET)
	 public String magazine(Model model) {
	  List<BookInfo> bookList = booksService.magazineList();
	  model.addAttribute("bookList",bookList);
	  return "home";
	 }
	
	//読了チェック

//	@RequestMapping(value = "/readStatus", method = RequestMethod.GET)
//	public String readStatus (@RequestParam("site${bookInfo.bookId}")String status,@RequestParam("bookId") int bookId, Model model) {
//	booksService.readStatus(bookId);
//	return "redirect:/home";
//	}
//	
}