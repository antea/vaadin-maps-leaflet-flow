package software.xdev.vaadin.maps.leaflet.flow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import software.xdev.vaadin.maps.leaflet.flow.data.entity.Marker;


@Component
public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
