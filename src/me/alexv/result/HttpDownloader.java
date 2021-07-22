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

    private long downloadBegTime;
    private long downloadEndTime;

    private int fileSizeInByte;

    private IDownloadListener listener;


    public static void main(String[] args) throws Exception {
        //Через стримы лишь для сдачи д/з 14.stream))

        //считываем входные параметры
        //1 параметр: количество одновременных потоков (если не задано, то 1)
        Integer threadsCount = Arrays.stream(args).limit(1).filter(s -> s.matches("^-?\\d+$")).mapToInt(Integer::parseInt).findFirst().orElse(1);

        //3 параметр: каталог для хранения скачанных файлов
        File dir = new File(args[2]);

        //2 параметр: считываем список ссылок в HashTable, чтобы убрать дубликаты URL,File
        Map<URL, File> urls = new Hashtable<>();
        Files.lines(Paths.get(args[1])).forEach(s -> {
            try {
                urls.put(new URL(s.split("\s")[0]), new File(dir, s.split("\s")[1]));
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
        System.out.printf("\nЗагружено: %d файлов, %d.%03d MB\n",
                listener.getCount(),
                listener.getSize() / (1024 * 1024),
                listener.getSize() % (1024 * 1024));
        System.out.printf("Время: %s\n", String.format("%02d:%02d:%02d.%03d",
                time / 1000 / 3600,
                time / 1000 / 60 % 60,
                time / 1000 % 60,
                time % 1000));

        System.out.printf("Средняя скорость: %f MB/s\n",
                (double) ((listener.getSize() / (1024 * 1024)) / (time / 1000d)) / listener.getCount());
    }

    public HttpDownloader (URL url, File file, IDownloadListener listener) {
        this.url = url;
        this.file = file;
        this.listener = listener;
        this.downloadBegTime = System.currentTimeMillis();
    }

    public String getFileName() {
        return file.getName();
    }

    public long getDownloadTime() {
        return this.downloadEndTime - this.downloadBegTime;
    }

    public int getFileSize() {
        return fileSizeInByte;
    }

    @Override
    public void run() {
        downloadFile();
    }

    private void downloadFile() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            long time = System.currentTimeMillis();

            listener.onDownloadBeg(this);

            HttpURLConnection urlConn = (HttpURLConnection) this.url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoInput(true);
            urlConn.connect();

            BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
            int nRead;
            byte[] buffer = new byte[128];
            while ((nRead = bis.read(buffer)) != -1) {
                fos.write(buffer,0, nRead);
                fileSizeInByte += nRead;
            }

            downloadEndTime = System.currentTimeMillis();

            listener.onDownloadEnd(this);
        } catch (Exception ignore) {
            listener.onError(String.format("Ошибка загрузки файла по url %s\n",url.toString()));
        }
    }
}

//класс для callback, подсчитывает размер файлов и количество успешно загруженных файловё
class Listener implements IDownloadListener {
    private int count;
    private int size;

    public int getCount() {
        return count;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void onError(String message) {
        System.out.printf(message);
    }

    @Override
    public void onDownloadBeg(HttpDownloader instance) {
        System.out.printf("Загружается файл: %s\n",instance.getFileName());
    }

    @Override
    public void onDownloadEnd(HttpDownloader instance) {
        //считаем размер и количество скачанных файлов
        synchronized (this) {
            size += instance.getFileSize();
            count++;
        }

        System.out.printf("Файл %s загружен: %d.%03dMByte за %s\n",
                instance.getFileName(),
                instance.getFileSize() / (1024 * 1024),
                instance.getFileSize() % (1024 * 1024),
                String.format("%02d:%02d:%02d.%03d",
                        instance.getDownloadTime() / 1000 / 3600,
                        instance.getDownloadTime() / 1000 / 60 % 60,
                        instance.getDownloadTime() / 1000 % 60,
                        instance.getDownloadTime() % 1000));
    }
}

interface IDownloadListener{
    void onError(String message);
    void onDownloadBeg(HttpDownloader instance);
    void onDownloadEnd(HttpDownloader instance);
}
