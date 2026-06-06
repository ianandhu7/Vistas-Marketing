package co.vistafoundation.vlearning.webex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.webex.dto.WebExMeetingDetails;
import co.vistafoundation.vlearning.webex.model.WebExAssignedTeacherDTO;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;
import co.vistafoundation.vlearning.webex.repository.WebExRepository;

@Service
public class WebExService {

	@Autowired
	private WebExRepository webExRepository;

	@Autowired
	private WebExPoolRepository webExPoolRepository;

	@Autowired
	TeacherRepository teacherRepository;

	public WebExMeetingDetails saveMeetingDetails(WebExMeetingDetails meetingDetails) {

		WebExMeetingDetails save = webExRepository.save(meetingDetails);
		return save;
	}

	public WebExPool saveTeacherWebExCredentialsToWebExPool(WebExPool webExPool) {
		WebExPool save = webExPoolRepository.save(webExPool);
		return save;

	}

	public List<WebExPool> fetchAllUnAssignedTeachers() {
		List<WebExPool> fetchAllUnAssignedTeachers = webExPoolRepository.fetchAllUnAssignedTeachers();
		return fetchAllUnAssignedTeachers;

	}

	public List<WebExPool> fetchAllwebx() {
		List<WebExPool> fetchAllUnAssignedTeachers = webExPoolRepository.findAll();
		return fetchAllUnAssignedTeachers;

	}

	public WebExPool fetchHostWebExCredentials(Long idWebExPool) {
		WebExPool findByIdWebExPool = webExPoolRepository.findByIdWebExPool(idWebExPool);
		return findByIdWebExPool;

	}

	public Document<?> checkForWebExIdExistence(String webExId) {

		Document<Boolean> doc = new Document<>();

		try {

			if (webExId == null) {
				throw new NullPointerException("WebEx Host User Id Cannot be Null or Empty");
			}

			Boolean exists = webExPoolRepository.existsByWebExUserId(webExId);

			if (exists) {
				doc.setData(Boolean.TRUE);
				doc.setMessage("WebEx Host Id is Already Exists");
				doc.setStatusCode(200);
			} else {
				doc.setData(Boolean.FALSE);
				doc.setMessage("WebEx Host Id Not Found.Can be Added to WebEx Pool");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public Document<?> fetchAllAssignedWebExPoolWithTeacherDetails() {

		Document<List<WebExAssignedTeacherDTO>> doc = new Document<>();

		try {

			List<WebExAssignedTeacherDTO> list = new ArrayList<>();

			List<Teacher> listOfAllTeachers = teacherRepository.findAll();

			if (listOfAllTeachers.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Teacher List is Empty");
				doc.setStatusCode(200);
			}

			else {

				for (Teacher teacher : listOfAllTeachers) {

					if (teacher.getIdWebexPool() != null) {
						WebExPool webExPool = webExPoolRepository.findByIdWebExPool(teacher.getIdWebexPool());

						if (webExPool == null)
							throw new NullPointerException(
									"Invalid WebEx id Found in , idTeacher: " + teacher.getIdTeacher());
						webExPool.setWebExPassword(null);

						WebExAssignedTeacherDTO dto = new WebExAssignedTeacherDTO();
						dto.setPool(webExPool);
						dto.setTeacher(teacher);

						list.add(dto);
					}

				}

				doc.setData(list);
				doc.setMessage("List Of All Assigned WebEx Pool Data");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public Document<?> deleteWebExPoolAndAlsoUpdateTeacherRecordAccordingly(Long idWebExPool) {

		Document<Boolean> doc = new Document<>();

		try {

			if (idWebExPool == null) {
				throw new NullPointerException("WebEx Pool Id cannot be Null");
			}

			WebExPool exPool = webExPoolRepository.findByIdWebExPool(idWebExPool);

			if (exPool == null) {
				throw new NullPointerException("Data Not Found with the Id Provided");
			}

			Teacher teacher = teacherRepository.findByIdWebexPool(exPool.getIdWebExPool());
			if (teacher != null) {
//				teacher.setIdWebexPool(null);
//				teacherRepository.save(teacher);
				throw new Exception("Cannot Delete this WebEx Host From Pool Because It has been Assigned to "
						+ teacher.getTeacherName());
			}

			webExPoolRepository.delete(exPool);

			doc.setData(Boolean.TRUE);
			doc.setMessage("Deletion Success");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public Document<?> reAssignWebExPoolToTeacher(Long idWebExPool, Long idTeacher) {

		Document<Boolean> doc = new Document<>();

		try {

			if (idWebExPool == null) {
				throw new NullPointerException("WebEx Pool Id Cannot be Null");
			}

			if (idTeacher == null) {
				throw new NullPointerException("Teacher Id Cannot be Null");
			}

			WebExPool webExPool = webExPoolRepository.findByIdWebExPool(idWebExPool);

			if (webExPool == null) {
				throw new NullPointerException("WebEx Pool Data Not Found with the Id Provided");
			}

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null) {
				throw new NullPointerException("Teacher Data not Found with the Id Provided");
			}

			if (teacher.getIdWebexPool().equals(idWebExPool)) {
				throw new Exception("Data Remains Unchanged");
			}

			WebExPool alreadyAssignedWebExPool = webExPoolRepository.findByIdWebExPool(teacher.getIdWebexPool());
			alreadyAssignedWebExPool.setAvailableFlag(Boolean.FALSE);
			webExPoolRepository.save(alreadyAssignedWebExPool);

//			Teacher teacher2 = teacherRepository.findByIdWebexPool(idWebExPool);

//			if (teacher2 != null) {
//				throw new Exception("This WebEx Id Has Already Been Assigned to " + teacher2.getTeacherName()
//						+ ". One WebEx Id Can Only be Assigned to One Teacher");
//			}

//			if (teacher.getIdWebexPool() != null || !teacher.getIdWebexPool().equals(0)) {
//				throw new Exception("This Teacher has Already been Assigned to One WebEx Host. Cannot Re-Assign Again");
//			}

			teacher.setIdWebexPool(idWebExPool);
			teacherRepository.save(teacher);

			// Now Make this WebEx Pool be Available
			webExPool.setAvailableFlag(Boolean.TRUE);
			webExPoolRepository.save(webExPool);

			doc.setData(Boolean.TRUE);
			doc.setMessage("WebEx Host Assigned Successfully to the Selected Teacher");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public Document<?> updateWebExPool(Long idWebExPool, WebExPool webExPool) {

		Document<Boolean> doc = new Document<>();

		try {

			if (idWebExPool == null) {
				throw new NullPointerException("WebEx Pool Id Cannot be Null");
			}

			if (webExPool == null) {
				throw new NullPointerException("WebEx Pool Cannot be Null");
			}

			WebExPool pool = webExPoolRepository.findByIdWebExPool(idWebExPool);

			if (pool == null) {
				throw new NullPointerException("WebEx Pool Not Found");
			}
			pool.setAvailableFlag(webExPool.getAvailableFlag());
			pool.setWebExPassword(webExPool.getWebExPassword());
			pool.setWebExUserId(webExPool.getWebExUserId());

			doc.setData(Boolean.TRUE);
			doc.setMessage("Data Updated Successfully");
			doc.setStatusCode(200);
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public Document<List<WebExPool>> fetchAllUnassigedWebExPoolIdInculdingTeacheWebEx(Long idTeacher) {
		Document<List<WebExPool>> doc = new Document<>();

		try {
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null) {
				throw new NullPointerException("Teacher Data not Found with the Id Provided");
			}

			List<WebExPool> unassignedPoolData = webExPoolRepository.findByAvailableFlag(Boolean.FALSE);

			if (unassignedPoolData.isEmpty()) {
				if (teacher.getIdWebexPool() != null) {
					WebExPool webExPool = webExPoolRepository.findByIdWebExPool(teacher.getIdWebexPool());

					if (webExPool == null) {
						throw new NullPointerException("WebEx Pool Found is Invalid in the teacher profile.");
					}
					unassignedPoolData.add(webExPool);
				} else {
					throw new NullPointerException("There No Unassigned webex  Availble ");

				}
			} else {

				if (teacher.getIdWebexPool() != null) {
					WebExPool webExPool = webExPoolRepository.findByIdWebExPool(teacher.getIdWebexPool());

					if (webExPool == null) {
						throw new NullPointerException("WebEx Pool Found is Invalid in the teacher profile.");
					}
					unassignedPoolData.add(webExPool);
				}
			}

			doc.setData(unassignedPoolData);
			doc.setMessage("Request Successfull");
			doc.setStatusCode(200);

		}

		catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

}
