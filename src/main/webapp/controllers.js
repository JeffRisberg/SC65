myApp.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
    $scope.appState = "home";

    //$scope.items = [
    //    {name: 'Challenge1', startDate: new Date(101, 5, 5), endDate: new Date(101, 6, 1)},
    //    {name: 'Challenge2', startDate: new Date(101, 7, 5), endDate: new Date(101, 8, 1)}
    //];

    $scope.items = {};

    $http.get('challenge.json').
        success(function (response, status, headers, config) {

            $scope.items = response.data;
        }).
        error(function (data, status, headers, config) {
            // log error
            console.log("error");
        })
}]);