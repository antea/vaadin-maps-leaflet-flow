// useful links:
// https://leafletjs.com/examples/overlays/
// https://www.geeksforgeeks.org/how-to-import-a-svg-file-in-javascript/
// https://www.npmjs.com/package/leaflet-imageoverlay-rotated

let imageUrl = "frontend/leaflet/cb17-100-median-age.svg";
let errorOverlayUrl = 'https://cdn-icons-png.flaticon.com/512/110/110686.png';
let altText = 'US median age between the years 2000 and 2016';
//let latLngBounds = L.latLngBounds([[-0.308849, -123.453116], [49.923578, -57.619317]]);

let topleft    = L.latLng(44.497933,-9.706642),
    topright   = L.latLng(40.415020,-2.670899),
    bottomleft = L.latLng(38.101557,-12.213126);
let imageOverlay = L.imageOverlay.rotated(imageUrl, topleft, topright, bottomleft, {
    opacity: 0.8,
    errorOverlayUrl: errorOverlayUrl,
    alt: altText,
    interactive: true,
    attribution: '<a href="https://census.gov/newsroom/press-releases/2017/cb17-100.html">U.S. Census Bureau</a>'
}).addTo(this.map);
// reposition use this: imageOverlay.reposition(updatedTopLeft, updatedTopRight, updatedBottomLeft);

