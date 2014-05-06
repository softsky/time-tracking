'use strict';

describe('Controller: WeekCtrl', function () {

  // load the controller's module
  beforeEach(module('webClientApp'));

  var WeekCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    WeekCtrl = $controller('WeekCtrl', {
      $scope: scope
    });
  }));

  it('should use uiConfig', function () {
    expect(scope.uiConfig).not.toBe(undefined);
  });
});
