package software.xdev.vaadin.maps.leaflet.flow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.maps.leaflet.flow.data.entity.Marker;


public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
