package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;





@Controller
public class ApiController{
	final static Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private BooksService booksService;
	
	/*
	＊ 対象書箱をファイル出力する
	* @param locale ロケール情報
	* @param bookId 書籍ID
	* @param model モデル情報
	* @return 還移先画面名
	*/
	
	@Transactional
	@RequestMapping(value = "/fromjsp", method = RequestMethod.POST)
	public String bookApiInfo(Locale locale, @RequestParam("bookId")int bookId, RedirectAttributes redirectAttributes, Model model){
		logger.info("APIcontroller", locale);
		
		//書籍情報取得
		BookDetailsInfo bookInfo = booksService.getBookInfo(bookId);
		
		//API呼び出し
		String responseMessage = booksService.callApi(locale, bookInfo);
		
		if (responseMessage.equals("書類出力に成功しました")) {
			redirectAttributes.addFlashAttribute("successMessage",responseMessage);
		} else {
			redirectAttributes.addFlashAttribute("errorMessage",responseMessage);
		}
		
		return "redirect:/editBook?bookId=" + bookId;
		
	}
}
 