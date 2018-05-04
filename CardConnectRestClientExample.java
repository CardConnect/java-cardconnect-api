package com.cardconnect.rest;
/*
Copyright 2014, CardConnect (http://www.cardconnect.com)

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
*/

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Client Example showing various service request calls to CardConnect using REST
 */
@SuppressWarnings("unchecked")
public class CardConnectRestClientExample {
	private static final String ENDPOINT = "https://sitename.prinpay.com:6443/cardconnect/rest/";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password123";
	
	public static void main (String args[]) { 
		// Send an Auth Transaction request
		String retref = authTransaction();
		// Void transaction
		voidTransaction(retref);
		
		// Send an Auth Transaction w/ user fields
		retref = authTransactionWithUserFields();
		// Inquire transaction
		inquireTransaction(retref);
		
		// Send an Auth w/ Capture
		retref = authTransactionWithCapture();
		// Void 
		voidTransaction(retref);
		
		// Send normal Auth
		retref = authTransaction();
		// Explicit capture
		captureTransaction(retref);
		
		// Settlement Status
		settlementStatusTransaction();
		
		// Deposit Status
		depositTransaction();
		
		// Auth with Profile
		String profileid = authTransactionWithProfile();
		
		// Get profile
		getProfile(profileid);
		
		// Delete profile
		deleteProfile(profileid);
		
		// Create profile
		addProfile();
	}
	
	
	/**
	 * Authorize Transaction REST Example
	 * @return
	 */
	public static String authTransaction() {
		System.out.println("\nAuthorization Request");
		
		// Create Authorization Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Card Type
		request.put("accttype", "VI");
		// Card Number
		request.put("account", "4444333322221111");
		// Card Expiry
		request.put("expiry", "0914");
		// Card CCV2
		request.put("cvv2", "776");
		// Transaction amount
		request.put("amount", "100");
		// Transaction currency
		request.put("currency", "USD");
		// Order ID
		request.put("orderid", "12345");
		// Cardholder Name
		request.put("name", "Test User");
		// Cardholder Address
		request.put("Street", "123 Test St");
		// Cardholder City
		request.put("city", "TestCity");
		// Cardholder State
		request.put("region", "TestState");
		// Cardholder Country
		request.put("country", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		// Return a token for this card number
		request.put("tokenize", "Y");
		
		// Create the REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an AuthTransaction request
		JSONObject response = client.authorizeTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
		
		return (String)response.get("retref");
	}
	
	
	/**
	 * Authorize Transaction with User Fields REST Example
	 * @return
	 */
	public static String authTransactionWithUserFields() {
		System.out.println("\nAuthorization With User Fields Request");
		
		// Create Authorization Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Card Type
		request.put("accttype", "VI");
		// Card Number
		request.put("account", "4444333322221111");
		// Card Expiry
		request.put("expiry", "0914");
		// Card CCV2
		request.put("cvv2", "776");
		// Transaction amount
		request.put("amount", "100");
		// Transaction currency
		request.put("currency", "USD");
		// Order ID
		request.put("orderid", "12345");
		// Cardholder Name
		request.put("name", "Test User");
		// Cardholder Address
		request.put("Street", "123 Test St");
		// Cardholder City
		request.put("city", "TestCity");
		// Cardholder State
		request.put("region", "TestState");
		// Cardholder Country
		request.put("country", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		// Return a token for this card number
		request.put("tokenize", "Y");
		
		// Create user fields
		JSONArray fields = new JSONArray();
		JSONObject field = new JSONObject();
		field.put("Field1", "Value1");
		fields.add(field);
		request.put("userfields", fields);
		
		// Create the REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an AuthTransaction request
		JSONObject response = client.authorizeTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
		
		return (String)response.get("retref");
	}
	
	
	/**
	 * Authorize Transaction With Capture REST Example
	 * @return
	 */
	public static String authTransactionWithCapture() {
		System.out.println("\nAuthorization With Capture Request");
		
		// Create Authorization Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Card Type
		request.put("accttype", "VI");
		// Card Number
		request.put("account", "4444333322221111");
		// Card Expiry
		request.put("expiry", "0914");
		// Card CCV2
		request.put("cvv2", "776");
		// Transaction amount
		request.put("amount", "100");
		// Transaction currency
		request.put("currency", "USD");
		// Order ID
		request.put("orderid", "12345");
		// Cardholder Name
		request.put("name", "Test User");
		// Cardholder Address
		request.put("Street", "123 Test St");
		// Cardholder City
		request.put("city", "TestCity");
		// Cardholder State
		request.put("region", "TestState");
		// Cardholder Country
		request.put("country", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		// Return a token for this card number
		request.put("tokenize", "Y");
		// Capture auth
		request.put("capture", "Y");
		
		// Create the REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an AuthTransaction request
		JSONObject response = client.authorizeTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
		
		return (String)response.get("retref");
	}
	
	
	/**
	 * Authorize Transaction with Profile REST Example
	 * @return
	 */
	public static String authTransactionWithProfile() {
		System.out.println("\nAuthorization With Profile Request");
		
		// Create Authorization Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Card Type
		request.put("accttype", "VI");
		// Card Number
		request.put("account", "4444333322221111");
		// Card Expiry
		request.put("expiry", "0914");
		// Card CCV2
		request.put("cvv2", "776");
		// Transaction amount
		request.put("amount", "100");
		// Transaction currency
		request.put("currency", "USD");
		// Order ID
		request.put("orderid", "12345");
		// Cardholder Name
		request.put("name", "Test User");
		// Cardholder Address
		request.put("Street", "123 Test St");
		// Cardholder City
		request.put("city", "TestCity");
		// Cardholder State
		request.put("region", "TestState");
		// Cardholder Country
		request.put("country", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		// Return a token for this card number
		request.put("tokenize", "Y");
		// Create Profile
		request.put("profile", "Y");
		
		// Create the REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an AuthTransaction request
		JSONObject response = client.authorizeTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
		
		return (String)response.get("profileid");
	}
	
	
	/**
	 * Capture Transaction REST Example
	 * @param retref
	 */
	public static void captureTransaction(String retref) {
		System.out.println("\nCapture Transaction Request");
		
		// Create Authorization Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Transaction amount
		request.put("amount", "100");
		// Transaction currency
		request.put("currency", "USD");
		// Order ID
		request.put("retref", retref);
		// Purchase Order Number
		request.put("ponumber", "12345");
		// Tax Amount
		request.put("taxamnt", "007");
		// Ship From ZipCode
		request.put("shipfromzip", "11111");
		// Ship To Zip
		request.put("shiptozip", "11111");
		// Ship to County
		request.put("shiptocountry", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		
		// Line item details
		JSONArray items = new JSONArray();
		// Singe line item
		JSONObject item = new JSONObject();
		item.put("lineno", "1");
		item.put("material", "12345");
		item.put("description", "Item Description");
		item.put("upc", "0001122334455");
		item.put("quantity", "5");
		item.put("uom", "each");
		item.put("unitcost", "020");
		items.add(item);
		// Add items to request
		request.put("items", items);
		
		// Authorization Code from auth response
		request.put("authcode", "0001234");
		// Invoice ID
		request.put("invoiceid", "0123456789");
		// Order Date
		request.put("orderdate", "20140131");
		// Total Order Freight Amount
		request.put("frtamnt", "1");
		// Total Duty Amount
		request.put("dutyamnt", "1");
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send a captureTransaction request
		JSONObject response = client.captureTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
	}
	
	
	/**
	 * Void Transaction REST Example
	 * @param retref
	 */
	public static void voidTransaction(String retref) {
		System.out.println("\nVoid Transaction Request");
		
		// Create Update Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Transaction amount
		request.put("amount", "0");
		// Transaction currency
		request.put("currency", "USD");
		// Return Reference code from authorization request
		request.put("retref", retref);
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send a voidTransaction request
		JSONObject response = client.voidTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
	}
	
	
	/**
	 * Refund Transaction REST Example
	 * @param retref
	 */
	public static void refundTransaction(String retref) {
		System.out.println("\nRefund Transaction Request");
		
		// Create Update Transaction request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Transaction amount
		request.put("amount","-100");
		// Transaction currency
		request.put("currency", "USD");
		// Return Reference code from authorization request
		request.put("retref", retref);
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an refundTransaction request
		JSONObject response = client.refundTransaction(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
	}
	
	
	/**
	 * Inquire Transaction REST Example
	 * @param retref
	 */
	public static void inquireTransaction(String retref) {
		System.out.println("\nInquire Transaction Request");
		String merchid = "496400000840";
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Send an inquire Transaction request
		JSONObject response = client.inquireTransaction(merchid, retref);
		
		// Handle response
		if (response != null) {
			Set<String> keys = response.keySet();
			for (String key : keys) 
				System.out.println(key + ": " + response.get(key));
		}
	}
	
	
	/**
	 * Settlement Status REST Example
	 */
	public static void settlementStatusTransaction() {
		System.out.println("\nSettlement Status Transaction Request");
		// Merchant ID
		String merchid = "496400000840";
		String date = "0404";
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		JSONArray responses = client.settlementStatus(merchid, date);
		//JSONArray responses = client.settlementStatus(null, null);
		
		// Handle response
		if (responses != null) {
			for (int i=0; i<responses.size(); i++) {
				JSONObject response = (JSONObject)responses.get(i);
				Set<String> keys = response.keySet();
				for (String key : keys) {
					if ("txns".equals(key)) {
						System.out.println("transactions: ");
						JSONArray txns = (JSONArray) response.get(key);
						for (int j=0; j<txns.size(); j++) {
							System.out.println("  ===");
							JSONObject txn = (JSONObject)txns.get(j);
							Set<String> txnkeys = txn.keySet();
							for (String txnkey : txnkeys) 
								System.out.println("  " + txnkey + ": " + txn.get(txnkey));
						}
					} else {
						System.out.println(key + ": " + response.get(key));
					}
				}
			}
		}
	}
	
	
	/** 
	 * Deposit Transaction REST Example
	 */
	public static void depositTransaction() {
		System.out.println("\nDeposit Transaction Request");
		// Merchant ID
		String merchid = "496400000840";
		// Date
		String date = "20140131";
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		JSONObject response = client.depositStatus(merchid, date);
		
		// Handle response
		if (response != null) {
			Set<String> keys = response.keySet();
			for (String key : keys) {
				if ("txns".equals(key)) {
					System.out.println("transactions: ");
					JSONArray txns = (JSONArray) response.get(key);
					for (int i=0; i<txns.size(); i++) {
						System.out.println("  ===");
						JSONObject txn = (JSONObject)txns.get(i);
						Set<String> txnkeys = txn.keySet();
						for (String txnkey : txnkeys) 
							System.out.println("  " + txnkey + ": " + txn.get(txnkey));
					}
				} else {
					System.out.println(key + ": " + response.get(key));
				}
			}
		}
	}
	
	
	/**
	 * Get Profile REST Example
	 * @param profileid
	 */
	private static void getProfile(String profileid) {
		System.out.println("\nGet Profile Request");
		// Merchant ID
		String merchid = "496400000840";
		// Account ID
		String accountid = "1";
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Retrieve profile from Profile Service
		JSONArray response = client.profileGet(profileid, accountid, merchid);
		
		// Handle response
		if (response != null) {
			for (int i=0; i<response.size(); i++) {
				JSONObject object = (JSONObject)response.get(i);
				Set<String> keys = object.keySet();
				for (String key : keys) 
					System.out.println(key + ": " + object.get(key));
			}
		}
	}
	
	
	/**
	 * Delete Profile REST Example
	 * @param profileid
	 */
	private static void deleteProfile(String profileid) {
		System.out.println("\nDelete Profile Request");
		// Merchant ID
		String merchid = "496400000840";
		String accountid = "";
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Delete profile using Profile Service
		JSONObject response = client.profileDelete(profileid, accountid, merchid);
		
		// Handle response
		if (response != null) {
			Set<String> keys = response.keySet();
			for (String key : keys) 
				System.out.println(key + ": " + response.get(key));
		}
	}
	
	
	/**
	 * Add Profile REST Example
	 */
	private static void addProfile() {
		System.out.println("\nAdd Profile Request");
		
		// Create Profile Request
		JSONObject request = new JSONObject();
		// Merchant ID
		request.put("merchid", "496400000840");
		// Default account
		request.put("defaultacct", "Y");
		// Card Number
		request.put("account", "4444333322221111");
		// Card Expiry
		request.put("expiry", "0914");
		// Cardholder Name
		request.put("name", "Test User");
		// Cardholder Address
		request.put("address", "123 Test St");
		// Cardholder City
		request.put("city", "TestCity");
		// Cardholder State
		request.put("region", "TestState");
		// Cardholder Country
		request.put("country", "US");
		// Cardholder Zip-Code
		request.put("postal", "11111");
		
		// Create the CardConnect REST client
		CardConnectRestClient client = new CardConnectRestClient(ENDPOINT, USERNAME, PASSWORD);
		
		// Create profile using Profile Service
		JSONObject response = client.profileCreate(request);
		
		// Handle response
		Set<String> keys = response.keySet();
		for (String key : keys) 
			System.out.println(key + ": " + response.get(key));
	}
}
