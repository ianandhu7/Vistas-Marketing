package co.vistafoundation.vlearning.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.ticket.model.IssueCategory;


public interface IssueCategoryRepository extends JpaRepository<IssueCategory,Integer>{

}
