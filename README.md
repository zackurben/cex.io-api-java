#Cex.io Java API
The Java source files and examples for the Cex.io API. This is an Open Source project under the MIT license.

## Contact
* Author : Zack Urben
* Contact: zackurben@gmail.com

### Support
If you would like to support the development of this project, please spread the word and donate!

* Motivation BTC   : 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
* Cryptsy Trade Key: e5447842f0b6605ad45ced133b4cdd5135a4838c

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
"username"   // Your Cex.io username
"api_key"    // Your Cex.io API key
"api_secret" // Your Cex.io API secret
```

##Methods and Parameters:
Parameters:

```java 
           // Description      (Data Type) : Value range.
"pair"     // Currency pair    (String)    : "GHS/BTC", "LTC/BTC", "NMC/BTC", "GHS/NMC", "BF1/BTC"
"since"    // Timestamp        (Integer)   : 1 - 2147483647
"order_id" // Order Number     (Integer)   : TODO
"type"     // Order Type       (String)    : "buy", "sell"
"amount"   // Order Quantitity (Float)     : 0.00000001 - 9223372036854775807
"price"    // Order Price      (Float)     : 0.00000001 - 9223372036854775807
```

Methods:

```java 
// Method Format, with required parameters:
   
// Fetch the ticker data, for the currency pair.
ticker("pair");

// Fetch the order book data, for the currency pair.
orderBook("pair");

// Fetch the trade history data, for the currency pair.
tradeHistory("pair", "order_id");

// Fetch the account balance data.
balance();

// Place an order, for the currency pair, with the given amount and price.
placeOrder("pair", "type", "amount", "price");

// Fetch the account open orders, for the currency pair.
openOrders("pair");

// Cancel the account order with the given ID.
cancelOrder("order_id");
```
 
##Examples with Output:
Create API object:

```java 
CexAPI test = new CexAPI("username", "api_key", "api_secret");
// Call API methods here
```

Fetch the CexAPI object data:

```json
{
    "username": "xxx",
    "apiKey": "xxx",
    "apiSecret": "xxx",
    "nonce": "xxx"
}
```

Fetch the ticker data, for the currency pair "GHS/BTC":

```java
ticker("GHS/BTC");
```

```json
{
    "timestamp": "1398634454",
    "bid": 0.0079197,
    "ask": 0.0079206,
    "low": "0.00765",
    "high": "0.00839",
    "last": "0.00792084",
    "volume": "156529.92009304"
}
```

Fetch the order book data, for the currency pair "GHS/BTC" (Most results removed for length):

```java
orderBook("GHS/BTC");
```

```json
{
    "timestamp": "1388458859",
    "bids": [
        [
            0.03524828,
            "0.09861694"
        ]
    ],
    "asks": [
        [
            0.03524829,
            "6.33317172"
        ]
    ]
}
```

Fetch the trade history data, for the currency pair "GHS/BTC" (Most results removed for length):

```java
tradeHistory("GHS/BTC", 1);
```

```json
[
    {
        "amount": "0.00030000",
        "price": "0.0079206",
        "date": "1398634455",
        "tid": 4096963
    }
]
```

Fetch the account balance data:

```java
balance();
```

```json
{
    "timestamp": "1398634457",
    "username": "xxx",
    "BTC": {
        "available": "xxx",
        "orders": "xxx"
    },
    "GHS": {
        "available": "xxx",
        "orders": "xxx"
    },
    "IXC": {
        "available": "xxx"
    },
    "DVC": {
        "available": "xxx"
    },
    "NMC": {
        "available": "xxx",
        "orders": "xxx"
    },
    "LTC": {
        "available": "xxx",
        "orders": "xxx"
    }
}
```

Place an order, for the currency pair, with the given amount and price:

```java
placeOrder("GHS/BTC", "buy", 1f, 0.00000001f);
```

```json
{
    "id": "829229545",
    "time": 1398634457348,
    "pending": "1.00000000",
    "amount": "1.00000000",
    "type": "buy",
    "price": "0.00000001"
}
```

Fetch the account open orders, for the currency pair:

```java
openOrders("GHS/BTC");
```

```json
[
    {
        "id": "829229545",
        "time": "1398634457348",
        "type": "buy",
        "price": "0.00000001",
        "amount": "1.00000000",
        "pending": "1.00000000"
    }
]
```

Cancel the account order with the given ID:

```java
cancelOrder(829229545);
```

```json
true
```

##Additional Help
* Cex.io online API documentation: https://cex.io/api
