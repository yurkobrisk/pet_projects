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

    <title>Web Crawler App</title>
  </head>
  <body>
	<div class="container" id="mainDiv">
		
		<h1>Search Frequent Words</h1>

		<form>
		  <div class="mb-3">
		    <label for="url" class="form-label">Input a URL address</label>
		    <input class="form-control" id="url" name="url" aria-describedby="URL Address" placeholder="https://www.google.com/">

        <div class="row">
          <div class="col-3" id="depth-label">Select link depth: </div>
          <div class="col-2">
            <select class="form-select" id="depth-select" name="depth" aria-label="Default link depth">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
            </select>
          </div>
          <div class="col-4" id="visited-pages-label">Max Visit Pages: </div>
          <div class="col-3">
            <!-- <fieldset disabled> -->
              <input type="text" class="form-control" id="visited-pages-input" name="maxlinks" placeholder="10000 max">
            <!-- </fieldset> -->
          </div>
        </div>

		    <label for="words" class="form-label" id="words-label">Input words separated by ","</label>
        <textarea class="form-control" id="words" name="words" placeholder="Input, words, here..."></textarea>
      </div>

		  <c:if test="${errorFill}">
        <div class="alert alert-danger" role="alert">
         One of the fields are not filled, please try fill all input fields correctly.
        </div>
      </c:if>

		  <c:if test="${errorUrl}">
        <div class="alert alert-danger" role="alert">
         The URL address is incorrect, please try another address.
        </div>
      </c:if>

      <c:if test="${errorLinks}">
        <div class="alert alert-danger" role="alert">
         The value of max visit pages is incorrect, please try again.
        </div>
      </c:if>

		  <button type="submit" class="btn btn-primary" id="button-submit">Submit</button>
		</form>

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

