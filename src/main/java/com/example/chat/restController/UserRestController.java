package com.example.chat.restController;

import com.example.chat.model.User;
import com.example.chat.service.UserService;
import com.example.chat.util.Common;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

/**
 * Handles incoming requests to the Chat User REST API, through which CRUD operations of the User entity can be
 * performed.
 *
 * <p>
 * NOTE: About trailing slash on mappings. The trailing slash matching configuration option has been deprecated and its
 * default value set to false. This means that previously, the following controller would match both "GET
 * /some/greeting" and "GET /some/greeting/". As of this Spring Framework change, "GET /some/greeting/" doesn’t match
 * anymore by default and will result in an HTTP 404 error.
 * <p>
 * Developers should instead configure explicit redirects/rewrites through a proxy, a Servlet/web filter, or even
 * declare the additional route explicitly on the controller handler (like @GetMapping("/some/greeting",
 * "/some/greeting/") for more targeted cases.
 *
 * @see  https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#spring-mvc-and-webflux-url-matching-changes
 * @see  https://github.com/spring-projects/spring-framework/issues/28552
 * *
 */
@RestController
@RequestMapping("api/v1/users") //TODO get version for path mapping from configuration)
public class UserRestController {

    @Autowired
    UserService userService;

    // CRUD: GET /users
    @GetMapping({"", "/"})
    public ResponseEntity<Iterable<User>> index(HttpServletRequest request) {

        Iterable<User> users = userService.findAll();

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "GET");
        headers.add("endpoint", "api/v1/users");
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Iterable<User>> response;
        if (users != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(users);
        } else {
            headers.add("status", "fail");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;

    }

    //CRUD: GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> show(HttpServletRequest request, @PathVariable String id) {

        // Accepts id and uuid as user identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        Optional<User> user = (idValue != null)
                ? userService.findById(idValue)
                : userService.findByUuid(id);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "GET");
        headers.add("endpoint", "api/v1/users/show/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<User> response;
        if (user.isPresent()) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(user.get());
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;
    }

    //CRUD: POST /users
    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(HttpServletRequest request,  @RequestBody User user) {

        User userCreated = userService.create(user);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "POST");
        headers.add("endpoint", "api/v1/users");
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<User> response;
        if (userCreated != null) {
            // TODO: response with create(URI location) code 201, investigate accepted(), investigate how Spring Boot
            //       can autogenerate this URI Resource
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(userCreated);
        } else {
            // Todo: manage failure codes 404 for “Not Found”, or a 409 conflict error if the item already exists.
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;
    }

    //CRUD: PATCH /users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<User> update(HttpServletRequest request, @PathVariable String id, @RequestBody User user) {

        // Accepts id and uuid as user identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        User userUpdated = (idValue != null)
                ? userService.update(idValue, user)
                : userService.update(id, user);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "PATCH");
        headers.add("endpoint", "api/v1/users/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<User> response;
        if (userUpdated != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(userUpdated);
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(userUpdated);
        }

        return response;
    }

    //CRUD: DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<User> destroy(HttpServletRequest request, @PathVariable String id) {

        // Accepts id and uuid as user identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        User user = (idValue != null)
                ? userService.delete(idValue)
                : userService.delete(id);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "DELETE");
        headers.add("endpoint", "api/v1/users/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<User> response;
        if (user != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(user);
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(user);
        }

        return response;
    }




}
