package com.example.chat.restController;


import com.example.chat.model.Room;
import com.example.chat.service.RoomService;
import com.example.chat.util.Common;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Handles incoming requests to the Chat Room REST API, through which CRUD operations of the Room entity can be
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
@RequestMapping("api/v1/rooms") //TODO get version for path mapping from configuration)
public class RoomRestController {

    @Autowired
    RoomService roomService;


    // CRUD: GET /rooms
    @GetMapping({"", "/"})
    public ResponseEntity<Iterable<Room>> index(HttpServletRequest request) {

        Iterable<Room> rooms = roomService.findAll();

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "GET");
        headers.add("endpoint", "api/v1/rooms");
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Iterable<Room>> response;
        if (rooms != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(rooms);
        } else {
            headers.add("status", "fail");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;

    }

    //CRUD: GET /rooms/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Room> show(HttpServletRequest request, @PathVariable String id) {

        // Accepts id and uuid as room identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        Optional<Room> room = (idValue != null)
                            ? roomService.findById(idValue)
                            : roomService.findByUuid(id);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "GET");
        headers.add("endpoint", "api/v1/rooms/show/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Room> response;
        if (room.isPresent()) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(room.get());
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;
    }

    //CRUD: POST /rooms
    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> create(HttpServletRequest request,  @RequestBody Room room) {

        Room roomCreated = roomService.create(room);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "POST");
        headers.add("endpoint", "api/v1/rooms");
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Room> response;
        if (roomCreated != null) {
            // TODO: response with create(URI location) code 201, investigate accepted(), investigate how Spring Boot
            //       can autogenerate this URI Resource
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(roomCreated);
        } else {
            // Todo: manage failure codes 404 for “Not Found”, or a 409 conflict error if the item already exists.
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }

        return response;
    }

    //CRUD: PATCH /rooms/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Room> update(HttpServletRequest request, @PathVariable String id, @RequestBody Room room) {

        // Accepts id and uuid as room identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        Room roomUpdated = (idValue != null)
                         ? roomService.update(idValue, room)
                         : roomService.update(id, room);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "PATCH");
        headers.add("endpoint", "api/v1/rooms/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Room> response;
        if (roomUpdated != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(roomUpdated);
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(roomUpdated);
        }

        return response;
    }

    //CRUD: DELETE /rooms/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Room> destroy(HttpServletRequest request, @PathVariable String id) {

        // Accepts id and uuid as room identification:
        //   Checks for Long id identification. If it is not a Long then assumes
        //   that looking for by String uuid identification
        Long idValue = Common.tryParseLong(id);
        Room room = (idValue != null)
                ? roomService.delete(idValue)
                : roomService.delete(id);

        // TODO automate this and add more info headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("verb", "DELETE");
        headers.add("endpoint", "api/v1/rooms/" + id);
        headers.add("timestamp", Instant.now().toString());

        ResponseEntity<Room> response;
        if (room != null) {
            headers.add("status", "success");
            response = ResponseEntity.accepted().headers(headers).body(room);
        } else {
            headers.add("status", "fail");
            response =ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(room);
        }

        return response;
    }


}
