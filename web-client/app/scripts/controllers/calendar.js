'use strict';

angular.module('webClientApp')
    .controller('CalendarCtrl', function ($scope) {
        /* config object */
        $scope.uiConfig = {
            calendar:{
                height: 450,
                editable: true,
                header:{
                    left: 'month basicWeek basicDay agendaWeek agendaDay',
                    center: 'title',
                    right: 'today prev,next'
                },
                dayClick: $scope.alertEventOnClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
                eventRender: function(event, element) {
                    // element.qtip({
                    //     content: event.description
                    // });
                },
                eventAfterRender: function(event, element, view){
                    var hoursWorked = (event.end - event.start)/1000/3600;
                    element.css("width", (hoursWorked/24) * 161)
                    element.attr("title", "Moveing")
                }

            }
        };

        $scope.events = [
            {
                start  : new Date('2014-04-02 00:30:00'),
                end    : new Date('2014-04-02 11:20:00'),
                className: ['newtask'],
                url: '#/login',
                color: '#660022',
                allDay : false // will make the time show

            },
            {
                start  : new Date('2014-04-02 10:30:00'),
                end    : new Date('2014-04-02 15:20:00'),
                className: ['newtask'],
                url: '#/login',
                color: '#22dd22',
                allDay : false // will make the time show

            },
            {
                start  : new Date('2014-04-05 10:30:00'),
                end    : new Date('2014-04-05 11:20:00'),
                allDay : false // will make the time show
            },
            {
                title  : 'event3',
                start  : new Date('2014-04-09 12:30:00'),
                end    : new Date('2014-04-09 19:30.00'),
                allDay : false // will make the time show
            },
        ]


        $scope.eventSources = [
            $scope.events
        ]

        $scope.chart = {
            //This is not a highcharts object. It just looks a little like one!
            options: {
                //This is the Main Highcharts chart config. Any Highchart options are valid here.
                //will be ovverriden by values specified below.
                chart: {
                    type: 'bar'
                },
                tooltip: {
                    style: {
                        padding: 10,
                        fontWeight: 'bold'
                    }
                },
            },

            //The below properties are watched separately for changes.

            //Series object (optional) - a list of series using normal highcharts series options.
            series: [{
                data: [10, 15, 12, 8, 7]
            }],
            //Title configuration (optional)
            title: {
                text: 'Hello'
            },
            //Boolean to control showng loading status on chart (optional)
            loading: false,
            //Configuration for the xAxis (optional). Currently only one x axis can be dynamically controlled.
            //properties currentMin and currentMax provied 2-way binding to the chart's maximimum and minimum
            xAxis: {
                currentMin: 0,
                currentMax: 20,
                title: {text: 'values'}
            },
            //Whether to use HighStocks instead of HighCharts (optional). Defaults to false.
            useHighStocks: false,
            size: {
                width: 200,
                height: 200
            }

        }


  })
