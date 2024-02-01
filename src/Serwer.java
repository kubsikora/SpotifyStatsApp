import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
class Serwer {

    String uruchom() throws IOException {
        ServerSocket serv = new ServerSocket(80);
        while (true) {
            Socket sock = serv.accept();
            Thread requestThread = new Thread(() -> {
                try {
                    InputStream is = sock.getInputStream();
                    BufferedReader inp = new BufferedReader(new
                            InputStreamReader(is));
                    String request = inp.readLine();
                    String obciete = "index.html";
                    if (request.startsWith("GET")){
                        String[] parts = request.split(" ");
                        String sciezka = parts[1];
                        obciete = sciezka.substring(1);
                    }
                    //System.out.println(obciete);
                    List<String> DostepneStrony = new ArrayList<>();
                    DostepneStrony.add("index.html");
                    DostepneStrony.add("app.js");
                    OutputStream os = sock.getOutputStream();
                    DataOutputStream outp = new DataOutputStream(os);
                    if (DostepneStrony.contains(obciete)) {
                        if(obciete.length()>6){
                            if (obciete.substring(0,10).equals("index.html")) {
                                InputStream fis = new FileInputStream("website\\index.html");
                                InputStream fis2 = new FileInputStream("website\\app.js");
                                byte[] bufor;
                                bufor = new byte[1024];
                                int n = 0;
                                while ((n = fis.read(bufor)) != -1) {
                                    outp.write(bufor, 0, n);
                                }
                                int k = 0;
                                while ((k = fis.read(bufor)) != -1) {
                                    outp.write(bufor, 0, n);
                                }
                            }
                        } else if (obciete.equals("app.js")) { // Dodaj obsługę pliku JS
                            InputStream fis = new FileInputStream("website\\app.js");
                            byte[] bufor;
                            bufor = new byte[1024];
                            int n = 0;
                            while ((n = fis.read(bufor)) != -1) {
                                outp.write(bufor, 0, n);
                            }
                        }
                    } else {
                        InputStream fis = new FileInputStream("website\\index2.html");

                        byte[] bufor;
                        bufor = new byte[1024];
                        int n = 0;
                        while ((n = fis.read(bufor)) != -1) {
                            outp.write(bufor, 0, n);
                        }
                    }
                    if (!request.startsWith("GET")) {
                        outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");

                    }
                    if (obciete.substring(0,3).equals("@@@")){
                        Main.TOKENnew = obciete.substring(3,obciete.length());
                    }
                    outp.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            requestThread.start();
        }
    }
}
