package web.crawler.servlet;

import web.crawler.model.InitialDto;
import web.crawler.model.ResultDto;
import web.crawler.service.SearchProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "")
public class WebCrawlerServlet extends HttpServlet {

    private final InitialParamsBinder initialParamsBinder
            = new InitialParamsBinder();
    private final SearchProcessor searchProcessor
            = new SearchProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("метод doGet сработал ...");

        String url = req.getParameter("url");
        url = setValue(url);
        String words = req.getParameter("words");
        words = setValue(words);

        if (url.equals("") || words.equals("")) {
            System.out.println("One field not fill!");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } else {
            System.out.println("All fields are fill.");
            if (!searchProcessor.isValidURL(url)) {
                req.setAttribute("error", true);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                InitialDto initialDto = initialParamsBinder.bind(req);
                ResultDto resultDto = searchProcessor.search(initialDto);
//            resultDto.getResultPageDtoList()
//                    .forEach(map -> {
//                        map.getWordCountMap()
//                                .forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));
//                    });
                req.setAttribute("resultList", resultDto);
                req.getRequestDispatcher("/result.jsp").forward(req, resp);
            }
        }
    }

    private String setValue(String value) {
        if (value != null) {
            return value;
        } else return "";
    }
}
