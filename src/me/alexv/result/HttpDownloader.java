package me.alexv.result;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/*
    Консольная утилита для скачивания файлов по HTTP протоколу.
    Входные параметры:
    - количество одновременно качающих потоков (1,2,3,4....)
    - путь к файлу со списком ссылок
    - имя папки, куда складывать скачанные файлы
*/

public class HttpDownloader implements Runnable {
    private URL url;
    private File file;
    private IDownloadListener listener;

    public static void main(String[] args) throws Exception {
        //Через стримы лишь для сдачи лабы по ним)

        //считываем входные параметры
        //1.количество одновременных потоков (если не задано, то 1)
        Integer threadsCount = Arrays.stream(args).limit(1).filter(s -> s.matches("^-?\\d+$")).mapToInt(Integer::parseInt).findFirst().orElse(1);

        //3.каталог для хранения скачанных файлов
        String downloadPath = args[2];

        //2.считываем список ссылок в HashTable, чтобы убрать дубликаты URL,File
        Map<URL, File> urls = new Hashtable<>();
        Files.lines(Paths.get(args[1])).forEach(s -> {
            try {
                urls.put(new URL(s.split("\s")[0]), new File(downloadPath + s.split("\s")[1]));
            } catch (MalformedURLException ignore) {
                //skip url
            }
        });

        //используем, чтобы получать callback..
        Listener listener = new Listener();

        //время начала загрузки файлов
        long time = System.currentTimeMillis();

        //чтобы не остслеживать состояние потоков вручную
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        //стримы для сдачи лабы по стримам))
        urls.entrySet().stream()
                .forEach(entry -> {
                        service.submit(new HttpDownloader(entry.getKey(), entry.getValue(), listener));
                });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        //считаем время выполнения
        time = System.currentTimeMillis() - time;
        System.out.printf("Загружено: %d файлов, %d B\n",listener.getCount(),listener.getSize());
        System.out.printf("Время: %d миллисекунд\n", time);
        System.out.printf("Средняя скорость: %d B/s\n" , listener.getCount(),listener.getSize() / time / 1000);
    }

    public HttpDownloader (URL url, File file, IDownloadListener listener) {
        this.url = url;
        this.file = file;
        this.listener = listener;
    }

    @Override
    public void run() {
        downloadFile(this.url, this.file, this.listener);
    }

    private void downloadFile(URL url, File file, IDownloadListener listener) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            listener.onDownloadBeg(String.format("Загружается файл: %s",file.getName()),url,file);
            long time = System.currentTimeMillis();

            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoInput(true);
            urlConn.connect();

            BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
            int nRead;
            byte[] buffer = new byte[128];
            while ((nRead = bis.read(buffer)) != -1) {
                fos.write(buffer,0, nRead);
            }

            time = System.currentTimeMillis() - time;
            listener.onDownloadEnd(String.format("Файл %s загружен: %d B за %d миллисекунд\n",
                    file.getName(),
                    file.length(),
                    time),
                    url, file);
        } catch (Exception ignore) {
            listener.onError("Ошибка загрузки файла по url " + url.toString());
        }
    }
}

//класс для callback, подсчитывает размер файлов и количество успешно загруженных файловё
class Listener implements IDownloadListener {
    AtomicInteger count = new AtomicInteger(0);
    AtomicLong size = new AtomicLong(0);

    public int getCount() {
        return count.intValue();
    }

    public long getSize() {
        return size.longValue();
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    @Override
    public void onDownloadBeg(String message, URL url, File file) {
        System.out.println(message);
    }

    @Override
    public void onDownloadEnd(String message, URL url, File file) {
        System.out.println(message);
        count.incrementAndGet();
        size.addAndGet(file.length());
    }
}

interface IDownloadListener{
    void onError(String message);
    void onDownloadBeg(String message, URL url, File file);
    void onDownloadEnd(String message, URL url, File file);
}
