package ActividadXML;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class TiendaXML {

    public Document cargarXML() throws Exception {

        
        File archivo = new File("tienda.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(archivo);
        return document;
    }

    public NodeList obtenerNombres(Document document) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expresion = "/tienda/producto/nombre";

        // XPathConstants.NODESET espera un conjunto de nodos como resultado
        NodeList nombres = (NodeList) xpath.evaluate(expresion, document, XPathConstants.NODESET);
        return nombres;
    }

    // Método main para probarlo directamente en IntelliJ
    public static void main(String[] args) {
        TiendaXML gestor = new TiendaXML();
        try {
            Document doc = gestor.cargarXML();
            NodeList listaNombres = gestor.obtenerNombres(doc);

            System.out.println("--- PRODUCTOS ENCONTRADOS EN EL XML ---");
            for (int i = 0; i < listaNombres.getLength(); i++) {
                Node nodo = listaNombres.item(i);
                System.out.println("- " + nodo.getTextContent());
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el XML: " + e.getMessage());
        }
    }

}
