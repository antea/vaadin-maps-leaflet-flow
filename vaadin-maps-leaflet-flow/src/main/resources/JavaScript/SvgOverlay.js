
let imageUrl = "frontend/leaflet/cb17-100-median-age.svg";
let errorOverlayUrl = 'https://cdn-icons-png.flaticon.com/512/110/110686.png';
let altText = 'US median age between the years 2000 and 2016';
let latLngBounds = L.latLngBounds([[-0.308849, -123.453116], [49.923578, -57.619317]]);

let imageOverlay = L.imageOverlay(imageUrl, latLngBounds, {
    opacity: 0.8,
    errorOverlayUrl: errorOverlayUrl,
    alt: altText,
    interactive: true
}).addTo(this.map);

