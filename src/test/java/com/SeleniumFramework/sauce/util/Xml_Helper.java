package com.SeleniumFramework.sauce.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author vsaxena2
 */
/** 
 * Provides the helper methods for XML handling
 */
public class Xml_Helper {
	/**
	 * This method fetches the given node from a xml document in the form of NodeList
	 *       @param doc:  XML document
	 *       @param nodeName: Node name
	 *       
	 * @return Node List
	 *       
	 */

	public NodeList getNodeList(Document doc, String nodeName)
			throws Exception {


		if (doc == null) {
			throw new FileNotFoundException("File not found");
		}

		if ((nodeName == null) || (nodeName.equals(""))) {
			throw new Exception("Not a valid node name");
		}
		NodeList nList = doc.getElementsByTagName(nodeName);
		System.out.println(nodeName);
		System.out.println("Node List :  "+ nList.getLength());
		return nList;
	}
	
	
	/**
	 * This method reads a xml from a location
	 * @param filePath: Full file path
	 *       
	 * @return XML document   
	 */
	
	public String getNodeValue(Document doc, String nodeName , String attribute)
			throws Exception {


		if (doc == null) {
			throw new FileNotFoundException("File not found");
		}

		if ((nodeName == null) || (nodeName.equals(""))) {
			throw new Exception("Not a valid node name");
		}
				
		String nodeValue = null;
		
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName(nodeName);	
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
		Node nNode = nList.item(temp);
		
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
		nNode.getChildNodes();
		Element eElement = (Element) nNode;
		nodeValue = eElement.getAttributes().getNamedItem(attribute).getNodeValue();
		System.out.println(nodeValue);
				
		}
		
		return nodeValue ;
	}
	

	/**
	 * This method reads a xml from a location
	 * @param filePath: Full file path
	 *       
	 * @return XML document   
	 */
	public Document getXMLDoc(String filePath)
			throws ParserConfigurationException, SAXException, IOException,
			FileNotFoundException {


		File xmlFile = new File(filePath);
		if (!xmlFile.exists()) {
			throw new FileNotFoundException("File Does not exists");
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		return doc;
	}

	/**
	 *This method returns the node list value if it has no child nodes.
	 *       @param nList: Node List name
	 *       
	 * @return Node value   
	 */
	public String getNodeVal(NodeList nList) throws Exception {



		Node n = null;
		Element eElement = null;
		String returnValue = null;
		for (int i = 0; i < nList.getLength(); i++) {
			System.out.println(nList.getLength());
			n = nList.item(i);

			eElement = (Element) n.getChildNodes();

			returnValue = eElement.getTextContent();
			System.out.println("Node Value: "+ returnValue);
			//returnValue = eElement.getTextContent();
		}
		return returnValue;
	}

	/**
	 *This method returns the node list value if it has child nodes.
	 * input: 
	 *       @param nList: Node List
	 *       @param eleName: Child node name
	 *       
	 * @return Child node value
	 */
	public String getNodeVal(NodeList nList, String eleName) throws Exception {
		if ((nList == null) || (nList.equals(""))) {
			
			throw new Exception("Not a valid node name");
		}
				

		if ((eleName == null) || (eleName.equals(""))) {
			throw new Exception("Not a valid element name");
		}

		Node n = null;
		Element eElement = null;
		String returnValue = null;

		//int on = nList.getLength();//item(0);
		//System.out.println("$$$$$$$$     Number of child Nodes: " +on);
		n = nList.item(0);
		eElement = (Element) n.getChildNodes();
		//System.out.println("<<<<<<<<    Number of Child elements"+eElement.getElementsByTagName(eleName).getLength()+"   >>>>>>>>>>>>>>>>>    ");
		returnValue = nList.item(0).getTextContent();//eElement.getElementsByTagName(eleName).item(0).getTextContent();
		return returnValue;
	}

	/**
	 *This method returns the node list value if it has multiple child nodes with same node name.
	 * input: 
	 *       @param nList: Node List
	 *       @param eleName: Child node name
	 *       @param nodeNumber: The nth child node whose value of to be fetched.
	 *       
	 * @return Child node value   
	 */
	public String getNodeVal(NodeList nList, String eleName, int nodeNumber) throws Exception {

		if ((nList == null) || (nList.equals(""))) {
			throw new Exception("Not a valid node name");
		}

		if ((eleName == null) || (eleName.equals(""))) {
			throw new Exception("Not a valid element name");
		}

		Node n = null;
		Element eElement = null;
		String returnValue = null;

		n = nList.item(0);
		eElement = (Element) n.getChildNodes();
		returnValue = eElement.getElementsByTagName(eleName).item(nodeNumber)
				.getTextContent();
		return returnValue;
	}

	/**
	 *This method returns the node list value if it has multiple child nodes with same node name.
	 *       @param nList: Node List
	 *       @param eleName: Child node name
	 *       @param nodeNumber: The nth child node whose value is to be set
	 *       
	 * @return Updated xml documet   
	 */
	public Document setNodeValue(Document doc, String nodeName,
			String nodeValue) throws Exception {

		NodeList nList = getNodeList(doc, nodeName);
		Node n = null;
		Element eElement = null;
		String returnValue = null;

		n = nList.item(0);
		if (n.getNodeType() == Node.ELEMENT_NODE) {
			eElement = (Element) n.getChildNodes();
			eElement.setTextContent(nodeValue);

		} else {
			eElement.setTextContent(nodeValue);

		}
		return doc;

	}

	/**
	 *This method returns the node list value if it has multiple child nodes with same node name.
	 *       @param nList: Node List
	 *       @param eleName: Child node name
	 *       
	 * @return Updated xml documet   
	 */
	public Document setNodeValue(Document doc, String nodeName,
			String nodeValue, int nodeNumber) throws Exception {

		NodeList nList = getNodeList(doc, nodeName);
		Node n = null;
		Element eElement = null;
		String returnValue = null;

		n = nList.item(nodeNumber);
		if (n.getNodeType() == Node.ELEMENT_NODE) {
			eElement = (Element) n.getChildNodes();
			eElement.setTextContent(nodeValue);

		} else {
			eElement.setTextContent(nodeValue);

		}
		return doc;

	}

	public static Boolean checkXmlNotBlank(String filepath) throws IOException, SAXException, ParserConfigurationException{
		//DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		//Document doc;

		File f = new File(filepath);
		FileReader fr = new FileReader(f);
		if (fr.read()==-1){
			return false;
		}

		return true;
	}
	
	
	public static void main(String[] args) throws SAXException, IOException,
    ParserConfigurationException {
		File xmlDocument = new File("src\\test\\resources\\ICUE\\api\\testdata\\assessmentResponse\\InstantResponse.xml");

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

//Document document = builder.parse(new InputSource(new FileReader(new File(xmlDocument))));
Document document = builder.parse(xmlDocument);


NodeList flowList = document.getElementsByTagName("soap:Body");
System.out.println("print");
for (int i = 0; i < flowList.getLength(); i++) {
    NodeList childList = flowList.item(i).getChildNodes();
    for (int j = 0; j < childList.getLength(); j++) {
        Node childNode = childList.item(j);
        if ("TaskID".equals(childNode.getNodeName())) {
            System.out.println(childList.item(j).getTextContent()
                    .trim());
        }
    }
}
}
	
	
	
	
	
	
	
	
}
