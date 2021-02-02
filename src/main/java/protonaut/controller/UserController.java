package protonaut.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import protonaut.model.Page;
import protonaut.model.User;
import protonaut.service.UserService;

import java.util.List;

@Controller("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get("/greet")
    public String greetUser(@QueryValue String userName) {
        return userService.greet(userName);
    }

    @Status(HttpStatus.CREATED)
    @Post
    public User create(@Body User user) {
        return userService.create(user);
    }

    @Get("/{userId}")
    public HttpResponse<User> getUserById(@PathVariable Long userId) {
        return HttpResponse.ok(userService.findById(userId));
    }

    @Get("{?page*}")
    public HttpResponse<List<User>> getUsers(Page page) {
        return HttpResponse.ok(userService.findAll(page));
    }
}
