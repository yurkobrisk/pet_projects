package web.crawler.model;

import java.util.List;

public class InitialDto {

    private String inputUrl;
    private List<String> inputWords;

    public String getInputUrl() {
        return inputUrl;
    }

    public void setInputUrl(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public List<String> getInputWords() {
        return inputWords;
    }

    public void setInputWords(List<String> inputWords) {
        this.inputWords = inputWords;
    }
}
