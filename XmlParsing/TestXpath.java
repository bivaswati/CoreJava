package XmlParsing;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class TestXpath {
   public static void main(String[] args) {
      try {

         File inputFile = new File("D:\\IntelliJ\\GitDemo\\CoreJava\\XmlParsing\\att_template.xml");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         dbFactory.setNamespaceAware(true);
         DocumentBuilder dBuilder;

         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         XPath xPath =  XPathFactory.newInstance().newXPath();

         String expression = "//*";	        
         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
						String fullXPath = nNode.getNamespaceURI() + getXPath(nNode) ;
            System.out.println("\nCurrent Element :" + fullXPath + "," +
								nNode.getTextContent());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
            }
         }

      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static String getXPath(Node node)
   {
    Node parent = node.getParentNode();
    if (parent == null)
    {
        return "";
    }
    return getXPath(parent) + "/" + node.getNodeName();
   }

}

