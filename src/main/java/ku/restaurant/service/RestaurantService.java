package ku.restaurant.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    private RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Page<Restaurant> getRestaurantsPage(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public Restaurant create(RestaurantRequest request) {
        if (repository.existsByName(request.getName()))
            throw new EntityExistsException("Restaurant name already exists");

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setRating(request.getRating());
        restaurant.setLocation(request.getLocation());

        restaurant.setCreatedAt(Instant.now());
        Restaurant record = repository.save(restaurant);
        return record;
    }

    public Restaurant getRestaurantById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Restaurant not found"));
    }

    public Restaurant update(Restaurant requestBody) {
        UUID id = requestBody.getId();
        Restaurant record = repository.findById(id).get();
        record.setName(requestBody.getName());
        record.setRating(requestBody.getRating());
        record.setLocation(requestBody.getLocation());

        record = repository.save(record);
        return record;

    }

    public Restaurant delete(UUID id) {
        Restaurant record = repository.findById(id).get();
        repository.delete(record);
        return record;
    }

    public Restaurant getRestaurantByName(String name) {
        return repository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Restaurant not found"));
    }

    public List<Restaurant> getRestaurantByLocation(String location) {
        return repository.findByLocation(location);
    }
}
