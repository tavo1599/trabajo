import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class App {
    private static final String[] URLS = {
        "https://i.blogs.es/ceda9c/dalle/450_1000.jpg",
        "https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg",
        "https://raulperez.tieneblog.net/wp-content/uploads/2015/09/tux.jpg",
        "https://thumbs.dreamstime.com/b/tigre-de-bengala-5528642.jpg",
        "https://kinsta.com/wp-content/uploads/2020/08/tiger-jpg.jpg"
      };
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(URLS.length);

        for (String url : URLS) {
          executor.execute(() -> {
            try (InputStream in = new URL(url).openStream();
                 FileOutputStream out = new FileOutputStream(url.substring(url.lastIndexOf("/") + 1))) {
              byte[] buffer = new byte[1024];
              int length;
              while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
              }
              System.out.println(url.substring(url.lastIndexOf("/") + 1) + " ha sido descargado.");
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
        }
    
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("Todos los archivos han sido descargados.");
    }
}
