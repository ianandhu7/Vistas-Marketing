/**
 * 
 */
package co.vistafoundation.vlearning.share.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.share.dto.ShareVideoDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoLinkDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoMetaInfoDTO;
import co.vistafoundation.vlearning.share.service.ShareVideoService;

/**
 * @author NaveenKumar
 *
 */
@RestController
@RequestMapping("api/v1/share")
public class ShareVideoController {

	@Autowired
	ShareVideoService shareVideoService;

	/**
	 * @author NaveenKumar
	 * 
	 *         This method retrieves a streamable URL for a video with the specified
	 *         encrypted ID. The encrypted ID is used to retrieve the video's
	 *         information from the database and generate a streamable URL for the
	 *         video content. That generated URL can be used to stream the video
	 *         content to the user.
	 * 
	 * @param encrptedId
	 * 
	 * @return ShareVideoDTO
	 */
	@GetMapping(value = "/{encrptedId}")
	public ResponseEntity<?> getShareVideoInfo(@PathVariable String encrptedId) {
		Document<ShareVideoDTO> reponses = shareVideoService.getSharedVideoIdInfo(encrptedId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author AbdulElahi
	 * 
	 *         This method generates an encrypted URL for a specified video ID. The
	 *         encrypted URL can be used to securely access the video content
	 *         without exposing the actual video ID. This helps to protect the
	 *         privacy and security of the video content.
	 * 
	 * @param shareVideoLinkDTO
	 * @return String
	 */

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping("/get-link")
	public ResponseEntity<?> getShareVideoLink(@RequestBody @Valid ShareVideoLinkDTO shareVideoLinkDTO) {
		Document<?> doc = shareVideoService.getShareVideoLink(shareVideoLinkDTO);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * 
	 * @author NaveenKumar
	 * 
	 *         This method increments the view count of a video with the specified
	 *         encrypted ID. The encrypted ID is used to retrieve the video's
	 *         information from the database and increment its view count. The view
	 *         count is incremented asynchronously to avoid slowing down the
	 *         response time for the user.
	 * 
	 * @param encrptedId
	 * @return
	 */
	@PutMapping(value = "/{encrptedId}")
	public ResponseEntity<?> updateShareVideoViewCount(@PathVariable String encrptedId) {

		shareVideoService.updatedVideoViewCount(encrptedId);

		Document<String> response = new Document<>();
		response.setData("View count updated.");
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Request Sucessfull");
		return ResponseEntity.status(response.getStatusCode()).body(response);

	}

	/**
	 * @author AbdulElahi
	 * 
	 *         Retrieves the metadata information of a video that corresponds to the
	 *         provided encrypted video ID.
	 * 
	 * @param encryptedVideoID A string representing the encrypted video ID that
	 *                         needs to be decrypted in order to retrieve the video
	 *                         metadata information.
	 * @return A JSON object containing the metadata information of the requested
	 *         video. The metadata information includes details such as the title of
	 *         the video, the subtitle, the thumbnail details.
	 * 
	 * @throws AppException If the encryptedVideoID is invalid or not found, the
	 *                      server will return an error message indicating that the
	 *                      video was not found.
	 */
	@GetMapping("/{idShareVideo}/meta-info")
	public ResponseEntity<Document<ShareVideoMetaInfoDTO>> getMetaInfo(
			@PathVariable("idShareVideo") String idShareVideo) {
		Document<ShareVideoMetaInfoDTO> metaInfo = shareVideoService.getMetaInfo(idShareVideo);
		return new ResponseEntity<>(metaInfo, HttpStatus.OK);
	}

}
