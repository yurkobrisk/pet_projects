package web.crawler.servlet;

import org.apache.commons.lang3.math.NumberUtils;
import web.crawler.model.InitialDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InitialParamsBinder {

    public static final int MAX_VISIT_PAGES = 10000;

    /**
     * Method binding input params from web form to object InitialDto
     * @param req http request
     * @return new object initialDto
     */
    public InitialDto bind(HttpServletRequest req) {
        final String url = req.getParameter("url");
        final String words = req.getParameter("words");
        final int depth = Integer.parseInt(req.getParameter("depth"));
        final int maxLinks = parseString(req.getParameter("maxlinks"));

        InitialDto initialDto = new InitialDto();

        initialDto.setInputUrl(url);
        initialDto.setInputWords(
                Arrays.stream(words.split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
        );
        initialDto.setDepth(depth);
        initialDto.setMaxlinks(maxLinks);

        return initialDto;
    }

    /**
     * Method parse string into number
     * @param maxLinks any input string
     * @return max number pages
     */
    public int parseString(String maxLinks) {
        if (NumberUtils.isDigits(maxLinks)
                && maxLinks.length() <= ("" + MAX_VISIT_PAGES).length()) {
            return Integer.parseInt(maxLinks) <= MAX_VISIT_PAGES ? Integer.parseInt(maxLinks) : -1;
        }
        return -1;
    }
}
