package web.crawler.model;

import java.util.*;

public class ResultPageDto {

    private String urlPage;
    private Map<String, Integer> wordCountMap = new HashMap<>();
    private Set<String> linksSet = new HashSet<>();

    public Set<String> getLinksSet() {
        return linksSet;
    }

    public void setLinksSet(Set<String> linksSet) {
        this.linksSet = linksSet;
    }

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
