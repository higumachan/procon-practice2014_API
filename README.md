procon-practice2014_API
=======================

##高専プロコン競技練習場2014のAPIの解説ページ
  
###問題取得(ppm)  
<pre>
URL    : http://procon2014-practice.oknct-ict.org/problem/ppm/<問題番号>  
METHOD : GET  
PARAM  : なし  
コレでppm形式のファイルが落ちてきます。  
(例)  
  http://procon2014-practice.oknct-ict.org/problem/ppm/1  
</pre>
###回答投稿  
####H™Lバージョン
<pre>
URL    : http://procon2014-practice.oknct-ict.org/solve/<問題番号>  
METHOD : POST  
PARAM  : username    => 登録時のユーザ名  
         passwd      => 登録時のパスワード    
         answer_text => 回答のテキストファイル
</pre> 
####JSONバージョン 
<pre>
URL    : http://procon2014-practice.oknct-ict.org/solve/<問題番号>  
METHOD : POST  
PARAM  : 	username    => 登録時のユーザ名  
            passwd      => 登録時のパスワード  
         	answer_text => 回答のテキストファイル  
RESPONSE:  
<pre>
{
	"status": 回答のステータス,
	"mismatch_cells": 不一致セル数
	"swap_score": 交換スコア,
	"select_score": 選択スコア,
	"total_score": 合計スコア(小さいほど良い), 
}
</pre>
</pre>

###ラッパーのお話
僕はPythonしか書けないので各言語のラッパーとかを書いたりしたらPull Requestとかくれると嬉しいです！
