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
        User userexists =  userService.findByuserName(userName);
        if(userexists==null) {
            user.setName(Name);
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setEmail(Email);
            userService.save(user);
        }
        return "testLogin";
    }
    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public String userlogin(User user,HttpServletRequest request, Model model, HttpSession session) {
        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");
        User userexists =  userService.findByuserName(userName);
        User passswexists =  userService.findByuserPassword(userPassword);
//      //  User userAdmin =  userService.findByAdmin(userName);
        Boolean blnadmin = userexists.getAdmin();
        System.out.println(blnadmin+"");


        if(userexists!=null && passswexists!=null)  {
            if(!blnadmin) {
                return "showImage";
            }
            else {
                model.addAttribute("users", userService.findAll());
                return "users";
                }
            }
        return "testLogin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping(value = "/usersman", method = RequestMethod.GET)
    public String usersfancy(Model model){
        model.addAttribute("users", userService.findAll());
        return "test";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String users(User user,HttpServletRequest request,Model model){
       // String id = request.getParameter("userID");
       // System.out.println("test");
        User usertest = userService.findById(161);
       //return "users";
        userService.delete(usertest);
        model.addAttribute("user", userService.findAll());
        return "users";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String usersedit(User user,HttpServletRequest request,Model model){
        // String id = request.getParameter("userID");
        // System.out.println("test");
        //User usertest = userService.findById(161);
        //return "users";
        //userService.delete(usertest);
        model.addAttribute("user", userService.findById(1));
        return "test";
    }
}


