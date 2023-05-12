package software.xdev.vaadin.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.xdev.vaadin.data.entity.Rectangle;


public interface RectangleRepository extends JpaRepository<Rectangle, Long> {

}
