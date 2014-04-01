package timetracking.security

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import groovy.xml.*
 
/**
 * @author archer
 * Created: Tue Apr 01 13:27:53 EEST 2014
 */
class AuthenticationService {
  private final String serverUrl;
  private final String userName;
  private final String password;

  private final HTTPBuilder http;


  public AuthenticationService(String serverUrl, String userName, String password){
    this.serverUrl = serverUrl
    this.userName = userName
    this.password = password

    this.http = new HTTPBuilder(this.serverUrl)

  }

  public void initHeaders(def headers){
    headers.'User-Agent' = userAgent
    headers.'Accept' = 'application/xml'
    headers.'Content-type' = 'application/xml'
    headers.'Authorization' = 'Basic ' + "${this.userName}:${this.password}".toString().bytes.encodeBase64().toString()
  }

  private String getUserAgent(){
    'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
  }
  

  public boolean login(){    
    boolean result = false;
    http.request(GET, XML){
      uri.path = '/account/who_am_i'
      initHeaders(headers)

      response.success = { resp, xml ->
        println resp.statusLine
        println "Class:${xml.getClass().name}"
        result = true
      }

      response.failure = {resp, xml ->
        println resp.statusLine
        println "Class:${xml.getClass().name}"
        result = false
      }
    }
    return result
  }

  public boolean clients(){
    boolean result = false;
    http.request(GET, TEXT){
      uri.path = '/clients'
      initHeaders(headers)

      response.success = { resp, reader ->
        println resp.statusLine
        println "Class:${reader.getClass().name}"

        def line;
        while(line = reader.readLine()){
          println line;
        }

        result = true;
      }

      response.failure = { resp, reader ->
        println resp.statusLine
        println "Class:${reader.getClass().name}"

        result = false;
      }
      return result
    }
  }

  public boolean daily(){
    boolean result = false;
    http.request(GET, XML){
      uri.path = '/daily'
      initHeaders(headers)

      response.success = { resp, xml ->
        println resp.statusLine

        System.out << XmlUtil.serialize(xml);

        result = true;
      }

      response.failure = { resp, reader ->
        println resp.statusLine
        println "Class:${reader.getClass().name}"

        result = false;
      }
      return result
    }
  }
}