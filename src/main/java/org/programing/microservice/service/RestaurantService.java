package org.programing.microservice.service;

import org.programing.microservice.dto.RestaurantDto;
import org.programing.microservice.request.CreateRestaurantRequest;
import org.programing.microservice.model.Restaurant;
import org.programing.microservice.model.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user, RestaurantDto restaurantDto) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
