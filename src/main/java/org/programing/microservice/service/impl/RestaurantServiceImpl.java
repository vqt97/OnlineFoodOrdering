package org.programing.microservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.programing.microservice.dto.RestaurantDto;
import org.programing.microservice.model.Address;
import org.programing.microservice.repositories.AddressRepository;
import org.programing.microservice.repositories.UserRepository;
import org.programing.microservice.request.CreateRestaurantRequest;
import org.programing.microservice.model.Restaurant;
import org.programing.microservice.model.User;
import org.programing.microservice.repositories.RestaurantRepository;
import org.programing.microservice.service.RestaurantService;
import org.programing.microservice.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {
        return restaurantRepository.save(Restaurant.builder()
                .address(addressRepository.save(request.getAddress()))
                .contactInfomation(request.getContactInfomation())
                .cuisineType(request.getCuisineType())
                .description(request.getDescription())
                .images(request.getImages())
                .name(request.getName())
                .openingHours(request.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build());
    }

    @Override
    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setCuisineType(updateRestaurant.getCuisineType());
        restaurant.setDescription(updateRestaurant.getDescription());
        restaurant.setName(updateRestaurant.getName());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> optional = restaurantRepository.findById(id);

        if (optional.isEmpty())
            throw new Exception("Restaurant not found by id");

        return optional.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant==null)
            throw new Exception("Restaurant not found with Owner");

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user, RestaurantDto restaurantDto) throws Exception {
        restaurantDto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        if (isFavorited)
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        else favorites.add(restaurantDto);

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);
        return restaurant;
    }
}
