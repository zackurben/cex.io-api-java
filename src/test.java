/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 * 
 * test.java
 * Version  : 1.0.3
 * Author   : Zack Urben
 * Contact  : zackurben@gmail.com
 * Creation : 12/29/13
 * 
 * This script requires a free API Key from Cex.io, which can be obtained
 * here: https://cex.io/trade/profile
 * This API Key requires the following permissions:
 * Account Balance, Place Order, Cancel Order, Open Order
 * 
 * This program requires the free Cex.io Java API, which can be obtained
 * here:
 * 
 * Support:
 * Motivation BTC       @ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
 * Cex.io Referral      @ https://cex.io/r/0/kannibal3/0/
 * Scrypt Referral      @ http://scrypt.cc?ref=baaah
 * Cryptsy Trade Key    @ e5447842f0b6605ad45ced133b4cdd5135a4838c
 * Other donations accepted via email request.
 */

public class test {
    public static void main(String[] args) {
        CexAPI test = new CexAPI("username", "api_key", "api_secret");

        // Fetch the CexAPI object data
        System.out.println("Displaying the user data..!");
        System.out.println(test.toString());

        // Fetch the ticker data, for the currency pair.
        System.out.println("\nTesting the \"GHS/BTC\" ticker..!");
        System.out.println(test.ticker("GHS/BTC"));

        // Fetch the order book data, for the currency pair.
        System.out.println("\nTesting the \"GHS/BTC\" order_book..!");
        System.out.println(test.order_book("GHS/BTC"));

        // Fetch the trade history data, for the currency pair.
        System.out.println("\nTesting the \"GHS/BTC\" trade_history..!");
        System.out.println(test.trade_history("GHS/BTC", 1));

        // Fetch the account balance data.
        System.out.println("\nTesting the user balance..!");
        System.out.println(test.balance());

        // Place an order, for the currency pair, with the given amount and
        // price.
        System.out.println("\nTesting the \"GHS/BTC\" place_order..!");
        String temp = test.place_order("GHS/BTC", "buy", 1f, 0.00000001f);
        System.out.println(temp);

        // Fetch the account open orders, for the currency pair.
        System.out.println("\nTesting the \"GHS/BTC\" open_orders..!");
        System.out.println(test.open_orders("GHS/BTC"));

        // Cancel the account order with the given ID.
        temp = temp.split(",")[0].split(":")[1].split("\"")[1];
        int order = Integer.parseInt(temp);
        System.out.println("\nTesting the \"GHS/BTC\" cancel_order..!");
        System.out.println("Cancel order (ID: " + order + "): "
            + test.cancel_order(order));
    }
}
