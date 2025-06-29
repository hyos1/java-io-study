package review.webservice;

import review.io.member.FileMemberRepository;
import review.io.member.MemberRepository;
import review.was.httpserver.HttpServer;
import review.was.httpserver.HttpServlet;
import review.was.httpserver.ServletManager;
import review.was.httpserver.servlet.DiscardServlet;
import review.was.httpserver.servlet.annotation.AnnotationServletV3;

import java.io.IOException;
import java.util.List;

public class MemberServiceMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        MemberRepository memberRepository = new FileMemberRepository();
        MemberController memberController = new MemberController(memberRepository);
        HttpServlet servlet = new AnnotationServletV3(List.of(memberController));
        ServletManager servletManager = new ServletManager();
        servletManager.add("/favicon.ico", new DiscardServlet());
        servletManager.setDefaultServlet(servlet);

        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
