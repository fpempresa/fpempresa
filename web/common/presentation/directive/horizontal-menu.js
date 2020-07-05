"use strict";

angular.module("common").directive('horizontalMenu', ['$rootScope',function($rootScope) {
        return {
            restrict: 'A',
            link: function($scope, element, attributes) {
                var horizontalMenu=new HorizontalMenu(element[0]);
                $rootScope[attributes.horizontalMenu+"HideMenuItems"]=function() {
                    horizontalMenu.hideMenuItems();
                }
                
            }
        };
    }]);


function HorizontalMenu(menu) {

    if (typeof (menu) === "string") {
        this.rootMenuElement = document.getElementById(menu);
    } else {
        this.rootMenuElement = menu;
    }
    var that=this;

    var itemsElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__items");

    itemsElements[0].addEventListener("animationend", function (event) {
        if (itemsElements[0].className.indexOf("c-horizontal-menu__items--movil-visible") >= 0) {
            itemsElements[0].className = " c-horizontal-menu__items c-horizontal-menu__items--movil-visible";
        } else {
            itemsElements[0].className = "c-horizontal-menu__items c-horizontal-menu__items--movil-hide";
        }
    }, false);

    var hamburgersElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__hamburger");
    hamburgersElements[0].addEventListener("click",function(event) {
        that.toggleVisibilityMenu();
    });
    
    this.toggleVisibilityMenu = function () {
        if (this.isVisibleMenu()) {
            this.hideMenuItems();
        } else {
            this.showMenuItems();
        }
    };

    this.hideMenuItems = function () {

        if (this.isVisibleMenu() === false) {
            return;
        }

        var itemsElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__items");

        var cssClass = "c-horizontal-menu__items  c-horizontal-menu__items--movil-hide";

        if (this.isVisibleHamburguer()) {
            cssClass = cssClass + " c-horizontal-menu__items--animation-rollin ";
        }

        itemsElements[0].className = cssClass;
        
        this.rootMenuElement.getElementsByClassName("c-horizontal-menu__hamburger")[0].classList.remove("c-horizontal-menu__hamburger--cerrar");
    };

    this.showMenuItems = function () {

        if (this.isVisibleMenu() === true) {
            return;
        }

        var itemsElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__items");

        var cssClass = "c-horizontal-menu__items  c-horizontal-menu__items--movil-visible";


        if (this.isVisibleHamburguer()) {
            cssClass = cssClass + " c-horizontal-menu__items--animation-rollout ";
        }

        itemsElements[0].className = cssClass;
        
        this.rootMenuElement.getElementsByClassName("c-horizontal-menu__hamburger")[0].classList.add("c-horizontal-menu__hamburger--cerrar");
    };

    this.isVisibleHamburguer = function () {
        var hamburgerElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__hamburger");
        var display = window.getComputedStyle(hamburgerElements[0], null).display;

        if (display !== "none") {
            return true;
        } else {
            return false;
        }
    };

    this.isVisibleMenu = function () {
        var itemsElements = this.rootMenuElement.getElementsByClassName("c-horizontal-menu__items");

        if (itemsElements[0].className.indexOf("c-horizontal-menu__items--movil-visible") >= 0) {
            return true;
        } else {
            return false;
        }
    };


    return this;
}