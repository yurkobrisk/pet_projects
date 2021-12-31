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
import java.util.Map;

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
        String numberLinks = req.getParameter("maxlinks");

        if (url.equals("") && words.equals("")) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else if (url.equals("") || words.equals("")) {
            req.setAttribute("errorFill", true);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else if (!searchProcessor.isValidURL(url)) {
            req.setAttribute("errorUrl", true);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else if (initialParamsBinder.parseString(numberLinks) == -1) {
            req.setAttribute("errorLinks", true);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            InitialDto initialDto = initialParamsBinder.bind(req);
            ResultDto resultDto = searchProcessor.search(initialDto);
            Map<String, Integer> resultMap = searchProcessor.calculateNumberWords(resultDto);
            req.setAttribute("resultMap", resultMap);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        }
    }

    private String setValue(String value) {
        if (value != null) {
            return value;
        } else return "";
    }
}
