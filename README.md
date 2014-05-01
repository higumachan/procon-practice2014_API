procon-practice2014_API
=======================

##高専プロコン競技練習場2014のAPIの解説ページ
  
###問題取得(ppm)  
URL    : http://procon2014-practice.oknct-ict.org/problem/ppm/<問題番号>  
METHOD : GET  
PARAM  : なし  
コレでppm形式のファイルが落ちてきます。  
(例)  
  http://procon2014-practice.oknct-ict.org/problem/ppm/1  

###回答投稿  
URL    : http://procon2014-practice.oknct-ict.org/solve/<問題番号>  
METHOD : POST  
PARAM  : username    => 登録時のユーザ名  
         passwd      => 登録時のパスワード    
         answer_text => 回答のテキストファイル  
