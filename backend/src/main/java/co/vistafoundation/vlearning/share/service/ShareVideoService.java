/**
 * 
 */
package co.vistafoundation.vlearning.share.service;


import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.share.dto.ShareVideoDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoLinkDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoMetaInfoDTO;

/**
 * @author NaveenKumar
 *
 */
public interface ShareVideoService {

	public Document<ShareVideoDTO> getSharedVideoIdInfo(String encryptedVidesoId);

	public Document<String> getShareVideoLink(ShareVideoLinkDTO svLinkDTO);

	public Document<String> updatedVideoViewCount(String encryptedVidesoId);
	
	public Document<ShareVideoMetaInfoDTO> getMetaInfo(String idShareVideo);

}
