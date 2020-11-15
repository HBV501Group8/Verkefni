package is.hi.hbv501GEfnahagsspa.Controllers;

import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller

public class UserController {

    private UserService userService;
    private ForecastService  forecastService;

    @Autowired

    // Smiður binda user service

    public UserController( UserService userService, ForecastService forecastService)
    {
        this.userService = userService;
        this.forecastService = forecastService;

    }
    //@Autowired

    /**
     * Grípur fyrirspurn login sem birtir Login template
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @return endursendum notandann aftur á rót vefs
     */


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String Home(User user,HttpSession session) {

        session.setAttribute("loggedInUser","");
        session.setAttribute("userType", "");

        return "testLogin";
    }

    /**
     * Grípur fyrirspurn þegar notandi er nýskráður
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @return endursendum notandann aftur á rót vefs
     */


    @RequestMapping("/register")
    public String register(Model model) {
        return "Register";
    }


    /**
     * Grípur fyrirspurn þegar notandi er nýskráður
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @return endursendum notandann aftur á rót vefs
     */

    /**
     * Grípur fyrirspurn þegar notandi er nýskráður
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param session hlutur af taginu HttpSession sem geymir key-value pör
     * @return Notandi er endursendur á login síðu
     */

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
        if(userexists==null) {
            user.setName(Name);
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setEmail(Email);
            user.setEnabled(true);
            user.setAdmin(false);
            userService.save(user);
        }

        return "testLogin";
    }




    /**
     * Grípur fyrirspurn þegar notandi er authenticated
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param session hlutur af taginu HttpSession sem geymir key-value pör
     * @return ef result hefur engar villur þá endursendum við notandann á heimasvæðið ef hann er ekki admin
     * annars er farið í notandastýringu
     */

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
                session.setAttribute("loggedInUser", userexists.userName);
                session.setAttribute("userType", "user");
                String userlogged = (String) session.getAttribute("loggedInUser");
                model.addAttribute("forecasts", forecastService.findAll());
                model.addAttribute("userlogged", userlogged);

                return "forecast";
            }
            else {
                // Fara yfir á admin
                session.setAttribute("loggedInUser", userexists.userName);
                session.setAttribute("userType", "admin");
                String userlogged = (String) session.getAttribute("loggedInUser");
                model.addAttribute("userlogged", userlogged);
                model.addAttribute("users", userService.findAll());
                return "users";
                }
            }
        return "testLogin";
    }

    /**
     * Grípur fyrirspurn eftir að admin er innskárður
     * @param user hlutur af taginu User sem mun geyma email og password í innskráningu
     * @return strengur skilar admin á yfirlit notenda
     */

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
    /**
     * Grípur fyrirspurn þegar admin vill leita í nteondum
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param user hlutur af taginu User sem mun geyma email og password í innskráningu
     * @return strengur sem er nafnið á html síðunni sem verður birt
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String usersGET2(Model model,User user){
        //model.addAttribute("users", userService.findUsersByUserNameContaining()
        return "userSearch";
    }

    /**
     * Grípur fyrirspurn sem birtir notandalista efit leit
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param session hlutur af taginu HttpSession sem geymir key-value pör
     * @return ef result hefur engar villur þá endursendum við notandann á heimasvæðið annars leyfum við honum að
     *         reyna að logga sig inn aftur
     */

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

    /**
     * Grípur fyrirspurn þegar notandi er eyddur
     * @param id er lykill notanda til að eyða
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @return endursenda admin á yfirlistsíðu
     */


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String usersedit(@PathVariable("id") String strid,HttpServletRequest request,Model model){
        int intid = Integer.parseInt(strid);
        model.addAttribute("user", userService.findById(intid));
        return "test";
    }

    /**
     * Grípur fyrirspurn þegar notandi er uppfærður af admin
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param session hlutur af taginu HttpSession sem geymir key-value pör
     * @return Endursenda admin á yfirlitsíðu
     */

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String userupdate(HttpServletRequest request, Model model, HttpSession session, User user) {

        // Ná í breytur

        String Name = request.getParameter("Name");
        String Email = request.getParameter("Email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        String isEnabled = request.getParameter("isEnabled");
        if(isEnabled==null)
            isEnabled="";
        Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
        // Kalla á uppfæra notendur
        User userUpdate =  userService.findByuserName(userName);

        userUpdate.setName(Name);
        userUpdate.setUserPassword(password);
        userUpdate.setEmail(Email);

        // Uppfæra hvort notnndi sé virkur


        if(isEnabled.equals("on"))
            userUpdate.setEnabled(true);
        if(!isEnabled.equals("on"))
            userUpdate.setEnabled(false);

        userService.save(userUpdate);


        model.addAttribute("users", userService.findAll());
        return "users";
    }


}


