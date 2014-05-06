'use strict';

describe('Controller: CalendarCtrl', function () {

  // load the controller's module
  beforeEach(module('webClientApp'));

  var CalendarCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CalendarCtrl = $controller('CalendarCtrl', {
      $scope: scope
    });
  }));

  it('should use uiConfig', function () {
    expect(scope.uiConfig).not.toBe(undefined);
  });
});
