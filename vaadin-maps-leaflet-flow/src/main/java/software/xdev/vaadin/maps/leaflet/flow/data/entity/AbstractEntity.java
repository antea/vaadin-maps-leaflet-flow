package software.xdev.vaadin.maps.leaflet.flow.data.entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

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
	@Column(name = "id", nullable = false)
	private UUID id = UUID.randomUUID();
	
	@Version
	private int version;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
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
