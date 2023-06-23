package jp.co.seattle.library.service;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books",
				new BookInfoRowMapper());
			
		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name, favorite, genre FROM books WHERE books.id = ? ORDER BY title ASC;";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, favorite, genre, reg_date, upd_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now()) RETURNING id;";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getThumbnailName(),
				bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getFavorite(), bookInfo.getGenre());
		return bookId;
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, isbn = ?, description = ?, genre = ?, upd_date = now() WHERE id = ?; ";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getGenre(), bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, thumbnail_name = ?, thumbnail_url = ?, isbn = ?, description = ?, genre = ?, upd_date = now() WHERE id = ?; ";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(),
					bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getGenre(), bookInfo.getBookId());
			}	
		}
	//タスク7 検索窓
	/**
	 * 
	 * @param searchBook
	 * @retun
	 */
	public List<BookInfo> searchBook(String searchBook) {
				String sql = "SELECT * FROM books WHERE title LIKE ? ORDER BY title ASC ";
				String search = "%" + searchBook + "%";
				List<BookInfo> searchBookList = jdbcTemplate.query(sql, new BookInfoRowMapper(), search);
				
		return searchBookList;
	}
	
	public List<BookInfo> sortBookListAsc() {
		// タスク7昇順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title ASC ",
				new BookInfoRowMapper());
		return getedBookList;
	}
	
	public List<BookInfo> sortBookListDesc() {
		// タスク7降順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title DESC ",
				new BookInfoRowMapper());
		return getedBookList;
	}
	
	public List<BookInfo> sortBookListAuthor() {
		// タスク7著者名順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY author ASC ",
				new BookInfoRowMapper());
		return getedBookList;
	}
	
	public List<BookInfo> sortBookListDate() {
		// タスク7著者名順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date ASC ",
				new BookInfoRowMapper());	
		return getedBookList;
	}
	
	//お気に入り追加
	/**
	* @param bookInfo
	*/
	public void favoriteBooks(int bookId) {
		String sql;
			sql = "UPDATE books SET favorite = 1 WHERE id = ?; ";
			jdbcTemplate.update(sql, bookId);
	}
	//お気に入り削除
		/**
		* @param bookInfo
		*/
	public void unfavoriteBooks(int bookId) {
		String sql;
			sql = "UPDATE books SET favorite =0 WHERE id = ?; ";
			jdbcTemplate.update(sql, bookId);
	}
	
	//お気に入り一覧
//	public List<BookInfo> favoriteBooksList(Locale locale) {
//		List<BookInfo> BookList = jdbcTemplate.query(
//				"SELECT * FROM books WHERE favorite = 1;",
//				new BookInfoRowMapper());	
//		logger.info("favori", locale);
//		return BookList;
//	}
	public List<BookInfo> Favorite() {
		  // favorite
		  List<BookInfo> getedBookList = jdbcTemplate.query(
		    "SELECT * FROM books WHERE favorite = 1;",
		  new BookInfoRowMapper());
		  return getedBookList;
		 }
	
	
	//ジャンル分け
//	public List<BookInfo> novelList() {
//		List<BookInfo> getedBookList = jdbcTemplate.query(
//				"SELECT * FROM books WHERE genre = '小説'; ",
//				new BookInfoRowMapper());
//		return getedBookList;
//	}
	public List<BookInfo> novelList() {
		  // favorite
		  List<BookInfo> getedBookList = jdbcTemplate.query(
		    "SELECT * FROM books WHERE genre = '小説' ;",
		  new BookInfoRowMapper());
		  return getedBookList;
		 }
	
	public List<BookInfo> comicList() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE genre = '漫画' ;",
				new BookInfoRowMapper());
		return getedBookList;
	}
	public List<BookInfo> businessList() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE genre = 'ビジネス' ;",
				new BookInfoRowMapper());
		return getedBookList;
	}
	public List<BookInfo> technologyList() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE genre = '専門書' ;",
				new BookInfoRowMapper());
		return getedBookList;
	}
	public List<BookInfo> magazineList() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE genre = '雑誌' ;",
				new BookInfoRowMapper());
		return getedBookList;
	}
	
//	//読了チェック
//	public void readStatus(int bookId) {
//	jdbcTemplate.update("UPDATE books SET status=? WHERE id= ? ;");
//	// デバッグ用ログ
//	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * 
	 * @param locale
	 * @param bookInfo
	 * @return メッセージ
	 */
	
	public String callApi(Locale locale, BookDetailsInfo bookInfo) {
		
		ResourceBundle rb = ResourceBundle.getBundle("api");
		String url = rb.getString("url");
		logger.info("Api呼び出し", locale);
		
		try {
			//API
			String responseMessage = restTemplate.postForObject(url, bookInfo, String.class);
			return responseMessage;
			
			} catch (Exception e) {
			e.printStackTrace () ;
			return "API接続に失敗しました";
			}
	}
}
