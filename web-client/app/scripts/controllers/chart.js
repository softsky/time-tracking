'use strict';

angular.module('webClientApp')
  .controller('ChartCtrl', function ($scope, $routeParams, $data) {
      var beginOfThePeriod, endOfThePeriod, workingPeriod;

      if($routeParams.week){
          beginOfThePeriod = moment({year: $routeParams.year}).add('week', $routeParams.week).startOf('week')
          endOfThePeriod = moment(beginOfThePeriod).add('week', 1).subtract('seconds', 1)
      } else {
          beginOfThePeriod = moment({year: $routeParams.year, month: $routeParams.month - 1})
          endOfThePeriod = moment(beginOfThePeriod).endOf('month')
          workingPeriod = 'Working period for ' + $routeParams + " of " + $routeParams.year
      }

      workingPeriod = 'Working period for ' + beginOfThePeriod.format('YYYY-MM-DD') + " - " + endOfThePeriod.format('YYYY-MM-DD')


      $scope.chartConfig = {
          options: {
              chart: {
                  type: 'line',
                  zoomType: 'x'
              }
          },
          series: [
          ],
          title: {
              text: workingPeriod
          },
          xAxis: {
              currentMin: 0, 
              currentMax: 31, 
              minRange: 1
          },
          yAxis: {
              plotLines: [{
                  value: 0,
                  width: 2,
                  color: 'silver'
              }]
          },
          loading: true,
          tooltip: {
              pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
              valueDecimals: 2
          },
      }

      $scope.username = ""

      $scope.db = $data.db;
      $scope.$watch("db.data", function(data){
          $scope.chartConfig.series.length = 0;
          var idx = 0;
          for(var user in data){
              $scope.chartConfig.series.push({ name: user, data: [], type: 'column'});
              data[user].forEach(function(interval){
                  $scope.chartConfig.series[idx].data.push(moment(interval.end).diff(moment(interval.start), 'minutes'))
              })
              idx++;
          }
          $scope.chartConfig.loading = false;
          $scope.data = {}
          _.each($scope.chartConfig.series, function(serie){
              $scope.data[serie.name] = _.reduce(serie.data, function(memo, num) { return memo + num}, 0)
          })

              var a = 0;
      }, false)

      $scope.$watch("username", function(newVal){
          $scope.db.list('web-client', 'intervals', 'byDateAndUsername', {
              descending: false,
	      startkey: [beginOfThePeriod.valueOf(), newVal || ''], 
	      endkey: [endOfThePeriod.valueOf(), newVal || '']  // adding 7 days
          })
      })



  });
