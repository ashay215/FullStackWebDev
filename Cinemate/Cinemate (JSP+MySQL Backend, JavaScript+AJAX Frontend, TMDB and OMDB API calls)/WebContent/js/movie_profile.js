//function that changes the rating of the movie
//parameters:
//star -- html element that was clicked
//title -- movie title
//titleKey -- the parameter name for the title in the ajax URL
//ratingKey -- the parameter name for the rating  in the ajax URL
function rateMovie(star, title, titleKey, ratingKey, userID) {
	//the id of the star element holds what rating it represents (i.e. star with id = 5 means the user rated the movie a 5/10)
	var rating = parseInt(star.id);

	var xhttp = new XMLHttpRequest();
	//just to be safe and make sure our parameter names in our get request match the strings used in the servlet, we pass in the keys so we can reference StringConstants in movie_profile.jsp
	xhttp.open("GET", "/"+window.location.pathname.split("/")[1]+"/RatingEventServlet?"+titleKey+"="+
			title+"&"+ratingKey+"="+rating+"&"+"userID="+userID, false);
	xhttp.send();
	//if we got a response back (we always should in this case)
	if (xhttp.responseText.trim().length > 0) {
		//the response sent is the new average rating of the movie
		var newRating = parseInt(xhttp.responseText);
		//get both the average rating div and the user rating div
		//for some reason, if we only change the average rating html, the user rating stars don't line up anymore with the average rating stars
		//so to avoid this, we will just reset the user rating stars too
		var average_rating = document.getElementById("average_movie_rating");
		var user_rating = document.getElementById("user_movie_rating");
		user_rating.innerHTML = "";

		var html = "";

		for (let i = 0; i< 10; ++i) {
			//for the user rating stars, we will create a new span element
			var newstar = document.createElement("span");
			//set the id of the newstar to match what the rating will represent
			newstar.id = (10-i).toString();
			//set the onclick function of the new star to call this (rateMovie) function
			newstar.onclick = function (){ 
				rateMovie(this, title, titleKey, ratingKey) 
			};
			//set innerHTML
			newstar.innerHTML = "☆";
			//append it to the user rating div
			user_rating.appendChild(newstar);

			//append the appropriate star span to the average rating div depending on whether i is less than or greater than the new average rating
			if (i<newRating){
				html += "<span>★</span>";
			}
			else{
				html += "<span>☆</span>";
			}
		}

		average_rating.innerHTML = html;
	}

}

