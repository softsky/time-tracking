'use strict';

angular.module('webClientApp')
  .controller('MonthCtrl', function ($scope, $location, $routeParams, $log) {
      $scope.year = $routeParams.year;
      $scope.month = $routeParams.month;
  });
