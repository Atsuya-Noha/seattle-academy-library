<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATSUYA GAME</title>
<body>
	<style>
		body {
  text-align: center;
}
main {
  display: inline-block;
  position: relative;
}

/* start */
#modal {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(31, 19, 19, 0.8);
  color: white;
}

#menu {
  display: flex;
  justify-content: space-evenly;
  margin-top: 220px;
}
h3,
p {
  color: white;
}
#menu p {
  width: 100px;
  font-size: 24px;
  padding: 3px;
  margin: 15px;
  border: solid 1px white;
  cursor: pointer;
  background: black;
  color: white;
}

/* game */
#board {
  margin: 0 auto;
  background: #555555;
}
td.cell {
  background: rgb(128, 216, 154);
  width: 46px;
  height: 46px;
  margin: 2px;
}
.cell div {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin: 0 auto;
}
.cell-6 > tr > td {
  width: 63px;
  height: 63px;
}
.cell-6 > tr > td > div {
  width: 50px;
  height: 50px;
}
.cell-4 > tr > td {
  width: 92px;
  height: 92px;
}
.cell-4 > tr > td > div {
  width: 70px;
  height: 70px;
}
.black {
  background: black;
}
.white {
  background: white;
}
h2 {
  text-align: center;
  margin: 5px 0;
}
#counter {
  margin: 0 auto;
  width: 350px;
  display: flex;
  line-height: 50px;
  font-weight: bold;
  justify-content: space-between;
}
#numBlack {
  font-size: 36px;
  margin: 5px;
  width: 100px;
  height: 50px;
  background: black;
  color: white;
  border-radius: 5px;
}
#numWhite {
  font-size: 36px;
  margin: 5px;
  width: 100px;
  height: 50px;
  border: solid 1px black;
  border-radius: 5px;
}

/* end */
#restartBtn {
  z-index: 1;
  opacity: 0;
  position: absolute;
  width: 100%;
  height: 50px;
  line-height: 50px;
  font-weight: bold;
  cursor: pointer;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -110%);
  color: black;
  background: white;
}
.hide {
  display: none;
}
	</style>
	<header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">ATSUGMAES</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>othello</title>
</head>

<body>
    <main>
        <div id="modal">
            <h3>オセロ</h2>
                <p>マスの数を選択してください</p>
                <div id="menu">
                    <p id="4" class="select">4x4</p>
                    <p id="6" class="select">6x6</p>
                    <p id="8" class="select">8x8</p>
                </div>
        </div>
        <div id="restartBtn" class="hide">クリックするとスタート画面に戻ります</div>
        <div id="content">

            <table id="board"></table>
        </div>
        <div id="counter" class="hide">
            <div id="numBlack"></div>
            <h2></h2>
            <div id="numWhite"></div>
        </div>
    </main>

    <script src="main.js"></script>
</body>

</html>

    <script>
    "use strict";

    const BLACK = 1,
      WHITE = -1;
    let data = [];
    let turn = true;
    const board = document.getElementById("board");
    const h2 = document.querySelector("h2");
    const counter = document.getElementById("counter");
    const modal = document.getElementById("modal");
    document.querySelectorAll(".select").forEach((value) => {
      value.addEventListener("click", start);
    });
    let cells = 8; // マスの数

    // スタート画面でマスの数が選択された時の処理
    function start(e) {
      cells = Number(e.target.id);
      board.innerHTML = "";
      init();
      modal.classList.add("hide");
    }

    // 初期化
    function init() {
      for (let i = 0; i < cells; i++) {
        const tr = document.createElement("tr");
        data[i] = Array(cells).fill(0);
        for (let j = 0; j < cells; j++) {
          const td = document.createElement("td");
          const disk = document.createElement("div");
          tr.appendChild(td);
          td.appendChild(disk);
          td.className = "cell";
          td.onclick = clicked;
        }
        board.appendChild(tr);
      }
      // マスの数によって石の初期配置を変える
      switch (cells) {
        case 4:
          board.classList.add("cell-4");
          putDisc(1, 1, WHITE);
          putDisc(2, 2, WHITE);
          putDisc(1, 2, BLACK);
          putDisc(2, 1, BLACK);
          break;
        case 6:
          board.classList.add("cell-6");
          putDisc(2, 2, WHITE);
          putDisc(3, 3, WHITE);
          putDisc(2, 3, BLACK);
          putDisc(3, 2, BLACK);
          break;
        case 8:
          putDisc(3, 3, WHITE);
          putDisc(4, 4, WHITE);
          putDisc(3, 4, BLACK);
          putDisc(4, 3, BLACK);
      }
      showTurn();
    }

    init();

    // 石を描画
    function putDisc(x, y, color) {
      board.rows[y].cells[x].firstChild.className =
        color === BLACK ? "black" : "white";
      board.rows[y].cells[x].animate(
        { opacity: [0.4, 1] },
        { duration: 700, fill: "forwards" }
      );
      data[y][x] = color;
    }

    // 手番などの表示
    function showTurn() {
      h2.textContent = turn ? "黒の番です" : "白の番です";
      let numWhite = 0,
        numBlack = 0,
        numEmpty = 0;
      for (let x = 0; x < cells; x++) {
        for (let y = 0; y < cells; y++) {
          if (data[x][y] === WHITE) {
            numWhite++;
          }
          if (data[x][y] === BLACK) {
            numBlack++;
          }
          if (data[x][y] === 0) {
            numEmpty++;
          }
        }
      }
      document.getElementById("numBlack").textContent = numBlack;
      document.getElementById("numWhite").textContent = numWhite;

      let blacDisk = checkReverse(BLACK);
      let whiteDisk = checkReverse(WHITE);

      if (numWhite + numBlack === cells * cells || (!blacDisk && !whiteDisk)) {
        if (numBlack > numWhite) {
          document.getElementById("numBlack").textContent = numBlack + numEmpty;
          h2.textContent = "黒の勝ち!!";
          restartBtn();
          showAnime();
        } else if (numBlack < numWhite) {
          document.getElementById("numWhite").textContent = numWhite + numEmpty;
          h2.textContent = "白の勝ち!!";
          restartBtn();
          showAnime();
        } else {
          h2.textContent = "引き分け";
          restartBtn();
          showAnime();
        }
        return;
      }
      if (!blacDisk && turn) {
        h2.textContent = "黒スキップ";
        showAnime();
        turn = !turn;
        setTimeout(showTurn, 2000);
        return;
      }
      if (!whiteDisk && !turn) {
        h2.textContent = "白スキップ";
        showAnime();
        turn = !turn;
        setTimeout(showTurn, 2000);
        return;
      }
    }

    // マスがクリックされた時の処理
    function clicked() {
      const color = turn ? BLACK : WHITE;
      const y = this.parentNode.rowIndex;
      const x = this.cellIndex;
      // マスに置けるかチェック
      if (data[y][x] !== 0) {
        return;
      }
      const result = checkPut(x, y, color);
      if (result.length > 0) {
        result.forEach((value) => {
          putDisc(value[0], value[1], color);
        });
        turn = !turn;
      }
      showTurn();
    }

    // 置いたマスの周囲8方向をチェック
    function checkPut(x, y, color) {
      let dx, dy;
      const opponentColor = color == BLACK ? WHITE : BLACK;
      let tmpReverseDisk = [];
      let reverseDisk = [];
      // 周囲8方向を調べる配列
      const direction = [
        [-1, 0], // 左
        [-1, 1], // 左下
        [0, 1], // 下
        [1, 1], // 右下
        [1, 0], // 右
        [1, -1], // 右上
        [0, -1], // 上
        [-1, -1], // 左上
      ];

      // すでに置いてある
      if (data[y][x] === BLACK || data[y][x] === WHITE) {
        return [];
      }
      // 置いた石の周りに違う色の石があるかチェック
      for (let i = 0; i < direction.length; i++) {
        dx = direction[i][0] + x;
        dy = direction[i][1] + y;
        if (
          dx >= 0 &&
          dy >= 0 &&
          dx <= cells - 1 &&
          dy <= cells - 1 &&
          opponentColor === data[dy][dx]
        ) {
          tmpReverseDisk.push([x, y]);
          tmpReverseDisk.push([dx, dy]);
          // 裏返せるかチェック
          while (true) {
            dx += direction[i][0];
            dy += direction[i][1];
            if (
              dx < 0 ||
              dy < 0 ||
              dx > cells - 1 ||
              dy > cells - 1 ||
              data[dy][dx] === 0
            ) {
              tmpReverseDisk = [];
              break;
            }
            if (opponentColor === data[dy][dx]) {
              tmpReverseDisk.push([dx, dy]);
            }
            if (color === data[dy][dx]) {
              reverseDisk = reverseDisk.concat(tmpReverseDisk);
              tmpReverseDisk = [];
              break;
            }
          }
        }
      }
      return reverseDisk;
    }

    // 裏返せる場所があるか確認
    function checkReverse(color) {
      for (let x = 0; x < cells; x++) {
        for (let y = 0; y < cells; y++) {
          const result = checkPut(x, y, color);
          console.log(result);
          if (result.length > 0) {
            return true;
          }
        }
      }
      return false;
    }

    // ゲーム終了画面
    function restartBtn() {
      const restartBtn = document.getElementById("restartBtn");
      restartBtn.classList.remove("hide");
      restartBtn.animate(
        { opacity: [1, 0.5, 1] },
        { delay: 2000, duration: 3000, iterations: "Infinity" }
      );

      restartBtn.addEventListener("click", () => {
        document.location.reload();
      });
    }
    function showAnime() {
      h2.animate({ opacity: [0, 1] }, { duration: 500, iterations: 4 });
    }

    </script>
</body>

</html>