package com.SeleniumFramework.sauce.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.testatoo.selenium.server.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.SeleniumFramework.sauce.util.Soap_Helper;
import com.SeleniumFramework.sauce.util.Xml_Helper;




public class Soap_Webservice {

    public static String username;
    public static String password;
    private String requestTemplate;
    private Document xmlRequestDocument;
    public Document xmlResponseDocument;
    
    private String templateURI;

    private Soap_Helper soapHelper = new Soap_Helper();
    private Xml_Helper xmlHelper = new Xml_Helper();
    
    private SOAPMessage res;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        Soap_Webservice.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
    	
    	//this.password = passworddecrypt.passworddecrypt(password);
    	
    	Soap_Webservice.password =  password;
    	    	
         
    }

    /**
     * @return the requestTemplate
     */
    public String getRequestTemplate() {
        return requestTemplate;
    }

    /**
     * @param requestTemplate the requestTemplate to set
     */
    public void setRequestTemplate(String requestTemplate) {
        this.requestTemplate = requestTemplate;
    }

    /**
     * @return the xmlDocument
     */
    public Document getXmlDocument() {
        return xmlRequestDocument;
    }

    
   
    
    
    /**
	 * @return the templateURI
	 */
	public String getTemplateURI() {
		return templateURI;
	}

	/**
	 * @param templateURI the templateURI to set
	 */
	public void setTemplateURI(String templateURI) {
		this.templateURI = templateURI;
	}

	/**
     * @param xmlDocument the xmlDocument to set
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public void load_XML_Request_Template(String xmlTemplateRelativePath)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {
    	 this.templateURI = "SeleniumFramework\\API\\APIsoap\\" + xmlTemplateRelativePath;
        this.xmlRequestDocument =
                xmlHelper.getXMLDoc(this.templateURI);
       
    }
    
	/* public void load_XML_Request_Template(String xmlTemplateRelativePath)
	            throws FileNotFoundException, ParserConfigurationException,
	            SAXException, IOException {
		 String filepath = System.getProperty("user.dir") + "\\src\\test\\resources\\ICUE\\api\\testdata\\";
		 System.out.println(filepath);
	        this.xmlRequestDocument =
	                xmlHelper.getXMLDoc(filepath + xmlTemplateRelativePath);
	        this.templateURI = filepath + xmlTemplateRelativePath;
	    }*/
    
    
    public void load_XML_Request_Template1(String xmlTemplateAbsPath)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {
        this.xmlRequestDocument = xmlHelper.getXMLDoc(xmlTemplateAbsPath);
        this.templateURI = xmlTemplateAbsPath;
    }

    public void setNodeValue(String nodeName, String nodeValue)
            throws Exception {
        this.xmlRequestDocument =
                xmlHelper.setNodeValue(xmlRequestDocument, nodeName, nodeValue);

        NodeList nList = xmlHelper.getNodeList(xmlRequestDocument, nodeName);
        System.out.println(xmlHelper.getNodeVal(nList, nodeName));
    }

    @SuppressWarnings("static-access")
	public void post(String endPoint) throws Exception {

    	System.setProperty("javax.xml.soap.MessageFactory","com.sun.xml.internal.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl");
    	System.setProperty("javax.xml.bind.JAXBContext", "com.sun.xml.internal.bind.v2.ContextFactory");

    	//Parsing the input xml file
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(this.templateURI);
          /*      
		//iterating through the input xml template and insert the layer7 credentials       
        NodeList nodeList = doc.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                               
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                DateTime DT= new DateTime();
               
     		   if ("wsu:Created".equals(node.getNodeName())) {
     			node.setTextContent(CentralTimeStampUtil.getCentralJoda().toString());
     		   }
     		   
     		  if ("wsu:Expires".equals(node.getNodeName())) {
       			node.setTextContent(CentralTimeStampUtil.getCentralJodaPlusFive().toString());
       		   }
       		   
            }
        }
           */
            
     // update header with layer7 in request template xml file
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = transformerFactory.newTransformer();
       DOMSource source = new DOMSource(doc);
// //StreamResult result = new StreamResult(new File("src/main/resources/api/testdata/input/supplier/v2.0.2/supplier_request_template.xml"));
       StreamResult result = new StreamResult(new File(this.templateURI));
//        transformer.transform(source, result);
        
		        
		// Create SOAP req from Document
        SOAPMessage req = soapHelper.readSoapMessage(this.xmlRequestDocument);
        
        // get URL
       // String url = "http://icuealpha.uhc.com/icue/" + endPoint;
        String polarisurl = endPoint;

        // submit req
        
       // res = soapHelper.sendSoapMessage(req, url);
        res = soapHelper.sendSoapMessage(req, polarisurl);
        
        Soap_Helper.soapToXML(res);
     //  Soap_Helper.printSOAPResponse(res);
      //  xmlResponseDocument = xmlHelper.getXMLDoc("src/test/resources/ICUE/api/testdata/Assessments/BiometricsResponse.xml");
        xmlResponseDocument = xmlHelper.getXMLDoc("SeleniumFramework\\API\\APIsoap\\Soapresponse\\InstantResponse_"+Soap_Helper.i+".xml");//
        Soap_Helper.i=Soap_Helper.i+1;
/////ICUEAcceptanceTest/src/test/resources/ICUE/api/testdata/Assessments/Biometrics.xml
        //System.out.println("YYYYYYYYYYY");
        //System.out.println("AAAAAAAAAAA  --> getChildNodes" +xmlResponseDocument.getChildNodes().item(0));
        //System.out.println("BBBBBBBBBB  --> hasChildNodes  : "+xmlResponseDocument.hasChildNodes());
       // System.out.println("PrintSoapResponse : " + SOAPHelper.printSOAPResponse(res));
        //System.out.println("ZZZZZZZZZZZ");
        

    }


    public Boolean getNodeValue(String nodeName, String nodeValue)
            throws Exception {

        String parentNode;
        String parentNode1;
        String childNode;
        String childNode1;
        
        NodeList parentNodeList;
        NodeList childNodeList;
        String actVal = null;

        String[] parts = nodeName.split("/");

        if (parts.length > 1) {
            parentNode = parts[0];
            childNode = parts[1];
//            childNode1 = parts[2];
        //    parentNodeList = xmlHelper.getNodeList(xmlResponseDocument, parentNode);
       //        Element elem = (Element)parentNodeList.item(0);
        //    childNodeList = elem.getChildNodes();
            
            actVal =  xmlHelper.getNodeValue(xmlResponseDocument, parentNode, childNode);
            
        } else {
            parentNode = childNode = nodeName;
            childNodeList = xmlHelper.getNodeList(xmlResponseDocument, parentNode);
            actVal = xmlHelper.getNodeVal(childNodeList, childNode);
        }

       
        
        System.out.println(childNode);
        
        System.out.println("Actual Value in Response :" + actVal);

        return nodeValue.equals(actVal);
    }

	/*@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}
*/
	public boolean handleMessage(String endPointUrl) {
		// TODO Auto-generated method stub
		try {
			post(endPointUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Document getXmlDocument1() {
		
		// TODO Auto-generated method stub
		return xmlResponseDocument;
	}

	/*@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}
*/
	
	
	
public void	converttoxcel() throws SAXException, IOException,
    ParserConfigurationException {
	
	
	Workbook wb = new HSSFWorkbook();
	CreationHelper helper = wb.getCreationHelper();
	Sheet sheet = wb.createSheet("new sheet");
	
		File xmlDocument = new File("src\\test\\resources\\ICUE\\api\\testdata\\assessmentResponse\\InstantResponse.xml").getCanonicalFile();

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

//Document document = builder.parse(new InputSource(new FileReader(new File(xmlDocument))));
Document document = builder.parse(xmlDocument);
//Document doc1 = builder.parse(new InputSource(new StringReader(sw.toString())));


NodeList flowList = document.getElementsByTagName("soap:Body");
System.out.println("print");
for (int i = 0; i < flowList.getLength(); i++) {
    NodeList childList = flowList.item(i).getChildNodes();
    for (int j = 0; j < childList.getLength(); j++) {
      //  Node childNode = childList.item(j);
       // if ("TaskID".equals(childNode.getNodeName()))
        {
            System.out.println(childList.item(j).getTextContent()
                         .trim());
            Row row = sheet.createRow((short) j);
            row.createCell(1).setCellValue(childList.item(j).getTextContent()
                    .trim());
            
        }
        FileOutputStream fileOut = new FileOutputStream("src\\test\\resources\\ICUE\\api\\testdata\\assessmentResponse\\someName.xls");
        wb.write(fileOut);
        fileOut.close();
    }
}
}
}
