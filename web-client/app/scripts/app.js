'use strict';

angular.module('webClientApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.calendar',
//    'highcharts-ng',
    'CornerCouch'
]).config(function(){
    // some custom configuration
    Date.prototype.getWeek = function() {
        var onejan = new Date(this.getFullYear(), 0, 1);
        return Math.ceil((((this - onejan) / 86400000) + onejan.getDay() + 1) / 7);
    }

    String.prototype.toColor = function() {
        function hashCode(str) { // java String#hashCode
            var hash = 0;
            for (var i = 0; i < str.length; i++) {
                hash = str.charCodeAt(i) + ((hash << 5) - hash);
            }
            return hash;
        } 

        function intToRGB(i){
            return ((i >> 16) & 0xFF).toString(16) + 
                ((i >> 8) & 0xFF).toString(16) + 
                ((i >> 0) & 0xFF).toString(16);
        }

        return '#' + intToRGB(hashCode(this));
    }
    Number.prototype.timeToString = function(){
        var time = this;
        var hours, minutes, seconds;

        hours = Math.floor(time / (3600 * 1000));
        minutes = Math.floor((time - (hours * 3600 * 1000)) / (60 * 1000));
        seconds = Math.floor((time - (hours * 3600 * 1000) - minutes * (60 * 1000)) / 1000);
        
        return ((hours > 0) ? hours + " hours ": "") +
            ((minutes > 0) ? minutes + " minutes ":"") + 
            (seconds + " seconds");
    }
}).constant('DefaultUIConfig',  {
        eventMouseover: function(calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent">' + 
                (calEvent.end - calEvent.start).timeToString()
            '</div>';
            $("body").append(tooltip);
            $(this).mouseover(function(e) {
                $(this).css('z-index', 10000);
                $('.tooltipevent').fadeIn('500');
                $('.tooltipevent').fadeTo('10', 1.9);
            }).mousemove(function(e) {
                $('.tooltipevent').css('top', e.pageY + 10);
                $('.tooltipevent').css('left', e.pageX + 20);
            });
        },

        eventMouseout: function(calEvent, jsEvent) {
            $(this).css('z-index', 8);
            $('.tooltipevent').remove();
        },

        dayClick: function(date, allDay, jsEvent, view) {
            // if (allDay) {
            //     alert('Clicked on the entire day: ' + date);
            // }else{
            //     alert('Clicked on the slot: ' + date);
            // }

            // alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);

            // alert('Current view: ' + view.name);

            // // change the day's background color just for fun
            // $(this).css('background-color', 'red');
            window.location.hash = '/day/' + date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate()

        }    
        //eventDrop: $scope.alertOnDrop,
        //eventResize: $scope.alertOnResize,

    })
    .provider('$data', function (){
	function Provider(cornercouch){
	    this.db = cornercouch('http://vm81.softsky.com.ua:5984').getDB('softsky_timetracking');
	    this.db.method = "GET";
	}
	this.$get = function(cornercouch){
	    return new Provider(cornercouch)
	}
    })
    .config(function ($routeProvider) {
	$routeProvider
            .when('/', {
		templateUrl: 'views/main.html',
		controller: 'MainCtrl'
            })
            .when('/login', {
		templateUrl: 'views/login.html',
		controller: 'LoginCtrl'
            })
            .when('/calendar', {
		templateUrl: 'views/calendar.html',
		controller: 'CalendarCtrl'
            })
            .when('/day/:year/:month/:day', {
		templateUrl: 'views/day.html',
		controller: 'DayCtrl'
            })
            .when('/day/', {
		redirectTo: function(){
                    var date = new Date()
                    return ['/day', date.getFullYear(), (date.getMonth() + 1), date.getDate()].join('/');
		}
            })
            .when('/week/:year/:week', {
		templateUrl: 'views/week.html',
		controller: 'WeekCtrl'
            })
            .when('/week', {
		redirectTo: function(){
                    var date = new Date()
                    return ['/week', date.getFullYear(), date.getWeek()].join('/');
		}
            })
            .when('/month/:year/:month', {
		templateUrl: 'views/month.html',
		controller: 'MonthCtrl'
            })
            .when('/month', {
		redirectTo: function(){
                    var date = new Date()
                    return ['/month', date.getFullYear(), (date.getMonth() + 1)].join('/');
		}
            })
            .otherwise({
		redirectTo: '/'
            });
    })
