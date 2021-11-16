package weather.app.servlet;

import weather.app.model.Forecast;
import weather.app.service.ForecastProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {""})
public class WeatherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Сработал метод doGet ... ");
        String city = "";

        city = concatenateToString(req.getParameter("city"));
        System.out.println(city);

        if (city != null) {
            if (city.equals("")) {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }
            ForecastProcessor processor = new ForecastProcessor();
            Forecast forecast = processor.getForecast("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=1d3e0f1f78a754b9d3f7a810ebf65783");

            if (!forecast.cod.equals("200")) {
                req.setAttribute("error", true);
                req.getRequestDispatcher("/weather.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("error", false);
            req.setAttribute("temperature", forecast.main.temp);
            req.setAttribute("cityName", forecast.name);
            req.setAttribute("description", forecast.weather.get(0).description);
            req.setAttribute("speed", forecast.wind.speed);
            req.setAttribute("humidity", forecast.main.humidity);
            req.setAttribute("feelsLike", forecast.main.feels_like);
            req.setAttribute("icon", forecast.weather.get(0).icon);
            req.getRequestDispatcher("/weather.jsp").forward(req, resp);
        } else {
            System.out.println("Сработал else блок");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }

    private String concatenateToString(String stringWithSpaces) {
        if (stringWithSpaces != null) {
            return stringWithSpaces.replaceAll(" ", "+");
        } else return "";
    }
}


