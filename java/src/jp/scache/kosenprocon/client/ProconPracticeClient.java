package jp.scache.kosenprocon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * <a href="http://procon2014-practice.oknct-ict.org/">プロコン練習場2014</a>のためのクライアントプログラムです。
 * <p>
 * <a href="https://github.com/higumachan/procon-practice2014_API">プロコン練習場2014 API詳細(github)</a>
 * @author <a href="https://twitter.com/scal_ch">sckm</a>
 *
 */
public class ProconPracticeClient {
	private final String BASE_URL = "http://procon2014-practice.oknct-ict.org/";

	/** GET Method用InputStream **/
	private InputStream responseInputStream = null;
	
	private HttpURLConnection postConnection = null;
	
	/** POST Method用OutStream **/
	private OutputStream postOutputStream = null;

	/** POST Method用InputStream **/
	private InputStream postInputStream = null;
	
	/** 問題番号 **/
	private int problemNumber;
	
	public static void main(String[] args) {
		ProconPracticeClient p = new ProconPracticeClient(1);
		
		InputStream in = p.getGetInputStream();
		Scanner sc = new Scanner(in);
		System.out.println(sc.nextLine());
		System.out.println(sc.nextLine());
		System.out.println(sc.nextLine());
		System.out.println(sc.nextLine());
		p.closeGetInputStream();
		
		String ans = "2\r\n11\r\n21\r\nURDDLLURRDLLUURDDLUUD\r\n11\r\n40\r\nURDLURLDLURDRDLURUDLURDLLRDLUURRDLLURRDL\r\n";
		HashMap<String, String> map = p.sendAnswer("testkpcp", "1234", ans);
		System.out.println("Status : " + map.get("status"));
		System.out.println(map.toString());
	}
	
	/**
	 * 
	 * @param number 問題番号
	 */
	public ProconPracticeClient(int number) {
		problemNumber = number;
	}

	/**
	 * 問題受信用InputStreamの取得
	 * @return
	 */
	public InputStream getGetInputStream() {
		try {
			URL url = new URL(BASE_URL + "problem/ppm/" + problemNumber);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			// リダイレクトを自動で許可
			connection.setInstanceFollowRedirects(true);

			responseInputStream = connection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseInputStream;
	}
	
	/**
	 * 問題受信用InputStreamをclose
	 */
	public void closeGetInputStream(){
		try {
			responseInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解答送信用OutputStreamの取得
	 * @return
	 */
	private OutputStream getPostOutputStream(){
		try {
			URL url = new URL(BASE_URL + "solve/json/" + problemNumber);
			postConnection = (HttpURLConnection)url.openConnection();

			postConnection.setDoOutput(true);
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			
			postOutputStream = postConnection.getOutputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return postOutputStream;
	}

	/**
	 * 解答送信用OutputStreamをclose
	 */
	private void closePostOutputStream(){
		try{
			postOutputStream.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 解答送信後の返答を受信するためのInputStreamを取得
	 * @return
	 */
	private InputStream getPostInputStream(){
		try {
			postInputStream = postConnection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return postInputStream;
	}
	
	/**
	 * 解答送信後の返答を受信するためのInputStreamをclose
	 */
	private void closePostInputStream(){
		try{
			postOutputStream.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 解答を送信する
	 * @param userName ユーザ名
	 * @param password パスワード
	 * @param answer 解答
	 * @return 解答送信後のレスポンス
	 */
	public HashMap<String, String> sendAnswer(String userName, String password, String answer){
		OutputStream out = getPostOutputStream();
		PrintWriter pw = new PrintWriter(out);
		
		String s = "username=" + userName;
		s += "&passwd=" + password;
		s += "&answer_text=" + answer;
		pw.print(s);
		pw.flush();
		pw.close();
		
		closePostOutputStream();
		return getResponse();
	}
	
	/**
	 * post後のレスポンス(json)を受け取ってHashMapにして返す
	 * @return
	 */
	private HashMap<String, String> getResponse(){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			InputStream in = getPostInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String response = "";
			String s="";
			while((s=br.readLine()) != null)
				response += s;
//			System.out.println(response);
			map = convertJsonToHashMap(response);
			br.close();
			closePostInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return map;
	}
	
	/**
	 * Json文字列をHashMapに変換(練習場用に使えればいいのでかなり適当)
	 * @param jsonStr
	 * @return
	 */
	private HashMap<String, String> convertJsonToHashMap(String jsonStr){
		HashMap<String, String> map = new HashMap<String, String>();
		String s = jsonStr.replaceAll("[{}\\s\"]", "");
		String[] strArray = s.split(",");
		for(int i=0;i<strArray.length;i++){
			String[] pair = strArray[i].split(":");
			map.put(pair[0], pair[1]);
		//	System.out.println(pair[0]+ " " + pair[1]);
		}
		
		return map;
		
	}
}
