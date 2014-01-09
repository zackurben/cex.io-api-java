/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 *
 * CexAPI.java
 * Version		:	1.0.7
 * Author		:	Zack Urben
 * Contact		:	zackurben@gmail.com
 * Creation		:	12/29/13
 *
 * This script requires a free API Key from Cex.io, which can be obtained
 * here: https://cex.io/trade/profile
 * This API Key requires the following permissions:
 * Account Balance, Place Order, Cancel Order, Open Order
 *
 * Support:
 * Motivation BTC	@ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
 * Cex.io Referral	@ https://cex.io/r/0/kannibal3/0/
 * Cryptsy Trade Key@ e5447842f0b6605ad45ced133b4cdd5135a4838c
 * Other donations accepted via email request.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CexAPI {
	protected final String username;
	protected final String apiKey;
	protected final String apiSecret;
	private int nonce;
	
	/**
	 * Creates a CexAPI Object.
	 * 
	 * @param user (String) = Cex.io Username
	 * @param key (String) = Cex.io API Key
	 * @param secret (String) = Cex.io API Secret
	 */
	public CexAPI(String user, String key, String secret) {
		this.username = user;
		this.apiKey = key;
		this.apiSecret = secret;
		this.nonce = (int) System.currentTimeMillis();
	}
	
	/**
	 * Debug the contents of the a CexAPI Object.
	 * 
	 * @return (String) = The CexAPI object data: username, apiKey,
	 * apiSecret, and nonce.
	 */
	public String toString() {
		return "{\"username\":\"" + this.username + "\",\"apiKey\":\"" + this.apiKey + "\",\"apiSecret:\"" +
			this.apiSecret + "\",\"nonce:\"" + this.nonce + "\"}";
	}
	
	/**
	 * Create HMAC-SHA256 signature for our POST call.
	 * 
	 * @return (String) = HMAC-SHA256 message for POST authentication.
	 */
	private String signature() {
		String message = new String(this.nonce + this.username + this.apiKey);
		Mac hmac = null;
		
		try {
			hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(((String) this.apiSecret).getBytes(), "HmacSHA256");
			hmac.init(secret_key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return String.format("%X", new BigInteger(1, hmac.doFinal(message.getBytes())));
	}
	
	/**
	 * Make a POST request to the Cex.io API, with the given data.
	 * 
	 * @param addr (String) = HTTP Address to make the request.
	 * @param param (String) = Parameters to add to the POST request.
	 * @param auth (Boolean) = Authentication required flag.
	 * @return (String) = Result from POST sent to server.
	 */
	private String post(String addr, String param, boolean auth) {		
		URLConnection connection = null;
		DataOutputStream output = null;
		BufferedReader input = null;
		String charset = "UTF-8";
		
		try {
			connection = new URL(addr).openConnection();
			connection.setRequestProperty("User-Agent", "Cex.io Java API");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Charset", charset);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// generate post variables and catch errors
		if(auth) {
			try {
				String tSig = this.signature();
				String tNon = String.valueOf(this.nonce);
				
				connection.setDoOutput(true);
				output = new DataOutputStream(connection.getOutputStream());
				String content = "key=" + URLEncoder.encode(this.apiKey, charset) + "&signature=" +
						URLEncoder.encode(tSig, charset) + "&nonce=" + URLEncoder.encode(tNon, charset);
				
				if(param.contains(",")) {
					String[] temp = param.split(",");
					
					for(int a = 0; a < temp.length; a+=2){
						content += "&" + temp[a] + "=" + temp[a+1] + "&";
					}
					
					content = content.substring(0, content.length()-1);
				}
				
				output.writeBytes(content);
				output.flush();
				output.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String response = "";
		String temp = "";
		try {
			input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while((temp = input.readLine()) != null) {
				response += temp;
			}
			
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.nonce++;
		return response;
	}
	
	/**
	 * Wrapper for post method; builds the correct URL for the POST request.
	 * 
	 * @param method (String) = Method for the POST request.
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @param param (String) = Parameters to add to the POST request.
	 * @param auth (Boolean) = Authentication required flag.
	 * @return (String) = Result from POST sent to server.
	 */
	private String apiCall(String method, String pair, String param, boolean auth) {
		method = method + "/";
		
		// if pair exists, add slash after it
		if(pair != "") {
			pair = pair + "/";
		}
		
		return this.post(("https://www.cex.io/api/" + method + pair), param, auth);
	}
	
	/**
	 * Fetch the ticker data, for the given currency pair.
	 * 
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @return (String) = The public ticker data for the given pair.
	 */
	public String ticker(String pair) {
		return this.apiCall("ticker", pair, "", false);
	}
	
	/**
	 * Fetch the order book data, for the given currency pair.
	 * 
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @return (String) = The public order book data for the given pair.
	 */
	public String order_book(String pair) {
		return this.apiCall("order_book", pair, "", false);
	}
	
	/**
	 * Fetch the trade history data, for the given currency pair.
	 * 
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @param since (Int) = Unix time stamp to retrieve the data from.  
	 * @return (String) = The public trade history for the given pair
	 * (Currently limited to the last 1000 trades).
	 */
	public String trade_history(String pair, int since) {
		return this.apiCall("trade_history", pair, ("since," + since), false);
	}
	
	/**
	 * Fetch the account balance data, for the Cex.io API Object.
	 *   
	 * @return (String) = The account balance for all currency pairs.
	 */
	public String balance() {
		return this.apiCall("balance", "", "", true);
	}
	
	/**
	 * Fetch the accounts open orders, for the given currency pair.
	 * 
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @return (String) The account open orders for the currency pair.
	 */
	public String open_orders(String pair) {
		return this.apiCall("open_orders", pair, "", true);
	}
	
	/**
	 * Cancel the account order, with the given ID. 
	 * 
	 * @param id (Int) = The order ID number
	 * @return (String) = The successfulness of the order cancellation:
	 * (True/False).
	 */
	public String cancel_order(int id) {
		return this.apiCall("cancel_order", "", ("id," + id), true);
	}
	
	/**
	 * Place an order, via the Cex.io API, for the given currency pair,
	 * with the given amount and price.
	 * 
	 * @param pair (String) = Cex.io currency pair for the POST request.
	 * @param type (String) = Order type (buy/sell). 
	 * @param amount (Float) = Order amount.
	 * @param price (Float) = Order price.
	 * @return (String) = The order information, including: the order
	 * id, time, pending, amount, type, and price.
	 */
	public String place_order(String pair, String type, float amount, float price) {
		return this.apiCall("place_order", pair, ("type," + type + ",amount," + amount + ",price," + price), true);
	}
}
