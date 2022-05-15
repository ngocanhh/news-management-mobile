package com.group22.news_management.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.webkit.WebView;
import com.group22.newsmanagerment.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class HTMLParser extends AsyncTask<String, String, String> {

    private Activity parentContext;

    public HTMLParser(Activity parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String document = "";
        try {
            // lay toan bo HTML -> lay noi dung the body -> lay noi dung the article
            Document documentElement = Jsoup.connect(strings[0]).get();
            document = documentElement.toString();
            Element body = documentElement.body();
            Element article = body.getElementById("article_detail");
            publishProgress(article.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        WebView webView = parentContext.findViewById(R.id.webView);
        String content = values[0];
        String head = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "\n" +
                "    <style>\n" +
                "        img {\n" +
                "            width: 100%;\n" +
                "            height: 200px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>";
        String endTag = "</body>\n" +
                "</html>";
        webView.loadData(head + content + endTag, "text/html", "UTF-8");

    }

}
