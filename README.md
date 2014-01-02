#Cex.io Java API
The Java source files and examples for the Cex.io API. This is an opensource project under the MIT license.

## Contact
* Author: Zack Urben
* Contact: zackurben@gmail.com

### Support
If you would like to support the development of this project, please spread the word and donate!

* Motivation BTC	@ 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
* Cex.io referral	@ https://cex.io/r/0/kannibal3/0/
* Cryptsy Trade Key	@ e5447842f0b6605ad45ced133b4cdd5135a4838c
* Other donations accepted via email request!

**Additional Windows help on the [Project Wiki](https://github.com/zackurben/cex.io-api-java/wiki/Windows-Setup:-Basic-Help).**

##How to use:
1. Download this API source.
2. Generate a Cex.io API key and API secret (https://cex.io/trade/profile).
    This key needs the following permissions, to enable full functionality:
  * Account Balance
  * Open Order
  * Place Order
  * Cancel Order 
3. Create your Java project
4. Add the API to your project directory.
5. Create an API object:

```java 
CexAPI test = new CexAPI("username", "api_key", "api_secret");
```

```java 
				// API Object Parameters:
"username"		// Your Cex.io username
"api_key"		// Your Cex.io API key
"api_secret"	// Your Cex.io API secret
```

##Methods and Parameters:
Parameters:

```java 
			// Description (Data Type): Value range.
"pair"		// Currency pair (String): "GHS/BTC", "LTC/BTC", "NMC/BTC", "GHS/NMC", "BF1/BTC"
"since"		// Timestamp (Integer): 1 - 2147483647
"order_id"	// Order Number (Integer): TODO
"type"		// Order Type (String): "buy", "sell"
"amount"	// Order Quantitity (Float): 0.00000001 - 9223372036854775807
"price"		// Order Price (Float): 0.00000001 - 9223372036854775807
```

Methods:

```java 
// Method Format, with required parameters:
   
// Fetch the ticker data, for the currency pair.
test.ticker("pair");

// Fetch the order book data, for the currency pair.
test.order_book("pair");

// Fetch the trade history data, for the currency pair.
test.trade_history("pair", "order_id");

// Fetch the account balance data.
test.balance();

// Place an order, for the currency pair, with the given amount and price.
test.place_order("pair", "type", "amount", "price");

// Fetch the account open orders, for the currency pair.
test.open_orders("pair");

// Cancel the account order with the given ID.
test.cancel_order("order_id");
```
 
##Examples with Output:
Create API object:

```java 
CexAPI test = new CexAPI("username", "api_key", "api_secret");
// Call API methods here
```

Fetch the ticker data, for the currency pair "GHS/BTC":

```java
System.out.println(test.ticker("GHS/BTC"));
```

```json
{"timestamp":"1388458858","low":"0.03155","high":"0.041784","last":"0.03524828","volume":"93155.07390370","bid":"0.03524828","ask":"0.035248290000000002"}
```

Fetch the order book data, for the currency pair "GHS/BTC" (Most results removed for length):

```java
System.out.println(test.order_book("GHS/BTC"));
```

```json
{"timestamp":"1388458859","bids":[[0.03524828,"0.09861694"]],"asks":[[0.03524829,"6.33317172"]]}
```

Fetch the trade history data, for the currency pair "GHS/BTC" (Most results removed for length):

```java
System.out.println(test.trade_history("GHS/BTC", 1));
```

```json
[{"amount":"0.04385918","price":"0.03533999","date":"1388456363","tid":1893907}]
```

Fetch the account balance data:

```java
System.out.println(test.balance());
```

```json
{"timestamp":"1388458861","username":"kannibal3","BTC":{"available":"0.00000253","orders":"0.00000000"},"GHS":{"available":"0.07817099","orders":"0.00000000"},"IXC":{"available":"0.01365805"},"DVC":{"available":"0.38343789"},"NMC":{"available":"0.00000644","orders":"0.00000000"}}
```

Place an order, for the currency pair, with the given amount and price:

```java
System.out.println(test.place_order("GHS/BTC", "buy", 1f, 0.00000001f));
```

```json
{"id":"124442353","time":1388458861973,"pending":"1.00000000","amount":"1.00000000","type":"buy","price":"0.00000001"}
```

Fetch the account open orders, for the currency pair:

```java
System.out.println(test.open_orders("GHS/BTC"));
```

```json
[{"id":"124442353","time":"1388458861973","type":"buy","price":"0.00000001","amount":"1.00000000","pending":"1.00000000"}]
```

Cancel the account order with the given ID:

```java
System.out.println(test.cancel_order(123456789));
```

```json
true
```

##Additional Help
* Cex.io online API documentation: https://cex.io/api
