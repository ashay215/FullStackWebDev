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
		        		$("#hiddenfollowtype").html("unfollow");
		        		$("#followbutton").html("Unfollow");
		        		$("#followers").append("<a href = \"" + "userpage.jsp?uname=" + curr_user + "\"><tr id=\"follower_" + curr_user + "\"><td>" + curr_user + "</td></tr></a>");
		        	}
		        	else
		        	{
		        		$("#hiddenfollowtype").html("follow");
		        		$("#follower_"+curr_user).remove();
		        		$("#followbutton").html("Follow");
		        		
		        	}
		        }
		});
		
	});
	
});