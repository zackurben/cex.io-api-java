#Cex.io Java API
The Java source files and examples for the Cex.io API. This is an Open Source project under the MIT license.

## Index
 - [Contact](#contact)
 - [Support](#support)
 - [How to Use](#how-to-use)
 - [Methods and Parameters](#parameters-and-methods)
  - [Methods](#methods)
  - [Parameters](#parameters)
 - [Examples with Output](#examples-with-output)
  - [Create API Object](#create-api-object)
  - [Fetch Ticker Data](#fetch-ticker-data)
  - [Fetch Last Price](#fetch-last-price)
  - [Convert Currency](#convert-currency)
  - [Fetch Historical Data](#fetch-historical-data)
  - [Fetch Order Book](#fetch-order-book)
  - [Fetch Trade History](#fetch-trade-history)
  - [Fetch Account Balance](#fetch-account-balance)
  - [Place an Order](#place-an-order)
  - [Fetch Open Orders](#fetch-open-orders)
  - [Cancel Open Order](#cancel-open-order)
  - [Fetch Account Hash Rate](#fetch-account-hash-rate)
  - [Fetch Worker Hash Rate](#fetch-worker-hash-rate)
 - [Additional Help](#additional-help)

## Contact
```
Author : Zack Urben
Contact: zackurben@gmail.com
```

## Support
If you would like to support the development of this project, please spread the word and donate!

```
Motivation BTC   : 1HvXfXRP9gZqHPkQUCPKmt5wKyXDMADhvQ
Cryptsy Trade Key: e5447842f0b6605ad45ced133b4cdd5135a4838c
```

**Additional Windows help on the [Project Wiki](https://github.com/zackurben/cex.io-api-java/wiki/Windows-Setup:-Basic-Help).**

##How to use
1. Download this API source.
2. Generate a Cex.io API key and API secret (https://cex.io/trade/profile).
 - This key needs all the permissions, to enable full functionality.
3. Create your Java project
4. Add the API to your project directory.
5. Create an API object:

```java
// API Object Parameters:
"username"   // Your Cex.io username
"api_key"    // Your Cex.io API key
"api_secret" // Your Cex.io API secret

CexAPI test = new CexAPI("username", "api_key", "api_secret");
```

##Parameters and Methods
###Parameters

```java 
           // Description      (Data Type) : Value range.
"pair"     // Currency Pair    (String)    : "GHS/BTC", "LTC/BTC", "NMC/BTC", "GHS/NMC", "BF1/BTC"
"major"    // Major Currency   (String)    : TODO
"minor"    // Minor Currency   (String)    : TODO
"hours"    // Hours            (Integer)   : TODO
"count"    // Result Count     (Integer)   : TODO
"since"    // Timestamp        (Integer)   : 1 - 2147483647
"order_id" // Order Number     (String)    : TODO
"type"     // Order Type       (String)    : "buy", "sell"
"amount"   // Order Quantity   (Float)     : 0.00000001 - 9223372036854775807
"price"    // Order Price      (Float)     : 0.00000001 - 9223372036854775807
```

###Methods

```java 
// Method Format, with required parameters:
   
// Fetch the ticker data, for the currency pair.
ticker("pair");

// Fetch the last order price for the currency pair.
lastPrice("major", "minor");

// Fetch the price conversion from the Major to Minor currency.
convert("major", "minor", "amount");

// Fetch the historical data points for the Major to Minor currency.
chart("major", "minor", "hours", "count")

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

// Fetch the GHash.io hash rates for the last 24 hours.
hashrate();

// Fetch the GHash.io worker stats for the last 24 hours.
workers();
```
 
##Examples with Output
###Create API object:

```java 
// API Object Parameters:
"username"   // Your Cex.io username
"api_key"    // Your Cex.io API key
"api_secret" // Your Cex.io API secret

CexAPI test = new CexAPI("username", "api_key", "api_secret");
```

```json
{
    "username": "xxx",
    "apiKey": "xxx",
    "apiSecret": "xxx",
    "nonce": "xxx"
}
```

###Fetch Ticker Data
Fetch the ticker data, for the currency pair "GHS/BTC".

```java
ticker("GHS/BTC");
```

```json
{
    "timestamp": "1414961682",
    "bid": 0.00267964,
    "ask": 0.00268965,
    "low": "0.00261101",
    "high": "0.0027",
    "last": "0.00268966",
    "volume": "43614.47202243"
}
```

###Fetch Last Price
Fetch the last order price for the currency pair.

```java
lastPrice("BTC", "USD");
```

```json
{
    "lprice": "334.2772"
}
```

###Convert Currency
Fetch the price conversion from the Major to Minor currency.

```java
convert("BTC", "USD", 2f);
```

```json
{
    "amnt": 669.9326
}
```

###Fetch Historical Data
Fetch the historical data points for the Major to Minor currency.

```java
chart("BTC", "USD", 24, 2);
```

```json
[
    {
        "tmsp": 1415424600,
        "price": "334.1699"
    },
    {
        "tmsp": 1415467800,
        "price": "335.2923"
    }
]
```

###Fetch Order Book
Fetch the order book data, for the currency pair "GHS/BTC" (Most results removed for length).

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

###Fetch Trade History
Fetch the trade history data, for the currency pair "GHS/BTC" (Most results removed for length).

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

###Fetch Account Balance
Fetch the account balance data.

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
        "available": "xxx",
        "orders": "xxx"
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

###Place an Order
Place an order, for the currency pair, with the given amount and price.

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

###Fetch Open Orders
Fetch the account open orders, for the currency pair.

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

###Cancel Open Order
Cancel the open order with the given ID.

```java
cancelOrder(829229545);
```

```json
true
```

###Fetch Account Hash Rate
Fetch the account Hash Rate on GHash.io.

```java
hashrate();
```

```json
{
    "last5m": 0,
    "last15m": 0,
    "last1h": 0,
    "last1d": 0,
    "prev5m": 0,
    "prev15m": 0,
    "prev1h": 0,
    "prev1d": 0
}
```

###Fetch Worker Hash Rate
Fetch the worker Hash Rates on GHash.io.

```java
workers();
```

```json
{
    "xxx.ghash": {
        "last5m": 0,
        "last15m": 0,
        "last1h": 0,
        "last1d": 0,
        "prev5m": 0,
        "prev15m": 0,
        "prev1h": 0,
        "prev1d": 0,
        "rejected": {
            "stale": 0,
            "duplicate": 0,
            "lowdiff": 0
        }
    }
}
```

##Additional Help
* Cex.io online API documentation: https://cex.io/api
