'use strict';

angular.module('webClientApp')
    .config(function($dataProvider){
	// doing some config on $dataProvider
    })
    .controller('WeekCtrl', function ($scope, $location, $routeParams, $log, $data, DefaultUIConfig) {
	$log.info($routeParams);
	
	$scope.year = $routeParams.year;
	$scope.week = $routeParams.week;
	var weekBeginDate = new Date(new Date($scope.year, 0, 1).getTime() + (($scope.week - 1) * 7 * 24 * 3600 * 1000))

	$scope.username = "";

        $scope.events = [];

        $scope.eventSources = [ $scope.events ]


	$scope.db = $data.db;
	$scope.$watch("db.data", function(data){
            $scope.events.length = 0;
            for(var user in data){
                data[user].forEach(function(interval){
                    $scope.events.push(angular.extend(interval, {title: user, color: user.toColor(), allDay: false}))
                })
            }
	}, true)
	
	$scope.$watch("username", function(newVal){
	    $scope.db.list("web-client", "intervals", "byDateAndUsername", {
		descending: false, 
		startkey: [(weekBeginDate.getTime()), $scope.username], 
		endkey: [(weekBeginDate.getTime() + (7 * 24 * 3600 * 1000)), $scope.username]  // adding 7 days
	    })
	})

        $scope.uiConfig = {
            calendar:{
                height: '800',
                editable: false,
                year: weekBeginDate.getFullYear(), 
                month: weekBeginDate.getMonth(),
                date: weekBeginDate.getDate(),
                header:{
                    left: '',
                    center: 'title',
                    right: ''
                },
                allDaySlot: false,
                defaultView: 'agendaWeek',
                dayClick: $scope.alertEventOnClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
            }
        };

        $scope.uiConfig.calendar = angular.extend(angular.copy(DefaultUIConfig), $scope.uiConfig.calendar); // prevent DefaultUIConfig from modification
    })

