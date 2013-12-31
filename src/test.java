/**
 * This project is licensed under the terms of the MIT license,
 * you can read more in LICENSE.txt.
 *
 * test.java
 * Version		:	1.0.1
 * Author		:	Zack Urben
 * Contact		:	zackurben@gmail.com
 * Creation		:	12/29/13
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
 * Motivation BTC	@ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
 * Cex.io Referral	@ https://cex.io/r/0/kannibal3/0/
 * Cryptsy Trade Key@ e5447842f0b6605ad45ced133b4cdd5135a4838c
 * Other donations accepted via email request.
 */

public class test {
	public static void main(String[] args) {
		CexAPI test = new CexAPI("username", "api_key", "api_secret");

		// get current ticker for couple
		System.out.println("Testing the \"GHS/BTC\" ticker..!");
		System.out.println(test.ticker("GHS/BTC"));

		// get current order book for couple
		System.out.println("\nTesting the \"GHS/BTC\" order_book..!");
		System.out.println(test.order_book("GHS/BTC"));

		// check current trade history for couple, since given time.
		System.out.println("\nTesting the \"GHS/BTC\" trade_history..!");
		System.out.println(test.trade_history("GHS/BTC", 1));

		// check current account balance
		System.out.println("\nTesting the user balance..!");
		System.out.println(test.balance());

		// place order for couple with given amount at given price per GHS
		System.out.println("\nTesting the \"GHS/BTC\" place_order..!");
		String temp = test.place_order("GHS/BTC", "buy", 1f, 0.00000001f);
		System.out.println(temp);

		// display open orders for couple
		System.out.println("\nTesting the \"GHS/BTC\" open_orders..!");
		System.out.println(test.open_orders("GHS/BTC"));

		// cancel a current open order by id
		temp = temp.split(",")[0].split(":")[1].split("\"")[1];
		int order = Integer.parseInt(temp);
		System.out.println("\nTesting the \"GHS/BTC\" cancel_order..!");
		System.out.println("Cancel order (ID: " + order + "): " + test.cancel_order(order));
	}
}
