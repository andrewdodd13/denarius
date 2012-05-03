package org.ad13.denarius.controller;

import org.ad13.denarius.service.DenariusUserDetailsService;
import org.ad13.denarius.service.exception.UsernameInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class AuthenticationController {
    @Autowired
    private DenariusUserDetailsService userDetailsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showLogin(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        return "login/form";
    }

    @RequestMapping(value = "/denied")
    public ModelAndView accessDenied() {
        return new ModelAndView("login/denied");
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public String loginFailed() {
        final String message = "Login Failed!";
        return "redirect:/login?message=" + message;
    }

    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logoutSuccess() {
        final String message = "Logout Successful!";
        return "redirect:/login?message=" + message;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("r_username") String username, @RequestParam("r_password") String password,
            @RequestParam("r_password_confirm") String passwordConfirm, @RequestParam("r_email") String email,
            @RequestParam("r_fullname") String fullname) {
        
        if(!password.equals(passwordConfirm)) {
            return "redirect:/login?message=Passwords don't match.";
        }   
        try {
            userDetailsService.createUser(username, password, email, fullname, DenariusUserDetailsService.ROLE_USER);
        } catch (UsernameInUseException ex) {
            return "redirect:/login?message=Username in use.";
        }
        return "";
    }

    public DenariusUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(DenariusUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
