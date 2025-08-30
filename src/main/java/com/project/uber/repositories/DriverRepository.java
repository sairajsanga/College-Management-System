package com.project.uber.repositories;

import com.project.uber.entities.Driver;
import com.project.uber.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT d.*, " +
            "ST_Distance(d.current_location, ST_GeomFromText(?1, 4326)) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true " +
            "AND ST_Distance(d.current_location,ST_GeomFromText(?1, 4326)) < 10000 " +
            "ORDER BY distance " +
            "LIMIT 10",
            nativeQuery = true)
    List<Driver> findTenNearestDrivers(String pickUpLocationWkt);

    @Query(value = "SELECT d.*, " +
            "ST_Distance(d.current_location,ST_GeomFromText(?1, 4326)) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true " +
            "AND ST_Distance(d.current_location, ST_GeomFromText(?1, 4326)) < 10000 " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Driver> findTenNearbyTopRatedDrivers(String pickUpLocationWkt);

    Optional<Driver> findByUser(User user);
}
