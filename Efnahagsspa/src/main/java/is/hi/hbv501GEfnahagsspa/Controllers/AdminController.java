package is.hi.hbv501GEfnahagsspa.Controllers;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
/*
    AdminController.java
    Controller for admin level accounts which can lookup other users
    and edit or delete them.

    List of methods
        - adminGetUsers: Returns page with list of registered users
        - adminSearchUsers: Returns page where admin can submit search query for registered users by
                            name
        - adminSearchUsersResults: Returns list of search results from adminSearchUsers
        - adminDeleteUser: Deletes specified user
        - adminEditUser: Returns page where admin can edit attributes of existing User entity
        - adminUserUpdate: Updates specified User entity with input from adminEditUsers
*/

    private UserService userService;
    private ForecastService  forecastService;

    @Autowired
    public AdminController( UserService userService)
    {
        this.userService = userService;
    }

/*
    adminGetUsers
        Use: Catches url query "./users"
        Parameters: model, Model object
        Returns: template users.html populated with key/value pairs from model.
                 model key value pairs are:
                    - "users": list of all users in database
*/
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String adminGetUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

/*
    adminSearchUsers
        Use: Catches url query "./search"
        Parameters: None
        Returns: template usersearch.html
*/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String adminSearchUsers(){
        return "usersearch";
    }

/*
    adminSearchUsersResults
        Use: Catches url query ./userSearch
        Parameters: request, HttServletRequest containing parameter "userName"
                    model, Model object
        Returns: template users.html populated with key/value pairs from model.
                 model key value pairs are:
                    - "users": List of users with username attribute containing username
*/
    @RequestMapping(value = "/userSearch", method = RequestMethod.GET)
    public String adminSearchUsersResults(HttpServletRequest request, Model model){
        String username = request.getParameter("userName");
        model.addAttribute("users", userService.findUsersByUsernameContaining(username));
        return "users";
    }

/*
    adminDeleteUser
        Use: Catches url query "./delete/{id}"
        Parameters: strid, String extracted from path variable {id}
                    model, Model object
        Returns: Deletes user with id equal to strid and then returns template users.html
                 populated with key/value pairs from model.
                 model key value pairs are:
                    - "users": All users in database following deletion of User with id strid
*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String adminDeleteUser(@PathVariable("id") String strid, Model model){
        int intid = Integer.parseInt(strid);
        User user = userService.findById(intid);
        if(intid!=1)
            userService.delete(user);
        model.addAttribute("users", userService.findAll());
        return "users";
    }

/*
    adminUserEdit
        Use: Catches url query "./edit/{id}"
        Parameters: strid, String extracted from path variable {id}
                    model, Model object
        Returns: Returns template useredit.html populated with key/value pairs from model.
                 model key value pairs are:
                    - "user": User with id strid
*/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String adminUserEdit(@PathVariable("id") String strid, Model model){
        int intid = Integer.parseInt(strid);
        model.addAttribute("user", userService.findById(intid));
        return "useredit";
    }

/*
    adminUserUpdate
        Use: Catches url query "./updateUser"
        Parameters: request, HttServletRequest containing parameters:
                        "Name", "Email", "userName", "userPassoword", "isEnabled", "isAdmin"
                    model, Model object
        Returns: Updates attributes of user with id equal to strid and then returns
                 template users.html populated with key/value pairs from model.
                 model key value pairs are:
                    - "users": All users in database
*/
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String adminUserUpdate(HttpServletRequest request, Model model) {

        // Get parameters from request object
        String Name = request.getParameter("Name");
        String Email = request.getParameter("Email");
        String username = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        String isEnabled = request.getParameter("isEnabled");
        if(isEnabled==null)
            isEnabled="";
        Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

        // Find users which hava a username containing
        User userUpdate =  userService.findByUsername(username);

        // Update attributes using parameters from request
        userUpdate.setName(Name);
        userUpdate.setUserPassword(password);
        userUpdate.setEmail(Email);
        userUpdate.setAdmin(isAdmin);

        if(isEnabled.equals("on"))
            userUpdate.setEnabled(true);
        if(!isEnabled.equals("on"))
            userUpdate.setEnabled(false);


        // Save changes to user in database
        userService.save(userUpdate);

        // return template with list of users
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}
