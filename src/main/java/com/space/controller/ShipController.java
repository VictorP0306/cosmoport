package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {

    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> findAll(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "planet", required = false) String planet,
                                              @RequestParam(value = "shipType", required = false) ShipType shipType,
                                              @RequestParam(value = "after", required = false) Long after,
                                              @RequestParam(value = "before", required = false) Long before,
                                              @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                              @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                              @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                              @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                              @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                              @RequestParam(value = "minRating", required = false) Double minRating,
                                              @RequestParam(value = "maxRating", required = false) Double maxRating,
                                              @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                              @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        Specification<Ship> specification = Specification.where(shipService.filterName(name)
                .and(shipService.filterPlanet(planet))
                .and(shipService.filterShipType(shipType))
                .and(shipService.filterProdDate(after, before))
                .and(shipService.filterUse(isUsed))
                .and(shipService.filterSpeed(minSpeed, maxSpeed))
                .and(shipService.filterCrewSize(minCrewSize, maxCrewSize))
                .and(shipService.filterRating(minRating, maxRating)));

        return new ResponseEntity<>(shipService.getList(specification, pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/ships/count")
    public ResponseEntity<Integer> getCount(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "planet", required = false) String planet,
                                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                                            @RequestParam(value = "after", required = false) Long after,
                                            @RequestParam(value = "before", required = false) Long before,
                                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                            @RequestParam(value = "minRating", required = false) Double minRating,
                                            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        Specification<Ship> specification = Specification.where(shipService.filterName(name)
                .and(shipService.filterPlanet(planet))
                .and(shipService.filterShipType(shipType))
                .and(shipService.filterProdDate(after, before))
                .and(shipService.filterUse(isUsed))
                .and(shipService.filterSpeed(minSpeed, maxSpeed))
                .and(shipService.filterCrewSize(minCrewSize, maxCrewSize))
                .and(shipService.filterRating(minRating, maxRating)));

        return new ResponseEntity<>(shipService.getCount(specification), HttpStatus.OK);
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable String id) {
        Ship responseShip;
        Long longId = shipService.checkId(id);
        responseShip = shipService.getShip(longId);
        return new ResponseEntity<>(responseShip, HttpStatus.OK);
    }

    @PostMapping("/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable String id, @RequestBody Ship ship) {
        Ship responseShip;
        Long longId = shipService.checkId(id);
        responseShip = this.shipService.updateShip(longId, ship);
        return new ResponseEntity<>(responseShip, HttpStatus.OK);
    }

    @PostMapping("/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        Ship responseShip;
        responseShip = shipService.createShip(ship);
        return new ResponseEntity<>(responseShip, HttpStatus.OK);
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity<?> deleteShip(@PathVariable String id) {
        Long longId = shipService.checkId(id);

        shipService.deleteShip(longId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
