/**
 * 
 */
$(document).ready(function(){
	$("#followbutton").click(function(event)
	{
		console.log('clicked');
		event.preventDefault();
		var curr_user = $("#hiddencurruser").html(); 
		var to_follow = $("#hiddenpageuser").html(); 
		var followtype = $("#hiddenfollowtype").html();
		//console.log(followtype);
		
		$.ajax({
			 	url : "../jsp/updateprofile.jsp",
		        type: 'GET',
		        data: {curruser: curr_user, 
		        		tofollow: to_follow,
		        		type: followtype,
		        		},

		        error : function(jqXHR, textStatus, errorThrown) {
		            alert(textStatus);
		        },
		        success : function(){
		        	console.log("Success");
		        	if(followtype == "follow")
		        	{
		        		$("#followbutton").html("Unfollow");
		        		$("#followers").append("<tr id=\"follower_" + curr_user + "\"><td>" + curr_user + "</td></td>");
		        	}
		        	else
		        	{
		        		$("#followbutton").html("Follow");
		        		$("#follower_"+curr_user).remove();
		        	}
		        }
		});
		
	});
	
});