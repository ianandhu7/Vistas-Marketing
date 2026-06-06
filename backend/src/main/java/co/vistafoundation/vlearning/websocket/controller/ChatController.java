package co.vistafoundation.vlearning.websocket.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQndA;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassQndARepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassQuestionRepository;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.websocket.dto.ChatMessage;

/**
 * @author vk
 *
 */
@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private LiveClassQuestionRepository liveClassQuestionRepository;

	@Autowired
	private LiveClassQndARepository liveClassQndARepository;
	
	@Autowired
	private TeacherRepository teacherRepository;

	/* public chat */
	@MessageMapping("/chat.sendMessage")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		Teacher teacher = teacherRepository.findByIdTeacher(chatMessage.getSenderIdVlUser());
		if(teacher == null) {
			throw new AppException("Tecaher Details Not found!");
		}
		//save teacher question and answer to QA table
		LiveClassQndA liveClassQndA = new LiveClassQndA();
		liveClassQndA.setIdLiveClass(chatMessage.getIdLiveClass());
		liveClassQndA.setUserSurId(teacher.getUser().getUserSurId());
		liveClassQndA.setAnswerText(chatMessage.getAnswer());
		liveClassQndA.setQuestionText(chatMessage.getQuestion());
		if (!Objects.isNull(liveClassQndA)) {
			liveClassQndA = liveClassQndARepository.save(liveClassQndA);
		}
		simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getIdLiveClass()+"/public", chatMessage);
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	public ChatMessage addUser(@Payload ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		// add user in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getIdLiveClass()+"/public", chatMessage);
		return chatMessage;
	}

	/* private chat */
	@MessageMapping("/chat.sendPrivateMessage")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
		
		//save students question to question table
		LiveClassQuestion liveClassQuestion = new LiveClassQuestion(); 
		liveClassQuestion.setIdLiveClass(chatMessage.getIdLiveClass());
		liveClassQuestion.setUserSurId(chatMessage.getSenderIdVlUser());
		liveClassQuestion.setQuestionText(chatMessage.getQuestion());
		if (!Objects.isNull(liveClassQuestion) && !chatMessage.getSenderIdVlUser().equals(chatMessage.getReceiverIdVlUser())) {
			liveClassQuestion = liveClassQuestionRepository.save(liveClassQuestion);
		}
		simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverIdVlUser().toString(), "/reply", chatMessage);
	}

	@MessageMapping("/chat.addPrivateUser")
	@SendTo("/queue/reply")
	public ChatMessage addPrivateUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// add user in web socket session
		headerAccessor.getSessionAttributes().put("private-username", chatMessage.getSender());
		return chatMessage;
	}
}
