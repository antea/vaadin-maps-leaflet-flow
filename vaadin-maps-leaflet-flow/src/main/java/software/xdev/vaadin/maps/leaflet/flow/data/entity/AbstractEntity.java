package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;


@MappedSuperclass
public abstract class AbstractEntity {
	
	@Id
	@GenericGenerator(name = "custom_gen",
		strategy = "software.xdev.vaadin.maps.leaflet.flow.data.generator.CustomGenerator")
	@GeneratedValue(generator = "custom_gen")
	private Long id;
	
	@Version
	private int version;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}
	
	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractEntity)) {
			return false; // null or other class
		}
		AbstractEntity other = (AbstractEntity) obj;
		
		if (getId() != null) {
			return getId().equals(other.getId());
		}
		return super.equals(other);
	}
}
