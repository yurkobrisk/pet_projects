package web.crawler.servlet;

import web.crawler.model.InitialDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InitialParamsBinder {

    /**
     * Method binding input params from web form to object InitialDto
     * @param req http request
     * @return new object initialDto
     */
    public InitialDto bind(HttpServletRequest req) {
        final String url = req.getParameter("url");
        final String words = req.getParameter("words");
        final int depth = Integer.parseInt(req.getParameter("depth"));

        InitialDto initialDto = new InitialDto();

        initialDto.setInputUrl(url);
        initialDto.setInputWords(
                Arrays.stream(words.split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
        );
        initialDto.setDepth(depth);

        return initialDto;
    }
}
