import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class Strona {

    static String TOKEN = "";

    void main() throws IOException {
        Thread serwerthred = new Thread(() -> {
            try {
                Serwer serwer = new Serwer();
                serwer.uruchom();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serwerthred.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = "http://127.0.0.1/index.html";
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Obsługa otwierania przeglądarki nie jest dostępna.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i == 0){
            if (TOKEN.length() > 10){
                i = 1;
            }
        }
    }

    static void setTOKEN(String var){
        TOKEN = var;
    }
    String getTOKEN(){
        return TOKEN;
    }
}
