package cloud.molddata.parser.cv.controller;

import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.service.CVParserService;
import cloud.molddata.parser.cv.service.FileUploadService;
import cloud.molddata.parser.cv.service.UserService;
import cloud.molddata.parser.cv.service.UserUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserUploadService userUploadService;
    @Autowired
    private CVParserService cvParserService;

    @RequestMapping(value = {"/"})
    public String home(HttpServletRequest request, @ModelAttribute("userForm") UserSecurity userForm,
                       Model model) {
        String sessionID = request.getSession().getId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameAuth = auth.getName();

        if (!"anonymousUser".equals(nameAuth))
            userService.authorization(nameAuth, sessionID);

        return "redirect:/fileUploader";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserSecurity());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserSecurity userForm,
                               BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        String sessionID = request.getSession().getId();
        userService.save(userForm, sessionID);

        return "redirect:/fileUploader";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request
    ) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("loginBS");

        String sessionID = request.getSession().getId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameAuth = auth.getName();

        if ("anonymousUser".equals(nameAuth))
            userService.authorization(nameAuth, sessionID);

        return model;

    }

    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ModelAndView test1Page() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring TEST w/o SEC");
        model.addObject("message", "This is TEST page! without auth_SEC for ");
        model.setViewName("test1");
        return model;

    }

    @RequestMapping(value = "/db/{userName}", method = RequestMethod.GET)
    public String dbaPage(ModelMap model, Map<String, Object> map, @PathVariable String userName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();
        model.addAttribute("user", authName);
        model.addAttribute("userName", userName);
        model.addAttribute("title", "You are on personal page for ONE user!");
        model.addAttribute("message", "This page is for ROLE_ADMIN only!");
        map.put("userList", userUploadService.getListUsersByName(userName, "000"));
        map.put("cvList", cvParserService.getListCV());
        map.put("userListAuth", userUploadService.getListUsersAuth());

        return "dba";
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage(Map<String, Object> map) {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "You are on ADMIN PAGE!");
        model.addObject("message", "This page is for ROLE_ADMIN only!");
        model.setViewName("admin");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameAuth = auth.getName();
        System.out.println("MODELauth_NAME AUTH from /LIST=" + nameAuth);

        map.put("fileList", fileUploadService.getListFiles());
        map.put("userList", userUploadService.getListUsersAll());
        map.put("userListAuth", userUploadService.getListUsersAuth());

        return model;
    }

    // customize the error message
    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }

        return error;
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        // check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);

            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }
}