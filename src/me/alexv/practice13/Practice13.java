package me.alexv.practice13;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Practice13 {

    interface DownloadListener{
        void onError();
        void onDownload(Wiki wiki);
    }

    private static void searchByWiki(final String searchString, final DownloadListener downloadListener) {
        new Thread(() -> {
            try {
                //2. Сделать запрос к серверу.
                //3. Распарсить ответ.
                URL url = getUrl(searchString);
                Wiki jsonWiki = jsonWiki(url);
                //4. Вывести результат.
                downloadListener.onDownload(jsonWiki);
            } catch (Exception e) {
                downloadListener.onError();
            }
        }).start();
    }

    private static URL getUrl(String searchString) throws UnsupportedEncodingException, MalformedURLException {
        String HOST = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";
        URL url = new URL(HOST + URLEncoder.encode(searchString, "UTF-8"));
        return url;
    }

    private static Wiki jsonWiki(URL url) throws Exception {
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setDoInput(true);
        urlConn.connect();

        InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
        Gson gson = new Gson();

        Wiki wiki = gson.fromJson(isr, Wiki.class);
        if (wiki.toString() == "")
            throw new Exception("Not possible to parse the object!");

        return wiki;
    }

    public static void main(String[] args) {
        //Задача разбивается на 4 этапа:
        //1. Считать запрос.
        System.out.print("Укажите поисковый запрос:");
        Scanner scanner = new Scanner(System.in);
        String searchString = scanner.nextLine();

        /*2-4*/
        DownloadListener downloadListener = new DownloadListener() {
            @Override
            public void onError() {
                System.out.println("Error for request..");
            }

            @Override
            public void onDownload(Wiki wiki) {
                System.out.println(wiki.toString());
            }
        };
        searchByWiki(searchString, downloadListener);
    }
}