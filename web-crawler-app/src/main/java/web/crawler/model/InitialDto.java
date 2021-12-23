package web.crawler.model;

import java.util.List;

public class InitialDto {

    private String inputUrl;
    private List<String> inputWords;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

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
