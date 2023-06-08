<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>

<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">

</head>
<body class="wrapper" >
	<script type="text/javascript">
	//パターン1の色設定開始
		function color1() {
			document.bgColor = "#D19826"; // bgcolor：背景色
			document.fgColor = "#D19826"; // text：文字の基本色
			document.linkColor = "#D19826"; // link：リンク文字の色
			document.vlinkColor = "#D19826"; // vlink：リンク文字の色（アクセス済みのリンク）
			document.alinkColor = "#ff8000"; // alink：リンク文字の色（クリックした瞬間の色）
		}
	// パターン1の色設定終了
	// パターン2の色設定開始
		function color2() {
			document.bgColor = "#D1878B";
			document.fgColor = "#D1878B";
			document.linkColor = "#D1878B";
			document.vlinkColor = "#D1878B";
			document.alinkColor = "#ff8000";
		}
	// パターン2の色設定終了
	//パターン3
		function color3() {
			document.bgColor = "#67A2A0";
			document.fgColor = "#67A2A0";
			document.linkColor = "#67A2A0";
			document.vlinkColor = "#67A2A0";
			document.alinkColor = "#ff8000";
		}
	//パターン3終了
	</script>
    <header>
        <div class="left">
				<img class="mark" src="resources/img/logo.png" />
				<div class="logo">Metateam Library</div>
			<div>
				<button type="submit" value="🍊" class="buttonA" onClick="color1()">🍊</button>
			</div>
			<div>
				<button type="submit" value="🍓" class="buttonA" onClick="color2()">🍓</button>
			</div>
			<div>
				<button type="submit" value="🍈" class="buttonA" onClick="color3()">🍈</button>
			</div>
		</div>
		<div class="right"></div>
    </header>
    <main>
        <h1>Home</h1>
        <div id="drawerNavi" class="overlay">
  			<div id="drawerClose"><span class="lineClose"></span></div>
  			<ul class="overlay-content">
  				<li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
    			<li><a href="<%=request.getContextPath()%>/addBook">書籍の追加</a></li>
			    <li><a href="<%=request.getContextPath()%>/favorite">お気に入り一覧</a></li>
			    <li><a href="<%=request.getContextPath()%>/new">ゲーム</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
  			</ul>
		</div>
			<!-- drawer -->
		<button id="drawerOpen" class="drawerOpen">メニュー</button>
			<!-- drawer -->
    
	    <button id="button-open-dialog">絞り込む</button>
	    <dialog id="dialog-sample">
		  	<div id="dialog-container">
		    	<form id="form" method="dialog" class="dialog">
			      <p>ジャンルで絞り込む</p>
			      <li><a href="<%=request.getContextPath()%>/novel">小説</a></li>
			      <li><a href="<%=request.getContextPath()%>/comics">漫画</a></li>
			      <li><a href="<%=request.getContextPath()%>/business">ビジネス</a></li>
			      <li><a href="<%=request.getContextPath()%>/technology">専門書</a></li>
			      <li><a href="<%=request.getContextPath()%>/magazine">雑誌</a></li>
			      <button id="default" value="default">×</button>
		    	</form>
		  	</div>
		</dialog>
		<!-- モーダルのボタン -->
		<button id="modalView">検索</button>
		
		<div class="popup" id="firstTimeModal">
			<div class="popup-inner">
			    <!-- ここに検索フォーム -->
			    <form role="search" method="get" id="searchform" class="searchform" action="searchBook">
			    	<div>
			        	<input type="search" placeholder="書籍名" id="s" class="searchform__input" name="searchBook" value="" />
			        	<p align="right"><button type="submit" id="searchsubmit" class="searchform__submit"><i class="fa fa-search"></i></p></button>
			    	</div>
				</form>
			    <!-- ここまで検索フォーム -->
		 	</div>
  			<div class="black-background" id="js-black-bg"></div>
		</div>
    	
    	<div class="overlays"></div>
		  <nav class="nav">
		    <div class="toggle">
		      <span id="deleteconpo" class="toggler"></span>
		    </div>
		    <div class="logo">
		      <a href="#">LOGO</a>
		    </div>
		    <ul class="linkList">
		      <li><a href="#">Home</a></li>
		      <li><a href="#">About</a></li>
		      <li><a href="#">Projects</a></li>
		      <li><a href="#">Blog</a></li>
		      <li><a href="#">Contact</a></li>
		    </ul>
 		</nav>
 		
        <form method="GET" action="sortBooks">
			<select id="id_sort" name="sortBook" onchange="submit(this.form)">
				<option disabled selected hidden>並び替え</option>
 				<option value="sortASC">昇順</option>
		        <option value="sortDESC">降順</option>
		        <option value="sortAuthor">著者名順</option>
				<option value="sortDate">出版日順</option>
			</select>
		</form> 

        <div class="content_body">
        	<c:if test="${!empty emptyMessage}">
                <div class="error_msg">${emptyMessage}</div>
            </c:if>
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="get" class="book_thumnail" action="editBook">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <c:if test="${bookInfo.favorite!=1}">
            					<form method="GET" action="favoriteBook" name="favorite">
                					<p align="justify">
    								<button class="btn btn-primary addtofavorite">☆</button>
    								<input type="hidden" name="bookId" value="${bookInfo.bookId}">
                					</p>
            					</form>
        					</c:if><c:if test="${bookInfo.favorite==1}">
        						<form method="GET" action="unfavoriteBook" name="unfavorite">
                					<p align="justify">
                					<button class="btn btn-primary removefavorite hidden">★</button>
                					<input type="hidden" name="bookId" value="${bookInfo.bookId}">
                					</p>
            					</form>
                			</c:if>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                                <c:if test="${bookInfo.genre!=null}">
                                	<li class="book_publish_date">ジャンル：${bookInfo.genre}</li>
                                </c:if>
                                <c:if test="${bookInfo.genre==null}">
                                	<li class="book_publish_date">ジャンル：</li>
                                </c:if>
                            </ul>
                            <button onclick="location.href='https://www.amazon.co.jp/s?k=${bookInfo.title}&ref=nb_sb_noss'" >購入</button>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>

		<script  type="text/javascript">
		window.onload = function modal(){
			//ダイアログ
			const buttonOpenDialog = document.getElementById("button-open-dialog");
			const dialogSample = document.getElementById("dialog-sample");
			
			//ダイアログを開くイベント
			buttonOpenDialog.addEventListener("click", () => {
			  dialogSample.showModal();
			});
			
			//ダイアログのクリックイベント
			dialogSample.addEventListener('click', (event) => {
			  
			  if(event.target.closest('#dialog-container') === null) {
			    dialogSample.close();
			  }
			});
		
			//検索窓
			document.getElementById('modalView').addEventListener('click', () => {
				const modalbtn = document.getElementById('firstTimeModal');
				     modalbtn.classList.add('is-show');
				});
			
			document.getElementById('js-black-bg').addEventListener('click', () => {
				const modalClose = document.getElementById('firstTimeModal');
				     modalClose.classList.remove('is-show');
				});
			document.getElementById('modalCloseCloss').addEventListener('click', () => {
					const modalClose = document.getElementById('firstTimeModal');
				    modalClose.classList.remove('is-show');
				});
			}
		
	 	//ドロワー
			document.getElementById('drawerOpen').addEventListener('click', () => {
				  document.getElementById("drawerNavi").style.width = "100%";
			});
		
			document.getElementById('drawerClose').addEventListener('click', () => {
				document.getElementById("drawerNavi").style.width = "0%";
			});

			//ドロワー
			const toggler = document.querySelector(".toggle");

			window.addEventListener("click", event => {
			  if(event.target.className == "toggle" || event.target.className == "toggle") {
			    document.body.classList.toggle("show-nav");
			    document.getElementById("deleteconpo").classList.toggle("deleteclass")
			  } else if (event.target.className == "overlay") {
			    document.body.classList.remove("show-nav");
			document.getElementById("deleteconpo").classList.toggle("deleteclass")
			  }
			
			});
			
			
			//ドロワーのメニューをクリックしたら非表示
			const hrefLink = document.querySelectorAll('.linkList li a');
			for (i = 0; i < hrefLink.length; i++) {
			hrefLink[i].addEventListener("click", () => {
			document.body.classList.remove("show-nav");
			document.getElementById("deleteconpo").classList.toggle("deleteclass")
			});
			} 
		</script> 
	</body>
</html> 
