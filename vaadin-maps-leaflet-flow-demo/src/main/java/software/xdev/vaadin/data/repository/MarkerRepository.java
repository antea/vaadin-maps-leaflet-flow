package software.xdev.vaadin.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.data.entity.Marker;



public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
