package is.hi.hbv501GEfnahagsspa.Controllers;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller

public class UserController {

    private UserService userService;

    @Autowired

    // Smiður binda user service

    public UserController( UserService userService){
        this.userService = userService;
    }
    @Autowired

    // Route á login

    @RequestMapping("/")
    public String Home() {
        return "testLogin";
    }
    // Route á nýskráningu  notanda

    @RequestMapping("/register")
    public String register(Model model) {
        return "Register";
    }


    // Skrá nýja notanda í gangagrunn

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String userregister(HttpServletRequest request, Model model, HttpSession session, User user) {

        // Ná í allar breutur

        String Name = request.getParameter("Name");
        String userName = request.getParameter("login_username");
        String password = request.getParameter("password");
        String Email = request.getParameter("user_email");
        User userexists =  userService.findByuserName(userName);
        User passwordexists =  userService.findByuserPassword(password);
        // Ef notandi er þegar til er ekkert gert
        if(userexists==null && passwordexists==null) {
            user.setName(Name);
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setEmail(Email);
            user.setEnabled(true);
            user.setAdmin(true);
            userService.save(user);
        }

        return "testLogin";
    }

    // Þetta route vinnur úr login notanda

    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public String userlogin(User user,HttpServletRequest request, Model model, HttpSession session) {

        // Ná í breytur

        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");

        // Ná í notanda í JPA layer

        User userexists =  userService.findByuserName(userName);
        User passswexists =  userService.findByuserPassword(userPassword);

        // Ef username er ekki til staðar þá er ekert gert

        if(userexists==null)  {
            return "testLogin";
        }
        // Athuga hvort user sé Admin
        Boolean blnadmin = userexists.getAdmin();

          // Ef notandi er til

        if(userexists!=null && passswexists!=null)  {
            // Ef notandi er Admin
            if(!blnadmin) {
                return "showImage";
            }
            else {
                // Fara yfir á admin
                model.addAttribute("users", userService.findAll());
                return "users";
                }
            }
        return "testLogin";
    }

    // Þetta route er fyrir Admin Höndla notendur

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
    // Þetta route er fyrir Admin Höndla notendur

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String usersGET2(Model model,User user){
        //model.addAttribute("users", userService.findUsersByUserNameContaining()
        return "userSearch";
    }
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String usersList(HttpServletRequest request,Model model,User user){
        String userName = request.getParameter("userName");
        model.addAttribute("users", userService.findUsersByUserNameContaining(userName));
        return "users";
    }
    // Route fyrir að eyða notanda samkvæmt ID

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String users(@PathVariable("id") String strid,User user,HttpServletRequest request,Model model){
        int intid = Integer.parseInt(strid);
        User usertest = userService.findById(intid);
        if(intid!=1)
            userService.delete(usertest);


        model.addAttribute("users", userService.findAll());
        return "users";
    }

    //Þetta route er til að birta breyta notanda form

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String usersedit(@PathVariable("id") String strid,HttpServletRequest request,Model model){
        int intid = Integer.parseInt(strid);
        model.addAttribute("user", userService.findById(intid));
        return "test";
    }

    // Uppfæra notanda

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String userupdate(HttpServletRequest request, Model model, HttpSession session, User user) {

        // Ná í breytur

        String Name = request.getParameter("Name");
        String Email = request.getParameter("Email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        String isEnabled = request.getParameter("isEnabled");
        Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
        // Kalla á uppfæra notendur
        User userUpdate =  userService.findByuserName(userName);

        userUpdate.setName(Name);
        userUpdate.setUserPassword(password);
        userUpdate.setEmail(Email);

        // Uppfæra hvort notnndi sé virkur

        String strcheck = isEnabled;
        if(strcheck.equals("on"))
            userUpdate.setEnabled(true);
        if(!strcheck.equals("on"))
            userUpdate.setEnabled(false);

        userService.save(userUpdate);


        model.addAttribute("users", userService.findAll());
        return "users";
    }
}


