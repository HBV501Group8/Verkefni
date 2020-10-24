package is.hi.hbv501GEfnahagsspa.Controllers;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller

public class UserController {

    private UserService userService;

    @Autowired
    public UserController( UserService userService){
        this.userService = userService;
    }
    @Autowired
    @RequestMapping("/")

    public String Home() {

        //model.addAttribute("movies", movieService.findAll() );
        return "testLogin";
    }
    @RequestMapping("/register")
    public String register(Model model) {
        return "Register";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userlogin(Model model, HttpSession session) {
        return "testLogin";
    }





    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String userregister(HttpServletRequest request, Model model, HttpSession session, User user) {
        String Name = request.getParameter("Name");
        String userName = request.getParameter("login_username");
        String password = request.getParameter("password");
        String Email = request.getParameter("user_email");

        user.setName(Name);
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setEmail(Email);
        userService.save(user);
        return "testLogin";

    }
    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public String userlogin(User user,HttpServletRequest request, Model model, HttpSession session) {
        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");

        User userexists =  userService.findByuserName(userName);
        User passswexists =  userService.findByuserPassword(userPassword);
        if(userexists!=null && passswexists!=null) {
            return "showImage";
        }
        return "testLogin";
    }



}


