package software.xdev.vaadin.maps.leaflet.flow.data.generator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.type.Type;


public class CustomGenerator implements IdentifierGenerator {
	
	private IdentifierGenerator defaultGenerator;
	
	@Override
	public Serializable generate(
		SharedSessionContractImplementor session,
		Object object)
		throws HibernateException
	{
		Long idValue = (Long)defaultGenerator.generate(session, object);
		return idValue;
	}
}