package autostepper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleImageSearch {
		
    public static void FindAndSaveImage(String question, String destination) {
        String ua = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36";
		// String ua = "AutoStepper";
        String finRes = "";

        try {
            String googleUrl = "https://duckduckgo.com/?q=" + question.replace(",", "+").replace(" ", "+") + "&iaf=size%3ALarge%2Clayout%3AWide&iax=images&ia=images";
            Document doc1 = Jsoup.connect(googleUrl).userAgent(ua).timeout(8 * 1000).get();
            Elements elems = doc1.select("img[src]");
            if( elems.isEmpty() ) {
                System.out.println("Couldn't find any images for: " + question);
                System.out.println(googleUrl);
                return;
            }
            Element media = elems.first();
            String finUrl = media.attr("abs:src"); 
            saveImage(finUrl.replace("&quot", ""), destination);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}