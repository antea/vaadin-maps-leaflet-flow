// useful links:
// https://leafletjs.com/examples/overlays/
// https://www.geeksforgeeks.org/how-to-import-a-svg-file-in-javascript/
// https://www.npmjs.com/package/leaflet-distortableimage?activeTab=readme

let imageUrl = "frontend/leaflet/cb17-100-median-age.svg";

let img = L.distortableImageOverlay(imageUrl, {
    corners: [
        L.latLng(49.923578,-123.453116),
        L.latLng(49.923578, -57.619317),
        L.latLng(-0.308849, -123.453116),
        L.latLng(-0.308849,-57.619317),
    ],
}).addTo(this.map);


