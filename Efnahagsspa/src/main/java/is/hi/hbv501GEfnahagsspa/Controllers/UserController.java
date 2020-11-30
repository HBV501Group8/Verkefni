package is.hi.hbv501GEfnahagsspa.Controllers;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
/*
    UserController.java
    Controller for all interactions and requests related to the User entity.
    Handles registering of new users, login and logout of existing users.

    List of methods
        - newUserRegistered: Registers new user in database using input from register.html
        - userLogin: Handles login of existing users
        - userLogout: Logs active user out of system
*/

    private UserService userService;
    private ForecastService  forecastService;

    @Autowired
    public UserController( UserService userService, ForecastService forecastService)
    {
        this.userService = userService;
        this.forecastService = forecastService;

    }



/*
    newUserRegistered
        Use: Catches url query "./registeruser"
        Parameters: request, HttServletRequest containing parameters:
                        "Name", "login_username", "password", "user_email"
                    model, Model object
        Returns: If chosen username does not exist in database then create new user and
                 add to database. Then return template login.html populated with key/value pairs from model.
                 Else if chosen username already in database return template register.html with error message.
                 model key value pairs are:
                    - "errormsg": String containing either an error message or confirming user registration
*/
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String newUserRegistered(HttpServletRequest request, Model model, HttpSession session, User user) {

        // Extract parameters from request
        String Name = request.getParameter("Name");
        String userName = request.getParameter("login_username");
        String password = request.getParameter("password");
        String Email = request.getParameter("user_email");

        // Check if user with chosen username already exists in database.
        // If not then register user, otherwise return error message
        if(userService.findByUsername(userName) == null) {
            user.setName(Name);
            user.setUsername(userName);
            user.setUserPassword(password);
            user.setEmail(Email);
            user.setEnabled(true);
            user.setAdmin(false);

            userService.save(user);
            model.addAttribute("errormsg", "Notandi hefur verið nýskráður");
            return "login";
        } else {
            model.addAttribute("errormsg", "Villa notandi  með þetta notendanafn er þegar til");
            return "register";
        }
    }


/*
    userLogin
        Use: Catches url query "./loginsubmit"
        Parameters: request, HttServletRequest containing parameters:
                        "Name", "login_username", "password", "user_email"
                    model, Model object
                    session, HttpSession session
        Returns: If chosen username does not exist in database create new user and add to database.
                 Then return template login.html populated with key/value pairs from model.
                 Else if chosen username already in database return template register.html with error message.
                 model key value pairs are:
                    - "errormsg": String containing either an error message or confirming user registration
*/
    @RequestMapping(value = "/loginsubmit", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request, Model model, HttpSession session) {

        // Extract parameters from request
        String username = request.getParameter("username");
        String userPassword = request.getParameter("password");

        // Retrive user from database
        User user =  userService.findByUsername(username);
        
        // If User with typed user name does exist and that User's password from database
        // matches password typed by user then log user in as either admin or normal
        // user depending.
        // Otherwise direct back to login and display error
        if((user!=null) && (userPassword.equals(user.getUserPassword())))  {
            // Check if User account is admin account or not and redirect
            // accordingly
            if(!user.getAdmin()) {
                // Mark signed in user as activeUser
                session.setAttribute("activeUser", user);

                // Add required model attributes and return listforecast
                model.addAttribute("forecasts", forecastService.findAllByUser(user));
                model.addAttribute("username", user.getUsername());

                return "listforecasts";
            }
            else {
                // Mark signed in user as activeUser
                session.setAttribute("activeUser", user);

                // Add required model attributes and return user
                model.addAttribute("username", user.getUsername());
                model.addAttribute("users", userService.findAll());

                return "users";
            }
        }
        else {
            model.addAttribute("errormsg","Villa Notandi er ekki til");
            return "login";
        }
    }

/*
    userLogout
        Use: Catches url query "./logout"
        Parameters: session, HttpSession
        Returns: Removes user from session attribute activeUser and then returns
                 template login.html.
*/
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userLogout(HttpSession session) {
        session.setAttribute("activeUser", null);
        return "login";
    }
}


