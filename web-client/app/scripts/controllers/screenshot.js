'use strict';

angular.module('webClientApp')
    .controller('ScreenshotCtrl', function ($scope, $log, $routeParams, $data) {
        $scope.year = $routeParams.year;
        $scope.month = $routeParams.month;
        $scope.day = $routeParams.day;

        var monthBeginDate = new Date($scope.year, $scope.month?$scope.month - 1:0, $scope.day?$scope.day:1)
        // FIXME: sometime it could take the first day(s) of next month
        var monthEndDate = new Date(new Date($scope.year, $scope.month?$scope.month - 1:0, $scope.day?$scope.day:31).getTime() + 24 * 3600 * 1000);

        $scope.server = $data.server;
        $scope.server.method = "GET"

        $scope.showInfo = true;

        $scope.server.getInfo();
        $scope.server.getDatabases();
        $scope.server.getUUIDs(3);
        $scope.server.getUserDoc();
        $scope.db = $data.db;
        $scope.db.method = "GET"
        $scope.db.getInfo();

        $scope.db.query("web-client", "byDateAndUsername", { 
            include_docs: true, 
            descending: false, 
            limit: 20, 
            startkey: [monthBeginDate.getTime(), "skekes" ],
            endkey: [monthEndDate.getTime(), "skekes" ]
        });


        $scope.formatDate = function(row){
            return new Date(parseInt(row.doc._id));
        }

        $scope.getImagePath = function(row){
            return "../../" + row.doc._id + "/" + Object.keys(row.doc._attachments)[0];
        }

        $scope.nextClick = function() { $scope.db.queryNext(); delete $scope.detail }
        $scope.prevClick = function() { $scope.db.queryPrev(); delete $scope.detail }

        $scope.logout = function(){
            $scope.server.logout()
            $scope.loggedIn = false;
        }
    });
