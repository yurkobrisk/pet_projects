package web.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.crawler.model.InitialDto;
import web.crawler.model.ResultDto;
import web.crawler.model.ResultPageDto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
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
        int depth = initialDto.getDepth();

        Set<String> linksSet = new HashSet<>();
        ResultDto resultDto = new ResultDto();
        resultDto.getResultPageDtoList()
                    .add(parse(url, content, initialDto.getInputWords()));

        for (int i = 1; i < depth; i++) {
            linksSet.addAll(getAllLinksFromResultDto(resultDto));
            System.out.println("Количество ссылок на " + i + "-м уровне = " + linksSet.size());
            for (String link : linksSet) {
                content = httpLoader.get(link);
                resultDto.getResultPageDtoList()
                        .add(parse(link, content, initialDto.getInputWords()));
            }
        }
        return resultDto;
    }

    /**
     * Method calculate number of words in all ResultPageDto maps.
     * @param resultDto List of ResultPageDto.
     * @return sorted and concatenated map from ResultPageDto objects.
     */
    public Map<String, Integer> calculateNumberWords(ResultDto resultDto) {
        Map<String, Integer> resultMap = new HashMap<>();

        resultDto.getResultPageDtoList().stream()
                .map(ResultPageDto::getWordCountMap)
                .forEach(map -> map.forEach((k, v) -> resultMap.merge(k, v, Integer::sum)));

        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Method combines all links from all ResultPageDto`s sets
     * @param resultDto List of ResultPageDto.
     * @return merged links set
     */
    private Set<String> getAllLinksFromResultDto(ResultDto resultDto) {
        Set<String> resultSet = new HashSet<>();

        resultDto.getResultPageDtoList()
                .stream()
                .map(ResultPageDto::getLinksSet)
                .forEach(resultSet::addAll);

        return resultSet;
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
        resultPageDto.setLinksSet(parseLinks(content));

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
            String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sUrl);
            return matcher.matches();
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Method get all links from input page, validate them and output set of links
     * @param html page content
     * @return set of links
     */
    private HashSet<String> parseLinks(String html) {

        Document document = Jsoup.parse(html);
        Map<String, Boolean> linkMap = new HashMap<>();
        return document.select("a[href]")
                .stream()
                .distinct()
                .map(link -> link.attr("href"))
                .filter(this::isValidURL)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
