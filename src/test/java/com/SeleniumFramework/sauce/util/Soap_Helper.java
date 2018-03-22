package com.SeleniumFramework.sauce.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import com.SeleniumFramework.sauce.util.Central_Time_Stamp_Util;
import com.SeleniumFramework.sauce.util.Soap_Webservice;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import com.sun.jna.platform.win32.Guid.GUID;

/**
 * @author vsaxena2
 *
 */
/**
 * @author vsaxena2
 *
 */
public class Soap_Helper {
	static int i=1;
	public Soap_Helper() {

		// work around to by pass security
		TrustManager[] trustAllCerts =
				new TrustManager[] { new X509TrustManager() {
					@Override
					public java.security.cert.X509Certificate[]
							getAcceptedIssuers() {
						return null;
					}

					@SuppressWarnings("unused")
					public void checkClientTrusted(X509Certificate[] certs,
							String authType) {
					}

					@SuppressWarnings("unused")
					public void checkServerTrusted(X509Certificate[] certs,
							String authType) {
					}

					@Override
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] arg0,
							String arg1) throws CertificateException {

					}

					@Override
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] arg0,
							String arg1) throws CertificateException {

					}

				} };
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
			.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}

			public boolean verify1(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public SOAPMessage readSoapMessage(Document doc) throws SOAPException,
	TransformerException, IOException {
		SOAPMessage request = null ;
		//convert document to String
		if (!doc.equals(null)){
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
			//System.out.println(output);

			//Convert this String to SOAPMEssage


			InputStream is = new ByteArrayInputStream(output.getBytes());	
			 request = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage(null,is);

			MimeHeaders  header = request.getMimeHeaders();

			header.setHeader("Content-Type","application/soap+xml;charset=utf-8");
			SOAPPart soapPart = request.getSOAPPart();
			
			//changebypradeep
			
	        javax.xml.soap.SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	        
	        SOAPElement Header = soapEnvelope.addHeader();
	        
	        SOAPElement security =
	        		Header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
	        security.addAttribute(new QName("xmlns:wsu"),"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
	        //xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
	  //it could be tenative as per the webservice concept      
//	        SOAPElement timestampToken = 
//	        		security.addChildElement("TimeStamp","wsu");
//	        timestampToken.addAttribute(new QName("wsu:ID"),"TS-C575F353EDAD9D3FB414920981855452");
//	        
//	        SOAPElement created =
//           		timestampToken.addChildElement("Created", "wsu");
//         
//		created.addTextNode(CentralTimeStampUtil.getCentralJoda().toString());
//
//       SOAPElement expires =
//           		timestampToken.addChildElement("Expires", "wsu");
//           expires.addTextNode(CentralTimeStampUtil.getCentralJodaPlusFive().toString());
	        Central_Time_Stamp_Util CentralTimeStampUtil = null;
           SOAPElement usernameToken =
                   security.addChildElement("UsernameToken", "wsse");
          usernameToken.addAttribute(new QName("wsu:ID"), "UsernameToken-6B938797777DEE8786149157635322913");
	        
	        
//	        SOAPElement timestampToken =
//            		security.addChildElement("TimeStamp","wsse");
//            timestampToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
//            
//          SOAPElement usernameToken =
//                   security.addChildElement("UsernameToken", "wsse");
//          usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
////                        
//            SOAPElement created =
//            		timestampToken.addChildElement("Created", "wsu");
//            created.addTextNode(CentralTimeStampUtil.getCentralJoda().toString());
//
//            SOAPElement expires =
//            		timestampToken.addChildElement("Expires", "wsu");
//            expires.addTextNode(CentralTimeStampUtil.getCentralJodaPlusFive().toString());

           SOAPElement username =
                    usernameToken.addChildElement("Username", "wsse");
           username.addTextNode(Soap_Webservice.username);
         //  username.addTextNode("tupm3icue");
//
    SOAPElement password =
                   usernameToken.addChildElement("Password", "wsse");
           password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
         // password.addTextNode("fsesF5MK");
          password.addTextNode(Soap_Webservice.password);
//           
//           String usn = "MyUsername";
//           String pwd = "MyPassword";

        //   DateTime created1 = DateTime.now(To)now().ToUniversalTime();
          // Var nonce = getNonce();
           //String nonceToSend = Convert.ToBase64String(Encoding.UTF8.GetBytes(nonce));
           //String createdStr = created.toString("yyyy-MM-ddTHH:mm:ssZ");
           //String passwordToSend = GetSHA1String(nonce + createdStr + pwd);
           
                      
           
           SOAPElement nonceToken =
        		   usernameToken.addChildElement("Nonce", "wsse");
           nonceToken.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
           nonceToken.addTextNode("T9FF0FxJwkoUI9AfiDOE9g==");
           
           
           SOAPElement created1 =
        		   usernameToken.addChildElement("Created", "wsu");
            
           created1.addTextNode(Central_Time_Stamp_Util.getCentralJoda().toString());
           
           
           
           System.out.println("reuest printing");
            //Print out the outbound SOAP message to System.out
           request.writeTo(System.out);
            System.out.println("");

		

			// Temp location for output file
			//        String xmlOutputFilePath = "target/Test.xml";
			//
			//        TransformerFactory factory = TransformerFactory.newInstance();
			//        Transformer transformer = factory.newTransformer();
			//
			//        // Input document
			//        Source source = new DOMSource(doc);
			//        
			//        // Convert new xml doc to a filestream
			//        Result result = new StreamResult(new File(xmlOutputFilePath));
			//        transformer.transform(source, result);
			//        
			//        SOAPMessage message = readSoapMessage(xmlOutputFilePath);

			
		}
		
		return request;
	}

///
	
	protected String getNonce()
    {
        String phrase = GUID.newGuid().toString();
        return phrase;

    }
/*
    protected String GetSHA1String(String phrase)
    {
        SHA1CryptoServiceProvider sha1Hasher = new SHA1CryptoServiceProvider();
        byte[] hashedDataBytes = sha1Hasher.ComputeHash(Encoding.UTF8.GetBytes(phrase));
        string test = Convert.ToString(hashedDataBytes);
        return Convert.ToBase64String(hashedDataBytes);
    }
*/
	
	///

	public SOAPMessage readSoapMessage(String filepath) throws SOAPException,
	IOException, SAXException, ParserConfigurationException {
		SOAPMessage message = null;
		if (filepath == null) {
			throw new FileNotFoundException();
		}

		File f = new File(filepath);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}

		if(Xml_Helper.checkXmlNotBlank(filepath)){
			 message = MessageFactory.newInstance().createMessage();
			SOAPPart soapPart = message.getSOAPPart();
			soapPart.setContent(new StreamSource(new FileInputStream(filepath)));
			message.saveChanges();
			if(!message.equals(null)){
				return message;
			}else{      
				return null;
			}

		}
		//SOAPMessage message = MessageFactory.newInstance().createMessage();
		// SOAPPart soapPart = message.getSOAPPart();
		// soapPart.setContent(new StreamSource(new FileInputStream(filepath)));
		// message.saveChanges();
		// if(!message.equals(null)){
		//return message;
		//  }else{      
		//  return null;
		// }
		return message;

	}

	/*
	 * Method to send soap request
	 */
	public SOAPMessage sendSoapMessage(SOAPMessage message, String url)
			throws Exception {

		if (message == null) {
			throw new Exception("Soap message null");
		}

		if ((url == null) || (url.equals(""))) {
			throw new Exception("Incorrect URL");
		}
		SOAPConnectionFactory soapConnectionFactory =
				SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection =
				soapConnectionFactory.createConnection();

		MimeHeaders header = message.getMimeHeaders();

		header.setHeader("Content-Type", "application/soap+xml;charset=utf-8");
		SOAPMessage soapResponse = soapConnection.call(message, url);

		// Process the SOAP Response
		//wsconfiguration 
//		SOAPPart soapPart = soapResponse.getSOAPPart();
//		javax.xml.soap.SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
//        
//        SOAPElement Header = soapEnvelope.addHeader();
//        
//        SOAPElement security =
//        		Header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
//        		Header.addChildElement("Security", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
//        //xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
//        
//        SOAPElement timestampToken =
//        		security.addChildElement("TimeStamp","wsse");
//        timestampToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
//        
//        SOAPElement usernameToken =
//                security.addChildElement("UsernameToken", "wsse");
//        usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
//                    
//        SOAPElement created =
//        		timestampToken.addChildElement("Created", "wsu");
//        created.addTextNode(CentralTimeStampUtil.getCentralJoda().toString());
//
//        SOAPElement expires =
//        		timestampToken.addChildElement("Expires", "wsu");
//        expires.addTextNode(CentralTimeStampUtil.getCentralJodaPlusFive().toString());

//        SOAPElement username =
//                usernameToken.addChildElement("Username", "wsse");
//        username.addTextNode("smallel1");
//
//        SOAPElement password =
//                usernameToken.addChildElement("Password", "wsse");
//        password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
//       password.addTextNode("SnVseTEyMyU=");
//       
//       String usn = "MyUsername";
//       String pwd = "MyPassword";

    //   DateTime created1 = DateTime.now(To)now().ToUniversalTime();
      // Var nonce = getNonce();
       //String nonceToSend = Convert.ToBase64String(Encoding.UTF8.GetBytes(nonce));
       //String createdStr = created.toString("yyyy-MM-ddTHH:mm:ssZ");
       //String passwordToSend = GetSHA1String(nonce + createdStr + pwd);
       
                  
       
//       SOAPElement nonceToken =
//       		security.addChildElement("Nonce", "wsse");
//       nonceToken.setAttribute("Encoding", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
//      
//       System.out.println("reuest printing");
        //Print out the outbound SOAP message to System.out
      
		
		
		
		

		return soapResponse;
	}

	/*
	 * Method to convert soap response to Document Object
	 */
	public static Document soapToXML(SOAPMessage message) throws Exception {

		if (message == null) {
			throw new Exception("Soap message null");
		}
		
		TransformerFactory transformerFactory =
				TransformerFactory.newInstance();
		Transformer transformer;

		Source sourceContent = message.getSOAPPart().getContent();
		transformer = transformerFactory.newTransformer();
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(sourceContent, result);
		// DOMResult result = new DOMResult();  
		  //  transformer.transform(sourceContent, result); 
		    //System.out.println("Child Nodes in soapToXML  --> "+(Document)result.getNode().getChildNodes().item(1));
		    //return (Document)result.getNode(); 
Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(sw.toString())));
System.out.println("Response from Document  -->"+ sw.toString());
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document doc1 = builder.parse(new InputSource(new StringReader(sw.toString())));
// Use a Transformer for output
TransformerFactory tFactory = TransformerFactory.newInstance();
Transformer transformer1 = tFactory.newTransformer();

DOMSource source = new DOMSource(doc);
//StreamResult result11 = new StreamResult(new File("src/test/resources/ICUE/api/testdata/Assessments/BiometricsResponse.xml"));

StreamResult result11 = new StreamResult(new File("SeleniumFramework\\API\\APIsoap\\Soapresponse\\InstantResponse_"+i+".xml"));
transformer1.transform(source, result11);

return doc;
///ICUEAcceptanceTest/src/test/resources/ICUE/api/testdata/Assessments/Biometrics.xml
		

	}

	/*
	 * Method to print the soap response on console for debugging
	 */
	public static Boolean printSOAPResponse(SOAPMessage soapResponse)
			throws Exception {

		// return false;
		if (soapResponse == null) {
			throw new Exception("Soap message null");

		}
		TransformerFactory transformerFactory =
				TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(sourceContent, result);
		if (sw.toString()!=null){
			System.out.println("Printing SOAP Response: "+sw.toString());
			return true;
		}
		return true;
	}

}
