package is.hi.hbv501GEfnahagsspa.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
/*
    HomeController.java
    Controller used to handle general navigation between pages (those that
    do not involve any retrival or manipulation of entities).

    List of methods
        - home: Directs from root to login.html template
        - register: Directs to register.html template
        - forecastForm: Directs to forecast generation form in forecastgeneration.html.


*/

/*
    home
        Use: Catches url query "./"
        Parameters: None
        Returns: Directs to template login.html.
*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() { return "login"; }

/*
    register
        Use: Catches url query "./register"
        Parameters: None
        Returns: Directs to template register.html.
*/
    @RequestMapping("/register")
    public String register() { return "register"; }

/*
    forecastForm
        Use: Catches url query "./forecastgeneration"
        Parameters: None
        Returns: Directs to template forecastgeneration.html.
*/
    @RequestMapping(value = "forecastgeneration", method = RequestMethod.GET)
    public String forecastForm(){
        return "forecastgeneration";
    }

}

