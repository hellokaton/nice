'use strict';

// modal.js

Array.prototype.each = function (fn) {
    for (var i = 0; i < this.length; i++) {
        fn(this[i], i, this);
    }
};

var oTriggers = [],
    cTriggers = [],
    overlayArr = [];
var dataOpenModal = document.querySelectorAll('[data-open="modal"]');
var dataCloseModal = document.querySelectorAll('[data-close="modal"]');
var overlays = document.querySelectorAll('.modal-overlay');

for (var x = 0; x < dataOpenModal.length; x++) {
    oTriggers.push(dataOpenModal[x]);
}
for (var _x = 0; _x < dataCloseModal.length; _x++) {
    cTriggers.push(dataCloseModal[_x]);
}
for (var _x2 = 0; _x2 < overlays.length; _x2++) {
    overlayArr.push(overlays[_x2]);
}

oTriggers.each(function (el) {
    el.addEventListener('click', function (e) {
        e.preventDefault();
        var modalContainer = el.getAttribute('href');
        modalContainer = document.querySelector('.modal' + modalContainer);
        var modalClass = modalContainer.getAttribute('class');
        modalContainer.setAttribute('class', modalClass + ' active');
    });
});

cTriggers.each(function (el) {
    el.addEventListener('click', function (e) {
        e.preventDefault();
        // console.log('close click')
        var modalContainer = el.getAttribute('href');
        modalContainer = document.querySelector('.modal' + modalContainer);
        modalContainer.setAttribute('class', 'modal');
    });
});

overlayArr.each(function (el) {
    el.addEventListener('click', function (e) {
        e.preventDefault();
        var activeModal = document.querySelector('.modal[id].active');
        activeModal.setAttribute('class', '' + activeModal.getAttribute('class').replace('active', ''));
    });
});