/*
 * Copyright © 2019 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.vaadin.maps.leaflet.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import software.xdev.vaadin.maps.leaflet.layer.LLayer;

@DomEvent("pm:create")
public class GeomanCreateEvent extends ComponentEvent<Component>
{
	private final LLayer<?> layer;
	
	private final String shape;
	
	/**
	 * Creates a new event using the given source and indicator whether the event originated from the client side or
	 * the
	 * server side.
	 *
	 * @param source     the source component
	 * @param fromClient <code>true</code> if the event originated from the client
	 *                   side, <code>false</code> otherwise
	 */
	public GeomanCreateEvent(
		final Component source, final boolean fromClient,
		@EventData("event.shape") String shape,
		@EventData("event.layer") LLayer<?> layer)
	{
		super(source, fromClient);
		this.shape = shape;
		this.layer = layer;
	}
	
	public String getShape()
	{
		return shape;
	}
	
	public LLayer<?> getLayer()
	{
		return layer;
	}
	
}
