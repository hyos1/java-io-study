package review.was.v5.servlet;

import review.was.httpserver.HttpRequest;
import review.was.httpserver.HttpResponse;
import review.was.httpserver.HttpServlet;

public class Site2Servlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site2</h1>");

    }
}
