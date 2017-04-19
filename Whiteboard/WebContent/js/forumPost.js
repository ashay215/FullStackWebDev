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
function reply(parentID, username)
{
	if(document.getElementById('body').value === "") {
		console.log("no body text");
	}
	else {
		console.log('replying');
		var url = "/Whiteboard/ForumPostServlet?title="+document.getElementById('title').value+"&body="
				+document.getElementById('body').value+"&type=submit"+"&classID="+GetURLParameter("classID")+"&parentID="+parentID;
		var req = new XMLHttpRequest();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
	if(req.readyState == 4 && req.status == 200) { 
		
		var postID = req.responseText;
		console.log(postID);
		var list = document.getElementById('reply-list');
		var entry = document.createElement('li');
		
		var body = document.getElementById('body').value;
		var bodyNode = document.createElement('text');
		bodyNode.className = 'post-reply';
		bodyNode.innerHTML = body;
		
		var upButton = document.createElement('button');
		upButton.className = 'upvote';
		upButton.onclick = function(){upvote(postID)};
		var upArrow = document.createElement('i');
		upArrow.className = 'fa fa-arrow-up';
		upButton.appendChild(upArrow);
		entry.appendChild(upButton);
		
		var dButton = document.createElement('button');
		dButton.className = 'downvote';
		dButton.onclick = function(){downvote(postID)};
		var dArrow = document.createElement('i');
		dArrow.className = 'fa fa-arrow-down';
		dButton.appendChild(dArrow);
		entry.appendChild(dButton);
		
		var score = document.createElement('text');
		score.className = 'score';
		var scoreID = 'score'+postID;
		score.id = scoreID;
		score.innerHTML = '1';
		entry.appendChild(score);

		entry.appendChild(bodyNode);
		
		var br = document.createElement('br');
		entry.appendChild(br);
		var posterName = document.createElement('text');
		posterName.className = 'poster-name';
		posterName.innerHTML = 'posted by:'+username;
				entry.appendChild(posterName);
				
				list.insertBefore(entry, list.childNodes[0]);
			}
		}
	req.send(null);
	}
}