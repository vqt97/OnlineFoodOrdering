package org.programing.microservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private String title;

    @Column(length = 1000)
    private List<String> images;
    private String description;
    private Long id;
}
