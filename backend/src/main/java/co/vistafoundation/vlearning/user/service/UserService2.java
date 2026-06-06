package co.vistafoundation.vlearning.user.service;

import org.springframework.data.domain.Page;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.UserFetchDTOV2;
import co.vistafoundation.vlearning.user.dto.UserInfoDto;

@Service
public interface UserService2 {

	public Document<Page<UserListDTO>> fetchAllVLUsersLists(UserFetchDTOV2 userFetchDTO);
	
	public Document<UserInfoDto> fetchAllVLUsersinfo(Long userSurId);

	/**
	 * @param jwt
	 * @return
	 */
	Document<Object> getUserActiveStatus(String jwt,Device device,Boolean isBrowser);

}
