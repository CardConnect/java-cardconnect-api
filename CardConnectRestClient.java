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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * An example REST client for CardConnect
 */
public class CardConnectRestClient {
	private String url;
	private String userpass;
	
	// Endpoint names
	private static final String ENDPOINT_AUTH       = "auth";
	private static final String ENDPOINT_CAPTURE    = "capture";
	private static final String ENDPOINT_VOID       = "void";
	private static final String ENDPOINT_REFUND     = "refund";
	private static final String ENDPOINT_INQUIRE    = "inquire";
	private static final String ENDPOINT_SETTLESTAT = "settlestat";
	private static final String ENDPOINT_DEPOSIT    = "deposit";
	private static final String ENDPOINT_PROFILE    = "profile";
	
	private enum OPERATIONS {GET, PUT, POST, DELETE};
	
	private static final String USER_AGENT = "CardConnectRestClient-Java";
	private static final String CLIENT_VERSION = "1.0";
	
	
	/**
	 * Creates a CardConnect REST client
	 * @param url Base URL to rest service.  Eg: https://sitename.prinpay.com:6443/cardconnect/rest/
	 * @param username API Username
	 * @param password API Password
	 */
	public CardConnectRestClient(String url, String username, String password) throws IllegalArgumentException {
		if (isEmpty("url")) throw new IllegalArgumentException("url parameter is required");
		if (isEmpty("username")) throw new IllegalArgumentException("username parameter is required");
		if (isEmpty("password")) throw new IllegalArgumentException("password parameter is required");
		
		if (!url.endsWith("/")) url = url + "/";
		
		this.url = url;
		this.userpass = username + ":" + password;
	}
	
	
	/**
	 * Authorize trasaction
	 * @param request JSONObject representing an Authorization transaction request
	 * @return JSONObject representing an Authorization transaction response
	 */
	public JSONObject authorizeTransaction(JSONObject request) {
		return (JSONObject)send(ENDPOINT_AUTH, OPERATIONS.PUT, request);
	}
	
	
	/**
	 * Capture transaction
	 * @param request JSONObject representing a Capture transaction request
	 * @return JSONObject representing a Capture transaction response
	 */
	public JSONObject captureTransaction(JSONObject request) {
		return (JSONObject)send(ENDPOINT_CAPTURE, OPERATIONS.PUT, request);
	}
	
	
	/**
	 * Void transaction
	 * @param request JSONObject representing a Void transaction request
	 * @return JSONObject representing a Void transaction response
	 */
	public JSONObject voidTransaction(JSONObject request) {
		return (JSONObject)send(ENDPOINT_VOID, OPERATIONS.PUT, request);
	}
	
	
	/**
	 * Refund Transaction
	 * @param request JSONObject representing a Refund transaction request
	 * @return JSONObject represeting a Refund transactino response
	 */
	public JSONObject refundTransaction(JSONObject request) {
		return (JSONObject)send(ENDPOINT_REFUND, OPERATIONS.PUT, request);
	}
	
	
	/**
	 * Inquire Transaction
	 * @param merchid Merchant ID
	 * @param retref RetRef to inquire
	 * @return JSONObject representing the request transaction
	 * @throws IllegalArgumentException
	 */
	public JSONObject inquireTransaction(String merchid, String retref) throws IllegalArgumentException {
		if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
		if (isEmpty(retref)) throw new IllegalArgumentException("Missing required parameter: retref");
		
		String url = ENDPOINT_INQUIRE + "/" + retref + "/" + merchid;
		return (JSONObject)send(url, OPERATIONS.GET, null);
	}
	
	
	/**
	 * Gets the settlement status for transactions
	 * @param merchid Mechant ID
	 * @param date Date in MMDD format
	 * @return JSONArray of JSONObjects representing Settlement batches, each batch containing a JSONArray of 
	 * JSONObjects representing the settlement status of each transaction
	 * @throws IllegalArgumentException
	 */
	public JSONArray settlementStatus(String merchid, String date) throws IllegalArgumentException {
		if ((!isEmpty(merchid) && isEmpty(date)) || (isEmpty(merchid) && !isEmpty(date))) 
			throw new IllegalArgumentException("Both merchid and date parameters are required, or neither");
		
		String url = null;
		if (isEmpty(merchid) || isEmpty(date)) {
			url = ENDPOINT_SETTLESTAT;
		} else {
			url = ENDPOINT_SETTLESTAT + "?merchid=" + merchid + "&date=" + date;
		}
		
		return (JSONArray)send(url, OPERATIONS.GET, null);
	}
	
	
	/**
	 * Retrieves deposit status information for the given merchant and date
	 * @param merchid Merchant ID
	 * @param date in MMDD format
	 * @return
	 * @throws IllegalArgumentException
	 */
	public JSONObject depositStatus(String merchid, String date) throws IllegalArgumentException {
		if ((!isEmpty(merchid) && isEmpty(date)) || (isEmpty(merchid) && !isEmpty(date)))
			throw new IllegalArgumentException("Both merchid and date parameters are required, or neither");
		
		String url = null;
		if (isEmpty(merchid) || isEmpty(date)) {
			url = ENDPOINT_DEPOSIT;
		} else {
			url = ENDPOINT_DEPOSIT + "?merchid=" + merchid + "&date=" + date;
		}
		return (JSONObject)send(url, OPERATIONS.GET, null);
	}
	
	
	/**
	 * Retrieves a profile
	 * @param profileid ProfileID to retrieve
	 * @param accountid Optional account id within profile
	 * @param merchid Merchant ID
	 * @return JSONArray of JSONObjects each represeting a profile
	 * @throws IllegalArgumentException
	 */
	public JSONArray profileGet(String profileid, String accountid, String merchid) throws IllegalArgumentException {
		if (isEmpty(profileid)) throw new IllegalArgumentException("Missing required parameter: profileid");
		if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
		if (accountid == null) accountid = "";
		
		String url = ENDPOINT_PROFILE + "/" + profileid + "/" + accountid + "/" + merchid;
		return (JSONArray)send(url, OPERATIONS.GET, null);
	}
	
	
	/**
	 * Deletes a profile
	 * @param profileid ProfileID to delete
	 * @param accountid Optional accountID within the profile
	 * @param merchid Merchant ID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public JSONObject profileDelete(String profileid, String accountid, String merchid) throws IllegalArgumentException {
		if (isEmpty(profileid)) throw new IllegalArgumentException("Missing required parameter: profileid");
		if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
		if (accountid == null) accountid = "";
		
		String url = ENDPOINT_PROFILE + "/" + profileid + "/" + accountid + "/" + merchid;
		return (JSONObject)send(url, OPERATIONS.DELETE, null);
	}
	
	
	/**
	 * Creates a new profile
	 * @param request JSONObject representing the Profile creation request
	 * @return JSONObejct representing the newly created profile
	 * @throws IllegalArgumentException
	 */
	public JSONObject profileCreate(JSONObject request) throws IllegalArgumentException {
		return (JSONObject)send(ENDPOINT_PROFILE, OPERATIONS.PUT, request);
	}
	
	
	/**
	 * Updates an existing profile
	 * @param request JSONObject representing the Profile Update request
	 * @return JSONObject representing the updated Profile
	 */
	public JSONObject profileUpdate(JSONObject request) {
		return profileCreate(request);
	}
	
	
	// Internal method to test if a string is null or empty
	private boolean isEmpty(String str) {
		if (str == null) return true;
		if (str.length() <= 0) return true;
		if ("".equals(str)) return true;
		return false;
	}
	
	
	// Internal method to send an HTTP REST request
	private Object send(String endpoint, OPERATIONS operation, JSONObject request) { 
		Object response = null;
		String resp = null;
		String url = this.url + endpoint;
		CloseableHttpClient httpclient = null;
		
		try {
			httpclient = HttpClients.createDefault();
			HttpClientContext context = HttpClientContext.create();
			
			Header[] headers = getHeaders();
			
			StringEntity entity = null;
			if (request != null) {
				entity = new StringEntity(request.toJSONString(), "UTF-8");
				entity.setContentType("application/json");
			}
			
			ResponseHandler<String> rh = getResponseHandler();
			
			// Send request over HTTP
			switch (operation) {
				case PUT:    {
					HttpPut put = new HttpPut(url);
					put.setHeaders(headers);
			        	put.setEntity(entity);
					resp = httpclient.execute(put, rh, context);
					break;
				}
				case POST:   {
					HttpPost post = new HttpPost(url);
					post.setHeaders(headers);
			        	post.setEntity(entity);
					resp = httpclient.execute(post, rh, context);
					break;
					}
				case GET:    {
					HttpGet get = new HttpGet(url);
					get.setHeaders(headers);
					resp = httpclient.execute(get, rh, context);
					break;
				}
				case DELETE: {
					HttpDelete delete = new HttpDelete(url);
					delete.setHeaders(headers);
					resp = httpclient.execute(delete, rh, context);
					break;
				}
			}
			if (resp != null) {
				response = JSONValue.parse(resp);
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		} finally {
			try { httpclient.close(); } catch (Exception ex) { }
		}
		
		return response;
	}
	
	
	// Creates a response handler for the HTTP responses
	private ResponseHandler<String> getResponseHandler() {
		return new ResponseHandler<String>() {
		    public String handleResponse(final HttpResponse response) throws IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }
		        
		        InputStream instream = entity.getContent();
		        StringBuilder sb = new StringBuilder();
		        BufferedReader r = new BufferedReader(new InputStreamReader(instream), 2000);
		        for (String line = r.readLine(); line != null; line = r.readLine()) {
		            sb.append(line);
		        }
		        instream.close();
		        return sb.toString();
		    }
		};
	}
	
	
	// Generaters headers for the HTTP calls - for Authorization and JSON
	private Header[] getHeaders() {
		Header[] headers = new BasicHeader[3];
		headers[0] = new BasicHeader("Authorization", "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes()));
		headers[1] = new BasicHeader("Accept", "application/json");
		headers[2] = new BasicHeader("Content-Type", "application/json");
		
		return headers;
	}
}
