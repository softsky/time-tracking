'use strict';

angular.module('webClientApp')
  .controller('MonthCtrl', function ($scope, $location, $routeParams, $log, $data, DefaultUIConfig) {
      $scope.year = $routeParams.year;
      $scope.month = $routeParams.month;
      var monthBeginDate = new Date($scope.year, $scope.month - 1, 1)
      var monthEndDate = new Date(new Date($scope.year, $scope.month, 1) - (24 * 3600 * 1000))

      $scope.username = "skekes";

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
	      startkey: [monthBeginDate.getTime(), $scope.username], 
	      endkey: [monthEndDate.getTime(), $scope.username]  // adding 7 days
	  })
      })

      $scope.uiConfig = {
          calendar:{
              height: '800',
              editable: false,
              year: $scope.year, 
              month: $scope.month - 1, 
              date: $scope.day,
              header:{
                  center: 'title',
                  right: ''
              },
              allDaySlot: false,
              defaultView: 'month',
          }
      };

      $scope.uiConfig.calendar = angular.extend(DefaultUIConfig, $scope.uiConfig.calendar);

  });
