package is.hi.hbv501GEfnahagsspa.Controllers;




import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    public AuthService authService;
    @Autowired
    @RequestMapping("/")

    public String Home() {

        //model.addAttribute("movies", movieService.findAll() );
        return "testLogin";
    }
    @RequestMapping("/register")
    public String register(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "Register";
    }
    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public String userlogin(HttpServletRequest request, Model model, HttpSession session, User user, AuthService authService) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        String  userIsFound = authService.findByuserName(userName).toString();
        String  passwIsFound = authService.findByuserPassword(password).toString();
        if(userName.equals(userIsFound) && password.equals(passwIsFound)) {
            session.setAttribute("Name", userName);
            return "Loggedin";
        }else {

            return "testLogin";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userlogin(Model model, HttpSession session) {
            return "testLogin";
     }





    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String userregister(HttpServletRequest request, Model model, HttpSession session, User user) {
        String Name = request.getParameter("user_Name");
        String userName = request.getParameter("login_username");
        String password = request.getParameter("password");
        String Email = request.getParameter("user_email");


        user.setName(Name);
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setEmail(Email);
        authService.save(user);
        return "testLogin";

    }

}
