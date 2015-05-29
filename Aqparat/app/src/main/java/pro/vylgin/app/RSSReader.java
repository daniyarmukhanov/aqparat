package pro.vylgin.app;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.HTMLDocument;
import java.net.URL;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSReader {

    private static RSSReader instance = null;
    private URL rssURL;
    private String resource_id;

    private RSSReader() {
    }

    public static RSSReader getInstance() {
        if (instance == null) {
            instance = new RSSReader();
        }
        return instance;
    }

    public void setURL(URL url, String r_id) {
        rssURL = url;
        resource_id = r_id;
    }

    public void writeFeed() throws Exception {

        boolean saved = false;
        String title, link, desc, text, category, language, imageLink;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(rssURL.openStream());

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                title = getValue(item, "title");
                title = title.replace("`", "");
                title = title.replace("'", "");
                desc = getValue(item, "description");
                desc = desc.replace("`", "");
                desc = desc.replace("'", "");
                link = getValue(item, "link");

                text = ArticleExtractor.INSTANCE.getText(new URL(link));
                language = "2";
                imageLink = "no";
                System.out.println(link + "\n" + desc + "\n" + title + "\n" + text + "\n" + resource_id + "\n" + language + "\n" + imageLink);
                System.out.println("\n\n\n\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(Element parent, String nodeName) {
        return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
    }

}
