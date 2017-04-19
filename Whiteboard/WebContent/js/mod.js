/**
 * 
 */
function deletePost(postID)
{
	var url = "../ModServlet?type=post&id="+postID
	// create AJAX request
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			location.reload(true);
		}
	}
	req.send(null);
}

function deleteDocument(documentID)
{
	var url = "../ModServlet?type=doc&id="+documentID
	// create AJAX request
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) { 
			location.reload(true);
		}
	}
	req.send(null);
}
