import java.io.IOException;
import java.net.MalformedURLException;

public class test {
	public static void main(String[] args) throws MalformedURLException, IOException {
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
