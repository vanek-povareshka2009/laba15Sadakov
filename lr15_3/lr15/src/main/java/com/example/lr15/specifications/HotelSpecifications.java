package com.example.lr15.specifications;

import com.example.lr15.entities.Hotel;
import org.springframework.data.jpa.domain.Specification;


public class HotelSpecifications {
    public static Specification<Hotel> hasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Hotel> hasHotel(String hotel) {
        return ((root, query, criteriaBuilder) -> {
            if (hotel == null || hotel.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("hotel")), "%" + hotel.toLowerCase() + "%");
        });
    }
    public static Specification<Hotel> hasPriceFrom(Double priceFrom) {
        return (((root, query, criteriaBuilder) -> {
            if(priceFrom == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom);
        }));
    }
    public static Specification<Hotel> hasPriceTo(Double priceTo) {
        return (((root, query, criteriaBuilder) -> {
            if(priceTo == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo);
        }));
    }

    public static Specification<Hotel> hasStatus(Integer status) {
        return (root, query, criteriaBuilder) -> status == null ? null :
                criteriaBuilder.equal(root.get("status"), status);
    }
    public static Specification<Hotel> hasType(String quantity) {
        return ((root, query, criteriaBuilder) -> {
            if (quantity == null || quantity.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("quantity"), quantity);
        });
    }



//    public static Specification<Hotel> hasType(String type) {
//        return (((root, query, criteriaBuilder) -> {
//            if(type == null || type.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.like((root.get("quantity")), "%" + type + "%");
//        }));
//    }


}
