package com.example.lr15.services;

import com.example.lr15.entities.Hotel;
import com.example.lr15.specifications.HotelSpecifications;
import com.example.lr15.repositories.HotelRepository; // Assuming this is the correct repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository repository;

    @Autowired
    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public Hotel getById(Integer id) {
        Hotel hotel = repository.findById(id).orElse(null);
        if (hotel == null) throw new UsernameNotFoundException("");
        return hotel;
    }

    public Page<Hotel> getAllHotels(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Hotel> getAllHotel(String name, Double priceFrom, String quantity, String hotel, Double priceTo, Integer status, Pageable pageable) {
        Specification<Hotel> specification = Specification
                .where(HotelSpecifications.hasName(name))
                .and(HotelSpecifications.hasHotel(hotel))
                .and(HotelSpecifications.hasPriceFrom(priceFrom))
                .and(HotelSpecifications.hasPriceTo(priceTo))
                .and(HotelSpecifications.hasStatus(status))
                .and(HotelSpecifications.hasType(quantity));
        return repository.findAll(specification, pageable);
    }




    public List<Hotel> getTopHotels() {
        List<Hotel> hotels = repository.findAll(); // Используйте ваш инжектированный репозиторий
        hotels.sort(Comparator.comparing(Hotel::getPrice).reversed()); // Сортировка в обратном порядке
        return hotels.stream().limit(3).collect(Collectors.toList());
    }


    public void add(Hotel hotel) {
        hotel.setViews(0);
        repository.save(hotel);
    }

    public void delete(Hotel hotel) {
        repository.delete(hotel);
    }

    public void update(Hotel existing, Hotel updated) {
        if (updated.getName() != null && !updated.getName().isBlank()) {
            existing.setName(updated.getName());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getPrice() != null) {
            existing.setPrice(updated.getPrice());
        }
        if (updated.getQuantity() != null) {
            existing.setQuantity(updated.getQuantity());
        }
        repository.save(existing);
    }


    public void incViews(Hotel flower) {
        flower.setViews(flower.getViews() + 1);
        repository.save(flower);
    }


}
