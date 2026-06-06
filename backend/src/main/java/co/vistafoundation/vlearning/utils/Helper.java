package co.vistafoundation.vlearning.utils;

import org.springframework.stereotype.Service;

@Service
public class Helper {
	public  String extractYTId(String ytUrl) {
//	    String vId = null;
	    String [] arr = ytUrl.split("/");
	    String temp = arr[arr.length-1];
	    //System.out.println("video id"+" "+temp);
//	    
//	    Pattern pattern = Pattern.compile(
//	                     "^https?://(?:www\\.)?youtu(?:\\.be|be\\.com)/(?:\\S+/)?(?:[^\\s/]*(?:\\?|&)vi?=)?([^#?&]+)", 
//	                     Pattern.CASE_INSENSITIVE);
//	    Matcher matcher = pattern.matcher(ytUrl);
//	    if (matcher.matches()){
//	        vId = matcher.group(1);
//	    }
	    return temp;
	}
}
