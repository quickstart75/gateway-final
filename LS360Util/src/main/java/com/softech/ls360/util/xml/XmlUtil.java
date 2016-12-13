package com.softech.ls360.util.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlUtil {

	public static Document getXmlDocument(String xml) throws Exception {
		
		/**
    	 * The mechanism for getting access to a DOM parser is very similar to what you used to obtain a SAX parser. You 
    	 * start with a factory object that you obtain like this.
    	 * 
    	 * The newInstance() method is a static method in the javax.xml.parsers.DocumentBuilderFactory class for creating
    	 * factory objects. As with SAX, this approach of dynamically creating a factory object that you then use to create 
    	 * a parser allows you to change the parser you are using without modifying or recompiling your code. The factory 
    	 * object creates a javax.xml.parsers.DocumentBuilder object that encapsulates a DOM parser:
    	 */
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		
		/**
		 * The static newInstance() method in the DocumentBuilderFactory class returns a reference to a factory object. 
		 * You call the newDocumentBuilder() method for the factory object to obtain a reference to a DocumentBuilder 
		 * object that encapsulates a DOM parser. This is the default parser. If you want the parser to validate the XML
		 * or provide other capabilities, you can set the parser features before you create the DocumentBuilder object 
		 * by calling methods for the DocumentBuilderFactory object.
		 */
		DocumentBuilder documenBuilder = builderFactory.newDocumentBuilder();
		
		/**
    	 * With a validating parser, you should define an ErrorHandler object that deals with parsing errors. You identify 
    	 * the ErrorHandler object to the parser by calling the setErrorHandler() method for the DocumentBuilder object.
    	 * 
    	 * Here handler refers to an object that implements the three methods declared in the org.xml.sax.ErrorHandler 
    	 * interface. If you do create a validating parser, you should always implement and register an ErrorHandler 
    	 * object. Otherwise, the parser may not work properly.
    	 */
		documenBuilder.setErrorHandler(new DomErrorHandler());
		
		/**
    	 * After you have created a DocumentBuilder object, you just call its parse() method with a document source as an 
    	 * argument to parse a document. The parse() method returns a reference of type Document to an object that 
    	 * encapsulates the entire XML document. The Document interface is defined in the org.w3c.dom package.
    	 * 
    	 * There are five overloaded versions of the parse() method that provide various options for you to identify the 
    	 * source of the XML document to be parsed. They all return a reference to a Document object encapsulating the XML 
    	 * document: 
    	 * 
    	 *     parse(File file): Parses the document in the file identified by file.
               parse(String uri): Parses the document at the URI uri.
               parse(InputSource srce): Parses the document read from srce.
               parse(InputStream in): Parses the document read from the stream in.
               parse(InputStream in, String systemID): Parses the document read from the stream in. The systemID argument is
                                                       used as the base to resolve relative URIs in the document.

         * All five versions of the parse() method can throw three types of exception. An IllegalArgumentException is thrown 
         * if you pass null to the method for the parameter that identifies the document source. The method throws an 
         * IOException if any I/O error occurs and a SAXException in the event of a parsing error. The last two exceptions 
         * must be caught. Note that it is a SAXException that can be thrown here. Exceptions of type DOMException arise 
         * only when you are navigating the element tree for a Document object. 
         * 
         * The org.xml.sax.InputSource class defines objects that encapsulate a source of an XML document. The InputSource 
         * class defines constructors that enable you to create an object from a java.io.InputStream object, 
         * a java.io.Reader object, or a String object specifying a URI for the document source. If the URI is a URL, it must
         * not be a relative URL.
    	 */
		Document xmlDocument = null;
    	try {
    		xmlDocument = documenBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes())));
    		
    		/**
    		 * optional, but recommended
    		 * 
    		 *  the following XML element
    		 		
    		 		<foo>hello 
					wor
					ld</foo>
			 * 
			 * could be represented like this in a denormalized node:
			 * 
			 		Element foo
    					Text node: ""
    					Text node: "Hello "
    					Text node: "wor"
    					Text node: "ld"
    					
    		 * When normalized, the node will look like this
    		 * 
    		 		Element foo
    					Text node: "Hello world"
    					
    		 * And the same goes for attributes: <foo bar="Hello world"/>, comments, etc.
    		 */
    		xmlDocument.getDocumentElement().normalize();
    	} catch(IOException | SAXException e ) {
    		e.printStackTrace();
    	} 
    	return xmlDocument;
	}
	
	public static Document getXmlDocumentFromInputStream(InputStream is) throws Exception {
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documenBuilder = builderFactory.newDocumentBuilder();
		documenBuilder.setErrorHandler(new DomErrorHandler());
		Document xmlDocument = null;
    	try {
    		xmlDocument = documenBuilder.parse(is);
    		xmlDocument.getDocumentElement().normalize();
    	} catch(IOException | SAXException e ) {
    		e.printStackTrace();
    	}
    	return xmlDocument;
	}
	
	public static Document getXmlDocumentFromFile(File xmlFile) throws Exception {
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documenBuilder = builderFactory.newDocumentBuilder();
		documenBuilder.setErrorHandler(new DomErrorHandler());
		Document xmlDocument = null;
    	try {
    		BufferedInputStream in = new BufferedInputStream(new FileInputStream(xmlFile));
    		xmlDocument = documenBuilder.parse(in);
    		xmlDocument.getDocumentElement().normalize();
    	} catch(IOException | SAXException e ) {
    		e.printStackTrace();
    	} 
    	return xmlDocument;
	}
	
	private static class DomErrorHandler implements ErrorHandler {
		
		/**
		 * Called to notify conditions that are not errors or fatal errors. The exception object, spe, contains information to
		 * enable you to locate the error in the original document.
		 */
		public void warning(SAXParseException spe) {
		
			/**
			 * Returns the line number of the end of the document text where the error occurred. If this is not available, –1 
			 * is returned.
			 */
			System.out.println("Warning at line " + spe.getLineNumber());
			
			/**
			 * Returns the column number within the document that contains the end of the text where the error occurred. If this
			 * is not available, –1 is returned. The first column in a line is column 1.
			 */
			System.out.println("Column Number " + spe.getColumnNumber());
			
			/**
			 * Returns the public identifier of the entity where the error occurred, or null if no public identifier is 
			 * available.
			 */
			System.out.println("Public Id" + spe.getPublicId());
			
			/**
			 * Returns the system identifier of the entity where the error occurred, or null if no system identifier is 
			 * available.
			 */
			System.out.println("System Id" + spe.getSystemId());
		    System.out.println(spe.getMessage());
		
		} //end of warning()
		
		/**
		 * Here you just rethrow the SAXParseException after outputting an error message indicating the line number that caused 
		 * the error. The SAXParseException class is a subclass of SAXException, so you can rethrow spe as the superclass type. 
		 * Don't forget the import statements in the MySAXHandler source fi le for the SAXException and SAXParseException class 
		 * names from the org.xml.sax package.
		 */
		public void fatalError(SAXParseException spe) throws SAXException {
			System.out.println("Fatal error at line " + spe.getLineNumber());
		    System.out.println(spe.getMessage());
		    throw spe;
		} //end of fatalError()

		public void error(SAXParseException spe) throws SAXException {
			System.out.println("Error at line " + spe.getLineNumber());
			System.out.println(spe.getMessage());
		} //end of error()
		
	} //end of static inner class DomErrorHandler
	
	public static String convertObjectToXml(Object source, Class<?>... type) {
        String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
	
	public static Object convertXmlToObject(String xmlStringData, Class<?>... type) throws JAXBException {
		 Object obj = null;
		 StringReader reader = new StringReader(xmlStringData);
		 try {
			 
			 JAXBContext context = JAXBContext.newInstance(type);
		     Unmarshaller unmarshaller = context.createUnmarshaller();
		     obj = unmarshaller.unmarshal(reader); 
		 } catch (JAXBException e) {
			 throw new RuntimeException(e);
		 }
		
	     return obj;
	 }
	
	public String extractValue(String xml, String xpathExpression) {
        String actual;
        try {
            Document doc = getXmlDocument(xml);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            actual = xpath.evaluate(xpathExpression, doc, XPathConstants.STRING).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return actual;
    }
	
}
