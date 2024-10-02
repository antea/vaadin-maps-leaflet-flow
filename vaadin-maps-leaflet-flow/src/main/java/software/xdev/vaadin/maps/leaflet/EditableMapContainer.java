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
package software.xdev.vaadin.maps.leaflet;

import java.util.function.Consumer;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEventListener;

import software.xdev.vaadin.maps.leaflet.controls.LGeomanOptions;
import software.xdev.vaadin.maps.leaflet.controls.LGeomanUtils;
import software.xdev.vaadin.maps.leaflet.events.GeomanCreateEvent;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.map.LMapOptions;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;


public class EditableMapContainer extends MapContainer
{
	public EditableMapContainer(final LComponentManagementRegistry reg)
	{
		super(reg);
	}
	
	public EditableMapContainer(
		final LComponentManagementRegistry reg,
		final LMapOptions options)
	{
		super(reg, options);
	}
	
	public EditableMapContainer(
		final LComponentManagementRegistry reg,
		final Consumer<LMap> afterInitialResize)
	{
		super(reg, afterInitialResize);
	}
	
	public EditableMapContainer(
		final LComponentManagementRegistry reg,
		final LMapOptions options,
		final Consumer<LMap> afterInitialResize)
	{
		super(reg, options, afterInitialResize);
	}
	
	public void addToolbar(LGeomanOptions geomanOptions)
	{
		LGeomanUtils.addToolbar(getlMap(), getlMap().componentRegistry(), geomanOptions);
		getlMap().on(
		"pm:create",
		"e => document.getElementById('" + this.ensureId() + "').$server.pmCreated(e.shape, e.layer.constructor)");
		
	}
	
	public void addCreateListener(ComponentEventListener<GeomanCreateEvent> listener)
	{
		this.addListener(GeomanCreateEvent.class, listener);
	}
	
	@ClientCallable
	public void pmCreated(String shape, String layer)
	{
		fireEvent(new GeomanCreateEvent(this, true, shape, null));
	}
	
	/*
	
	@ClientCallable
	public void pmCreated(String shape, String layer) {
		Notification.show(  "ciao " + shape + layer);
	}
	 */
	
	/*
		mapContainer.addCreateListener((ComponentEventListener<GeomanCreateEvent>)event -> {
				Notification.show(" hello world");
				Notification.show(" hello world");
			}
		);
		mapContainer.getContent().getElement().addEventListener("pm:create", event -> {
			Notification.show(" hello world222");
		});
	 */
}
