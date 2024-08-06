package org.programing.microservice.request;

import lombok.Builder;
import lombok.Data;
import org.programing.microservice.model.Address;
import org.programing.microservice.model.ContactInfomation;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfomation contactInfomation;
    private String openingHours;
    private List<String> images;
    private LocalDateTime register;
}
