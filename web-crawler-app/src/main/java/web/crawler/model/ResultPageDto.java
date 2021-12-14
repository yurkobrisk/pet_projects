package web.crawler.model;

import java.util.HashMap;
import java.util.Map;

public class ResultPageDto {

    private String urlPage;
    private Map<String, Integer> wordCountMap = new HashMap<>();

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(Map<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }
}
