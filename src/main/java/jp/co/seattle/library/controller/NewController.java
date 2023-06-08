package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NewController {
	@RequestMapping(value = "/new", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String osero(Model model, Locale locale) {
		final Logger logger = LoggerFactory.getLogger(NewController.class);
		// デバッグ用ログ
				logger.info("newコントローラー", locale);
		return "newPage";
	}
}
