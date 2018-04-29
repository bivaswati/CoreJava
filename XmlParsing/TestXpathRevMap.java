package XmlParsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class TestXpathRevMap {
	public static void main(String[] args) {
		Map<String, String> commandHash = processCommandOptions(args);
		String attFilledTemplate = commandHash.get("--attFilledTemplate");
		String vendorTemplate = commandHash.get("--vendorTemplate");
		System.out.println("attFilledTemplate = " + attFilledTemplate + ", vendorTemplate = " +  vendorTemplate);

		if (attFilledTemplate == null) {
			System.out.println("attFilledTemplate is null");
			printUsage();
		}
		if (vendorTemplate == null) {
			System.out.println("vendorTemplate is null");
			printUsage();
		}

		TestXpathRevMap xpathRevMap = new TestXpathRevMap();
		try {
			xpathRevMap.substitute(attFilledTemplate, vendorTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public List<String> substitute(String attFilledTemplate, 
			String vendorTemplate) throws Exception{

		List<String> newList = new ArrayList<>();
		Map<String, String> attXPathHash = getAttXPath(attFilledTemplate);

		if (attXPathHash.isEmpty()) {
			throw new Exception("attFilledTemplate does not have correct xpath values");
		}
		List<String> vendorTemplateList = getVendorTemplate(vendorTemplate); 

		System.out.println("=====================================");
		Iterator<String> listIterator = vendorTemplateList.iterator();
		while (listIterator.hasNext()) {
			String line = listIterator.next();
			String [] tokens = line.split(" ");
			for (int i=0; i<tokens.length; i++) {
				if (attXPathHash.get(tokens[i]) != null) {
					String val = attXPathHash.get(tokens[i]);
					System.out.print(val + " ");
				} else {	
					System.out.print(tokens[i] + " ");
				}
			}
			System.out.println("");
			
		}
		return newList;
	}
		
	public String getXPath(Node node) {
		Node parent = node.getParentNode();
		if (parent == null) {
		 return "";
		} else {
			Node grandPa = parent.getParentNode();
			if (grandPa == null) {
				return "";
			}
		}
		return getXPath(parent) + "/" + node.getNodeName();
	}

	public Map<String, String> getAttXPath(String attFilledTemplateFile) 
			throws Exception {
		Map<String, String> xPathHash = new HashMap<String, String>();
		try {
		
			File inputFile = new File(attFilledTemplateFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder;
			
			dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			String expression = "//*";	        
			NodeList nodeList = (NodeList) xPath.compile(expression).
										evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nNode = nodeList.item(i);
				String fullXPath = nNode.getNamespaceURI() + getXPath(nNode) ;
				//System.out.println("Element:" + fullXPath + "," +
				//	nNode.getTextContent());
				xPathHash.put(fullXPath, nNode.getTextContent());	
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return xPathHash;
	}


	public List<String> getVendorTemplate(String fileName) {
		List<String> tmpStmtList = new ArrayList<String>();
		BufferedReader br =null;
			try {
				br =  new BufferedReader( new FileReader(fileName));
				String line=null;
				while ((line = br.readLine()) != null) {
					if ( line.trim().length() == 0 ) {
						continue;
					}
					tmpStmtList.add(line);
					//System.out.println(line);
				}
			} catch (Exception ex) {
				System.out.println("Unable to open file " + fileName );
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (Exception ex2) {
					System.out.println("Exception in closing br stream " + fileName );
				}
			}
			return tmpStmtList;
		}

	public static Map<String, String> processCommandOptions(String [] args) {
		Map<String, String> commandHash = new HashMap<String, String>();
		if (args == null || args.length == 0) {
			printUsage();
		}
		System.out.println("Length is " + args.length);
		int i=0;
		while (i<args.length) {
			String option = args[i];
			if (option.startsWith("--")) {
				if ((i+1) < args.length) {
					String tmp = args[i+1];
					if (!tmp.startsWith("--")) {
						commandHash.put(option, tmp);
						i++;
					} else {
						commandHash.put(option, "true");
					}
				}
			}
			i++;
		}
		return commandHash;
	}

	public static void printUsage() {
		System.out.println("Usage:java TestXpathRevMap --attFilledTemplate <file_name.xml> --vendorTemplate <vendor_file_name>");
		System.exit(1);
	}
}

