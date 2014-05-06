'use strict';

angular.module('webClientApp')
  .controller('LoginCtrl', function ($scope, $location, cornercouch) {
      $scope.server = cornercouch("http://vm81.softsky.com.ua:5984");
      $scope.server.method = "GET"
      $scope.loginUser = "archer";
      $scope.loginPass = "XtujNj12";
      
      $scope.submitLogin = function() {
          $scope.server.login($scope.loginUser, $scope.loginPass)
              .success( function() {
                  $scope.loggedIn = true;
                  // $scope.loginPass = $scope.loginUser = '';
                  $location.path("/")
              });
      };

  });
