/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 * 
 * CexAPI.java
 *
 * <pre>
 * @version 2.0.0
 * @author  Zack Urben
 * @contact zackurben@gmail.com
 * </pre>
 *
 * This script requires a free API Key from Cex.io, which can be obtained
 * here: https://cex.io/trade/profile
 * This API Key requires the following permissions:
 * Account Balance, Place Order, Cancel Order, Open Order
 * 
 * Support:
 * Motivation BTC @ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
 * Cryptsy Trade Key @ e5447842f0b6605ad45ced133b4cdd5135a4838c
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CexAPI {
  protected final String username;
  protected final String apiKey;
  protected final String apiSecret;
  protected int nonce;

  /**
   * Creates a CexAPI Object.
   * 
   * @param user
   * Cex.io Username
   * @param key
   * Cex.io API Key
   * @param secret
   * Cex.io API Secret
   */
  public CexAPI(String user, String key, String secret) {
    this.username = user;
    this.apiKey = key;
    this.apiSecret = secret;
    this.nonce = Integer.valueOf((int) (System.currentTimeMillis() / 1000));
  }

  /**
   * Debug the contents of the a CexAPI Object.
   * 
   * @return The CexAPI object data: username, apiKey, apiSecret, and nonce.
   */
  public String toString() {
    return "{\"username\":\"" + this.username + "\",\"apiKey\":\"" + this.apiKey
        + "\",\"apiSecret\":\"" + this.apiSecret + "\",\"nonce\":\"" + this.nonce + "\"}";
  }

  /**
   * Create HMAC-SHA256 signature for our POST call.
   * 
   * @return HMAC-SHA256 message for POST authentication.
   */
  private String signature() {
    ++this.nonce;
    String message = new String(this.nonce + this.username + this.apiKey);
    Mac hmac = null;

    try {
      hmac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key =
          new SecretKeySpec(((String) this.apiSecret).getBytes("UTF-8"), "HmacSHA256");
      hmac.init(secret_key);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return String.format("%X", new BigInteger(1, hmac.doFinal(message.getBytes())));
  }

  /**
   * Make a POST request to the Cex.io API, with the given data.
   * 
   * @param addr
   * HTTP Address to make the request.
   * @param param
   * Parameters to add to the POST request.
   * @param auth
   * Authentication required flag.
   * 
   * @return Result from POST sent to server.
   */
  private String post(String addr, String param, boolean auth) {
    boolean sent = false;
    String response = "";

    while (!sent) {
      sent = true;
      HttpURLConnection connection = null;
      DataOutputStream output = null;
      BufferedReader input = null;
      String charset = "UTF-8";

      try {
        connection = (HttpURLConnection) new URL(addr).openConnection();
        connection.setRequestProperty("User-Agent", "Cex.io Java API");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Charset", charset);

        if (auth) {
          // generate post variables and catch errors
          String tSig = this.signature();
          String tNon = String.valueOf(this.nonce);

          connection.setDoOutput(true);
          output = new DataOutputStream(connection.getOutputStream());
          String content =
              "key=" + URLEncoder.encode(this.apiKey, charset) + "&signature="
                  + URLEncoder.encode(tSig, charset) + "&nonce=" + URLEncoder.encode(tNon, charset);

          if (param.contains(",")) {
            String[] temp = param.split(",");

            for (int a = 0; a < temp.length; a += 2) {
              content += "&" + temp[a] + "=" + temp[a + 1] + "&";
            }

            content = content.substring(0, content.length() - 1);
          }

          output.writeBytes(content);
          output.flush();
          output.close();
        }

        String temp = "";
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((temp = input.readLine()) != null) {
          response += temp;
        }

        input.close();
      } catch (MalformedURLException e) {
        sent = false;
        e.printStackTrace();
      } catch (IOException e) {
        sent = false;
        e.printStackTrace();

        // This will happen if cloudflare is active/api is down.
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      }
    }

    return response;
  }

  /**
   * Wrapper for post method; builds the correct URL for the POST request.
   * 
   * @param method
   * Method for the POST request.
   * @param pair
   * Cex.io currency pair for the POST request.
   * @param param
   * Parameters to add to the POST request.
   * @param auth
   * Authentication required flag.
   * 
   * @return Result from POST sent to server.
   */
  private String apiCall(String method, String pair, String param, boolean auth) {
    // if pair exists, add slash after it
    if (pair != "") {
      pair = pair + "/";
    }

    return this.post(("https://cex.io/api/" + method + "/" + pair), param, auth);
  }

  /**
   * Fetch the ticker data, for the given currency pair.
   * 
   * @param pair
   * Cex.io currency pair for the POST request.
   * 
   * @return The public ticker data for the given pair.
   */
  public String ticker(String pair) {
    return this.apiCall("ticker", pair, "", false);
  }

  /**
   * Fetch the order book data, for the given currency pair.
   * 
   * @param pair
   * Cex.io currency pair for the POST request.
   * 
   * @return The public order book data for the given pair.
   */
  public String order_book(String pair) {
    return this.apiCall("order_book", pair, "", false);
  }

  /**
   * Fetch the trade history data, for the given currency pair.
   * 
   * @param pair
   * Cex.io currency pair for the POST request.
   * @param since
   * Unix time stamp to retrieve the data from.
   * 
   * @return The public trade history for the given pair (Currently limited to the last 1000
   * trades).
   */
  public String trade_history(String pair, int since) {
    return this.apiCall("trade_history", pair, ("since," + since), false);
  }

  /**
   * Fetch the account balance data, for the Cex.io API Object.
   * 
   * @return The account balance for all currency pairs.
   */
  public String balance() {
    return this.apiCall("balance", "", "", true);
  }

  /**
   * Fetch the accounts open orders, for the given currency pair.
   * 
   * @param pair
   * Cex.io currency pair for the POST request.
   * 
   * @return The account open orders for the currency pair.
   */
  public String open_orders(String pair) {
    return this.apiCall("open_orders", pair, "", true);
  }

  /**
   * Cancel the account order, with the given ID.
   * 
   * @param id
   * The order ID number
   * 
   * @return The boolean successfulness of the order cancellation:
   * (True/False).
   */
  public String cancel_order(int id) {
    return this.apiCall("cancel_order", "", ("id," + id), true);
  }

  /**
   * Place an order, via the Cex.io API, for the given currency pair, with the given amount and
   * price.
   * 
   * @param pair
   * Cex.io currency pair for the POST request.
   * @param type
   * Order type (buy/sell).
   * @param amount
   * Order amount.
   * @param price
   * Order price.
   * 
   * @return The order information, including: the order id, time, pending,
   * amount, type, and price.
   */
  public String place_order(String pair, String type, float amount, float price) {
    return this.apiCall("place_order", pair,
        ("type," + type + ",amount," + amount + ",price," + price), true);
  }
}
