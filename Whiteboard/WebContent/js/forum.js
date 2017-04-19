function GetURLParameter(sParam){
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}
function validate() {
	if(document.getElementById('title').value === "" || document.getElementById('body').value === "") {
		document.getElementById('error').innerHTML = "ERROR: One or more of the requested fields is empty";
	}
	else {
		var url = "../ForumServlet?title="+document.getElementById('title').value+"&body="
				+document.getElementById('body').value+"&type=submit"+"&classID="+GetURLParameter("classID");
		// create AJAX request
		var req = new XMLHttpRequest();
		req.open("GET", url, true);
		
		req.onreadystatechange = function () {
			if(req.readyState == 4 && req.status == 200) { 
				var postID = req.responseText;
				var postURL = "forumPost.jsp?postID="+postID+"&classID="+GetURLParameter("classID");
				console.log(postURL);
				document.location.href = postURL;
			}
		}
		req.send(null);
	}
}

function upvote(postID)
{
	var id = 'score'+postID;
	var score = document.getElementById(id);
	var intScore = parseInt(score.innerHTML);
	intScore+=1;
	score.innerHTML = intScore;
	
	var url = "../ForumServlet?postID="+postID+"&type=upvote";
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			if(req.responseText === "") {
				console.log("upvoted");
			}
			else {
				console.log(req.responseText);
				document.getElementById('error').innerHTML = req.responseText;
			}
		}
	}
	req.send(null);
}

function downvote(postID)
{
	var id = 'score'+postID;
	var score = document.getElementById(id);
	var intScore = parseInt(score.innerHTML);
	intScore-=1;
	score.innerHTML = intScore;
	
	var url = "../ForumServlet?postID="+postID+"&type=downvote";
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			if(req.responseText === "") {
				console.log("downvoted");
			}
			else {
				console.log(req.responseText);
				document.getElementById('error').innerHTML = req.responseText;
			}
		}
	}
	req.send(null);
}
