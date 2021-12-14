package web.crawler.service;

import web.crawler.model.InitialDto;
import web.crawler.model.ResultDto;
import web.crawler.model.ResultPageDto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchProcessor {

    final private HttpLoader httpLoader = new HttpLoader();

    /**
     * Method method looks for related pages and counts the number of references on them
     * @param initialDto start object
     * @return list of ResultPageDto
     */
    public ResultDto search(InitialDto initialDto) {
        String url = initialDto.getInputUrl();
        String content = httpLoader.get(url);

        ResultDto resultDto = new ResultDto();
        resultDto.getResultPageDtoList()
                .add(parse(url, content, initialDto.getInputWords()));

        return resultDto;
    }

    /**
     * Method calculate the words quantity from web page content
     * @param url url address web page
     * @param pageContent web page content
     * @param words array of search terms
     * @return object ResultPageDto
     */
    private ResultPageDto parse(String url, String pageContent, List<String> words) {
        String content = pageContent.toLowerCase();

        ResultPageDto resultPageDto = new ResultPageDto();
        resultPageDto.setUrlPage(url);
        for (String word : words) {
            Pattern pattern = Pattern.compile(word);
            Matcher matcher = pattern.matcher(content);
            int counter = 0;
            while (matcher.find()) {
                counter ++;
            }
            resultPageDto.getWordCountMap().put(word, counter);
        }
        resultPageDto.setWordCountMap(
            resultPageDto.getWordCountMap()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new)));
        return resultPageDto;
    }

    /**
     * Method validate input URL
     * @param sUrl input URL
     * @return true if URL is valid
     */
    public boolean isValidURL(String sUrl) {
        try {
            URL url = new URL(sUrl);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
