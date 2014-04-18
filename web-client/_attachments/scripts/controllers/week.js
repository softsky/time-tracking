'use strict';

angular.module('webClientApp')
  .controller('WeekCtrl', function ($scope, $location, $routeParams, $log) {
      $scope.year = $routeParams.year;
      $scope.week = $routeParams.week;
  });
