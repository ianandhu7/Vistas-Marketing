package co.vistafoundation.vlearning.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.ChatbotQA;

public interface ChatbotRepository extends JpaRepository<ChatbotQA,Long>{

	Page<ChatbotQA> findByIdVlUserAndStatus(Long idVlUser,boolean status,Pageable pageable);
	
	long deleteByIdChatbotQa(Long idChatbotQa);
	
	public ChatbotQA findByIdChatbotQa(Long idChatbotQa);
}
