/**
 * 
 *//**
 * 
 */
$(document).ready(function(){
	$("#watchicon").click(function(event)
	{
		console.log('clicked');
		event.preventDefault();
		var curr_user = $("#hiddencurruser").html(); 
		var title_ = $("#hiddentitle").html(); 
		
		$.ajax({
			 	url : "../jsp/movieaction.jsp",
		        type: 'GET',
		        data: {curruser: curr_user, 
		        		title: title_,
		        		type: "Watched",
		        		},

		        error : function(jqXHR, textStatus, errorThrown) {
		            alert(textStatus);
		        },
		        success : function(){
		        	console.log("Success");
		        }
		});
		
	});
	
	$("#likeicon").click(function(event)
		{
			console.log('clicked');
			event.preventDefault();
			var curr_user = $("#hiddencurruser").html(); 
			var title_ = $("#hiddentitle").html(); 
			
			$.ajax({
				 	url : "../jsp/movieaction.jsp",
			        type: 'GET',
			        data: {curruser: curr_user, 
			        		title: title_,
			        		type: "Liked",
			        		},
	
			        error : function(jqXHR, textStatus, errorThrown) {
			            alert(textStatus);
			        },
			        success : function(){
			        	console.log("Success");
			        }
		});
			
	});
	
	$("#dislikeicon").click(function(event)
			{
				console.log('clicked');
				event.preventDefault();
				var curr_user = $("#hiddencurruser").html(); 
				var title_ = $("#hiddentitle").html(); 
				
				$.ajax({
					 	url : "../jsp/movieaction.jsp",
				        type: 'GET',
				        data: {curruser: curr_user, 
				        		title: title_,
				        		type: "Disliked",
				        		},
		
				        error : function(jqXHR, textStatus, errorThrown) {
				            alert(textStatus);
				        },
				        success : function(){
				        	console.log("Success");
				        }
			});
				
		});

});