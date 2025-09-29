import javax.xml.soap.*;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class SOAPClient {
    private static final String SOAP_ENDPOINT_URL = "https://fakeapi.com/endpoint";
    private static final String SOAP_ACTION = "";

    public static void main(String[] args) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Create SOAP Message
            SOAPMessage soapMessage = createSOAPRequest();
            printSOAPMessage(soapMessage);

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(soapMessage, SOAP_ENDPOINT_URL);

            // Process the SOAP Response
            printSOAPMessage(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://fakeapi.com/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ex", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetEnvelopes", "ex");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("AccountID", "ex");
        soapBodyElem1.addTextNode("YOUR_ACCOUNT_ID");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + SOAP_ACTION);

        soapMessage.saveChanges();

        return soapMessage;
    }

    private static void printSOAPMessage(SOAPMessage soapMessage) throws Exception {
        System.out.println("\nSOAP Message:\n");
        soapMessage.writeTo(System.out);
        System.out.println();
    }
}
