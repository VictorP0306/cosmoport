package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ShipService {
    Page<Ship> getList(Specification<Ship> specification, Pageable pageable);
    Integer getCount(Specification<Ship> specification);
    Long checkId(String id);
    Ship getShip(Long id);
    Ship updateShip(Long id, Ship ship);
    Ship createShip(Ship ship);
    void deleteShip(Long id);

    Specification<Ship> filterName(String name);
    Specification<Ship> filterPlanet(String planet);
    Specification<Ship> filterShipType(ShipType shipType);
    Specification<Ship> filterProdDate(Long after, Long before);
    Specification<Ship> filterUse(Boolean isUsed);
    Specification<Ship> filterSpeed(Double minSpeed, Double maxSpeed);
    Specification<Ship> filterCrewSize(Integer minCrewSize, Integer maxCrewSize);
    Specification<Ship> filterRating(Double minRating, Double maxRating);
}
