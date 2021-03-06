<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&display=swap" rel="stylesheet">

    <title>Weather App</title>
</head>
<body>

<div class="container" id="mainDiv">
    <h1>Weather In The City</h1>

    <form>
        <div class="mb-3">
            <label for="city">Input a city name</label>
            <input class="form-control" id="city" name="city" aria-describedby="Forecast city" placeholder="City name">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<div class="container" id="forecastDiv">
    <c:if test="${error}">
        <div class="alert alert-danger" role="alert">
            The city name is incorrect, please try another name.
        </div>
    </c:if>
    <c:if test="${!error}">
        <div class="alert alert-primary" role="alert">
            The weather in <c:out value="${cityName} is ${description} "/>
            <img  id="forecastImage" src='http://openweathermap.org/img/wn/<c:out value="${icon}"/>@2x.png'>
            The temperature is <c:out value="${temperature}"/> &#8451;.
            The speed of wind is <c:out value="${speed}"/> m/sec.
            The air humidity is <c:out value="${humidity}"/> &#37;.
            The feels like temperature is <c:out value="${feelsLike}"/> &#8451;.
        </div>
    </c:if>
</div>

<!-- Optional JavaScript; choose one of the two! -->

<!-- Option 1: Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<!-- Option 2: Separate Popper and Bootstrap JS -->
<!--
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
-->
</body>
</html>
