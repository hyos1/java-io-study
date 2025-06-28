package review.was.httpserver.servlet;

import review.was.httpserver.HttpRequest;
import review.was.httpserver.HttpResponse;
import review.was.httpserver.HttpServlet;

public class InternalErrorServlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatusCode(500);
        response.writeBody("<h1>Internal Error</h1>");
    }
}
