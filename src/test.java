/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 * 
 * test.java
 * 
 * <pre>
 * @version 2.0.0
 * @author  Zack Urben
 * @contact zackurben@gmail.com
 * 
 * @support
 * Motivation BTC    @ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
 * Cryptsy Trade Key @ e5447842f0b6605ad45ced133b4cdd5135a4838c
 * </pre>
 * 
 * This script requires a free API Key from Cex.io, which can be obtained
 * here: https://cex.io/trade/profile
 * This API Key requires the following permissions:
 * Account Balance, Place Order, Cancel Order, Open Order
 */

public class test {
  public static void main(String[] args) {
    CexAPI test = new CexAPI("username", "api_key", "api_secret");

    // Fetch the CexAPI account data.
    System.out.println("Testing User Data:");
    System.out.println(test.toString() + "\n");

    // Fetch the ticker data, for the currency pair.
    System.out.println("Testing method: ticker(\"GHS/BTC\"):");
    System.out.println(test.ticker("GHS/BTC") + "\n");

    // Fetch the last order price for the currency pair.
    System.out.println("Testing method: lastPrice(\"BTC\", \"USD\"):");
    System.out.println(test.lastPrice("BTC", "USD") + "\n");

    // Fetch the order book data, for the currency pair.
    System.out.println("Testing method: orderBook(\"GHS/BTC\"):");
    System.out.println(test.orderBook("GHS/BTC") + "\n");

    // Fetch the trade history data, for the currency pair.
    System.out.println("Testing method: tradeHistory(\"GHS/BTC\"):");
    System.out.println(test.tradeHistory("GHS/BTC", 1) + "\n");

    // Fetch the account balance data.
    System.out.println("Testing method: balance():");
    System.out.println(test.balance() + "\n");

    // Place an order, for the currency pair, with the given amount and price.
    System.out.println("Testing method: placeOrder(\"GHS/BTC\"):");
    String temp = test.placeOrder("GHS/BTC", "buy", 1f, 0.00010000f);
    System.out.println(temp + "\n");

    // Fetch the account open orders, for the currency pair.
    System.out.println("Testing method: openOrders(\"GHS/BTC\"):");
    System.out.println(test.openOrders("GHS/BTC") + "\n");

    // Cancel the account order with the given ID.
    temp = temp.split(",")[0].split(":")[1].split("\"")[1];
    System.out.println("Testing method: cancelOrder(" + temp + "):");
    System.out.println("Cancel order (ID: " + temp + "): " + test.cancelOrder(temp) + "\n");
    
    // Fetch the account open orders, for the currency pair.
    System.out.println("Testing method: hashrate():");
    System.out.println(test.hashrate() + "\n");
    
    // Fetch the account open orders, for the currency pair.
    System.out.println("Testing method: workers():");
    System.out.println(test.workers() + "\n");
  }
}
