const CLIENT_MAP = "this.map";
const CLIENT_MARKER_CLUSTER_GROUP = "this.markerClusterGroup";


CLIENT_MAP.addLayer(editableFeatureGroup);
var drawControl = new L.Control.Draw({
	edit: {
		featureGroup: editableFeatureGroup
	},
	draw: {
		rectangle: { showArea: false },
	}
});
CLIENT_MAP.addControl(drawControl);
let addMarkerToClusterGroup = (layer) => CLIENT_MARKER_CLUSTER_GROUP.addLayer(layer); // I did this because for some reason CLIENT_MARKER_CLUSTER_GROUP is undefined in the callback bellow
let removeMarkerFromClusterGroup = (layer) => CLIENT_MARKER_CLUSTER_GROUP.removeLayer(layer); // I did this because for some reason CLIENT_MARKER_CLUSTER_GROUP is undefined in the callback bellow
let clearMarkerFromClusterGroup = () => CLIENT_MARKER_CLUSTER_GROUP.clearLayers(); // I did this because for some reason CLIENT_MARKER_CLUSTER_GROUP is undefined in the callback bellow
CLIENT_MAP.on(L.Draw.Event.CREATED, function (e) {
	var type = e.layerType,
		layer = e.layer;
	editableFeatureGroup.addLayer(layer); \n
	addMarkerToClusterGroup(layer); \n // important: add this after adding to editableFeatureGroup

	if (type === 'marker') {
	} else {

	}
});
CLIENT_MAP.on(L.Draw.Event.DELETED, function (e) {
	e.layers.eachLayer(layer => {
		removeMarkerFromClusterGroup(layer);
	});
});