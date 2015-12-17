var app=angular.module('mysop', [])
app.controller('mysopCtrl',function($scope,$timeout) {	
	  var sop_number,sop_version,step_purpose,step_intro,step_text_context;
	  var step_order=0;
	  $scope.order_selected="1";
	  $scope.process="finish";
	  $scope.step_orders = [];
	  $scope.addForm = function(){	
		    
		    $scope.step_orders.push({
		        	order: step_order,
		            name: $scope.input
		        });
		    
		    jsonAjaxPost();
		    
		    $scope.input='';
		    $scope.step_purpose=''; 
			$scope.step_intro=''; 
			$scope.step_text_content='';
			$scope.start_message='';
			$scope.process = "finish"; 
			$scope.select(step_order);
	  };
	  $scope.copyForm = function(){	
		  	step_purpose=$scope.step_purpose; 
		  	step_intro=$scope.step_intro; 
		  	step_text_content=$scope.step_text_content; 
		  	start_rule=$scope.start_message;
	  };	
	  $scope.pasteForm = function(){	
		  	clearStep();
			$scope.step_purpose=step_purpose; 
			$scope.step_intro=step_intro; 
			$scope.step_text_content=step_text_content; 
			$scope.start_rule=start_message;
	  };	
	 
	  $scope.select= function(step_order) {
	        $scope.order_selected = step_order; 
	        $timeout( function(){getStepDetail();}, 5);
	      
	 };
	
	  $scope.start= function() {
		  	step_order++;
		  	$scope.order_selected = step_order;
	        $scope.process = "start"; 
	        
	        
	 };
	 
	 $scope.displayProcess= function(process) {
		 	return $scope.process === process; 
	 };
	 $scope.isActive = function(step_order) {
	        return $scope.order_selected === step_order;
	 };
	 
	 
  });
app.controller('sopcontentCtrl',function($scope) {
	$scope.order_selected="1";
	$scope.likeitem="unlike";
	$scope.collectitem="uncollect";
	$scope.like= function(){
		$scope.likeitem ="like"; 
	}
	$scope.likeDisplay= function(item){
		return $scope.likeitem === item; 
	}
	$scope.collect= function(){
		$scope.collectitem ="collect"; 
	}
	$scope.collectDisplay= function(item){
		return $scope.collectitem === item; 
	}
	$scope.selectorder= function(step_order) {
        $scope.order_selected = step_order;    
	 };
	 $scope.isDisplay = function(step_order) {
	        return $scope.order_selected === step_order;
	 };
	
})
