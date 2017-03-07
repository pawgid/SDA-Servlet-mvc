package pl.sda.servlet;

import file.FileOperations;
import file.User;
import org.apache.commons.lang3.StringUtils;
import pl.sda.controller.Controller;
import pl.sda.controller.CookieController;
import pl.sda.controller.LoginController;
import pl.sda.controller.UserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HelloServlet extends HttpServlet {

    private Map<String, Controller> controllerMap;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();

        String[] split = body.split(" ");

        User user = new User();
        user.setFirstName(split[0]);
        user.setLastName(split[1]);
        user.setAge(new Integer(split[2]));

        File file = new File("C:\\Users\\RENT\\Desktop\\test.txt");
        FileOperations.addUserToFile(user, file);

        resp.getWriter().write("Wszystko OK");
        resp.setStatus(201);
    }

    @Override
    public void init() throws ServletException {
        this.controllerMap = new HashMap<String, Controller>();
        this.controllerMap.put("users", new UserController());
        this.controllerMap.put("login", new LoginController());
        this.controllerMap.put("cookie", new CookieController());
        this.controllerMap.put("default", (request, response) ->
                response.getWriter()
                        .write("<h1>Hello from default controller</h1>")
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String requestURI = request.getRequestURI();
        String relativePath = requestURI.substring(StringUtils.ordinalIndexOf(requestURI, "/", 2) + 1);
        Controller controller = controllerMap.get(relativePath);
        if (controller == null) {
            controller = controllerMap.get("default");
        }
        controller.handleGet(request, response);
    }
}
