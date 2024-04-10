document.addEventListener('DOMContentLoaded', function() {
    const galleryContainer = document.querySelector('.gallery-container');
    const imagePrefix = '../gallery/'; // Adjust based on your naming
    const imageCount = 4; // Total number of images
    const imageExtension = '.jpg'; // Adjust based on your file type

    for(let i = 1; i <= imageCount; i++) {
        const img = document.createElement('img');
        img.src = `${imagePrefix}${i}${imageExtension}`;
        img.alt = `Image ${i}`;
        img.classList.add('gallery-image');
        galleryContainer.appendChild(img);
    }
});
