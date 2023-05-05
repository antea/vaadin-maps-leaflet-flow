// CLIENT_MAP = "this.map";
// CLIENT_MARKER_CLUSTER_GROUP = "this.markerClusterGroup";


this.map.addLayer(editableFeatureGroup);
var drawControl = new L.Control.Draw({
	edit: {
		featureGroup: editableFeatureGroup
	},
	draw: {
		rectangle: { showArea: false },
	}
});
this.map.addControl(drawControl);
let addMarkerToClusterGroup = (layer) => this.markerClusterGroup.addLayer(layer); // I did this because for some reason this.markerClusterGroup is undefined in the callback bellow
let removeMarkerFromClusterGroup = (layer) => this.markerClusterGroup.removeLayer(layer); // I did this because for some reason this.markerClusterGroup is undefined in the callback bellow
let clearMarkerFromClusterGroup = () => this.markerClusterGroup.clearLayers(); // I did this because for some reason this.markerClusterGroup is undefined in the callback bellow
this.map.on(L.Draw.Event.CREATED, function (e) {
	var type = e.layerType,
		layer = e.layer;
	editableFeatureGroup.addLayer(layer);
	addMarkerToClusterGroup(layer); // important: add this after adding to editableFeatureGroup

	if (type === 'marker') {
	} else {

	}
});
this.map.on(L.Draw.Event.DELETED, function (e) {
	e.layers.eachLayer(layer => {
		removeMarkerFromClusterGroup(layer);
	});
});