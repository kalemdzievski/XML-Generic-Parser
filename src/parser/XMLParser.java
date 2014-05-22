package parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XMLParser {
	
	public Object parseXML(String response, Class<?> resultClass) throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory xmlFactory = SAXParserFactory.newInstance();
		SAXParser xmlParser = xmlFactory.newSAXParser();
		XMLReader xmlReader = xmlParser.getXMLReader();

		XMLGenericParser xmlGenericParser = new XMLGenericParser(resultClass);
		xmlReader.setContentHandler(xmlGenericParser);

		ByteArrayInputStream is = new ByteArrayInputStream(response.getBytes());
		xmlReader.parse(new InputSource(is));	

		return xmlGenericParser.getResultObject();
		
	}

}
