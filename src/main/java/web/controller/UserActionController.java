package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.Set;

@Controller
@RequestMapping(path = "admin/users")
public class UserActionController {

    private final UserService userService;

    @Autowired
    public UserActionController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/users_list";
    }
    
    @GetMapping("{id}")
    public String getSingleUser(@PathVariable("id") Long id,
                                Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/user_single";
    }

    @GetMapping(path = "new")
    public String getNewUser(@ModelAttribute("user") User user) {
        return "users/create";
    }

    @PostMapping
    public String postNewUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(path = "{id}/edit")
    public String getEditUser(@PathVariable("id") Long id,
                              Model model) {
        final Set<String> userRoles = userService.getRolesFromUser(id);
        model.addAttribute("user_roles", userRoles);
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping(path = "{id}")
    public String patchUser(@PathVariable("id") Long userId,
                            @ModelAttribute("user") User user) {
        userService.updateUser(userId, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(path = "{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}
