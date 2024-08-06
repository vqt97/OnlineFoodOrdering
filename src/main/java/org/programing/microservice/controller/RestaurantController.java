package org.programing.microservice.controller;

import lombok.RequiredArgsConstructor;
import org.programing.microservice.dto.RestaurantDto;
import org.programing.microservice.model.Restaurant;
import org.programing.microservice.model.User;
import org.programing.microservice.service.RestaurantService;
import org.programing.microservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @RequestParam String keyword) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
                                                               @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(@RequestHeader("Authorization") String jwt,
                                                     @PathVariable Long id,
                                                    @RequestBody RestaurantDto restaurantDto
                                                     ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        RestaurantDto restaurant  = restaurantService.addToFavorites(id, user, restaurantDto);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
