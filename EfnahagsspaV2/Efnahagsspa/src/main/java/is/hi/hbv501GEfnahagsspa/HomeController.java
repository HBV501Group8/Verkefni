package is.hi.hbv501GEfnahagsspa;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller

public class  HomeController {

    private UserService userService;
    @Autowired
    public HomeController(UserService userService){this.userService = userService;}
    @RequestMapping("/")
    public String Home(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "testLogin";
    }
    @RequestMapping("/register")
    public String register(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "Register";
    }



    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public String userlogin(HttpServletRequest request, Model model, HttpSession session) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        userName = userName.trim();
        password = password.trim();
        if(userName.equals("Sigurjon") && password.equals("test")) {
            session.setAttribute("Name", userName);
            return "Loggedin";
       }else {

            return "testLogin";
        }

        }
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String userregister(HttpServletRequest request, Model model, HttpSession session, User user) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        userName = userName.trim();
        password = password.trim();
        user.setUserNane(userName);
        user.setUserPassword(password);
        userService.save(user);
        return "testLogin";

    }

}


