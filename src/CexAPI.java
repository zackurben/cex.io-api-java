/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 *
 * CexAPI.java
 * Version		:	1.0.4
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
	private final String username;
	private final String apiKey;
	private final String apiSecret;
	private int nonce;
	
	public CexAPI(String user, String key, String secret) {
		this.username = user;
		this.apiKey = key;
		this.apiSecret = secret;
		this.nonce = (int) System.currentTimeMillis();
	}
	
	public String toString() {
		return "{\"username\":\"" + this.username + "\",\"apiKey\":\"" + this.apiKey + "\",\"apiSecret:\"" +
			this.apiSecret + "\",\"nonce:\"" + this.nonce + "\"}";
	}
	
	/**
	 * Create HMAC-SHA256 signature for our POST call.
	 * @return HMAC-SHA256 message for POST authentication.
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
			
			this.nonce++;
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

		return response;
	}
	
	private String apiCall(String method, String couple, String param, boolean auth) {
		method = method + "/";
		
		// if couple exists, add slash after it
		if(couple != "") {
			couple = couple + "/";
		}
		
		return this.post(("https://www.cex.io/api/" + method + couple), param, auth);
	}
	
	public String ticker(String couple) {
		return this.apiCall("ticker", couple, "", false);
	}
	
	public String order_book(String couple) {
		return this.apiCall("order_book", couple, "", false);
	}
	
	public String trade_history(String couple, int since) {
		return this.apiCall("trade_history", couple, ("since," + since), false);
	}
	
	public String balance() {
		return this.apiCall("balance", "", "", true);
	}
	
	public String open_orders(String couple) {
		return this.apiCall("open_orders", couple, "", true);
	}
	
	public String cancel_order(int id) {
		return this.apiCall("cancel_order", "", ("id," + id), true);
	}
	
	public String place_order(String couple, String type, float amount, float price) {
		return this.apiCall("place_order", couple, ("type," + type + ",amount," + amount + ",price," + price), true);
	}
}
