package software.xdev.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;


// @SpringBootApplication
// temporarily disable dataSourceAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EntityScan("software.xdev.vaadin.maps.leaflet.flow.data.entity")
@EnableJpaRepositories("software.xdev.vaadin.maps.leaflet.flow.data.repository")
@EnableVaadin
@Push

public class Application extends SpringBootServletInitializer implements AppShellConfigurator
{
	public static void main(final String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}

