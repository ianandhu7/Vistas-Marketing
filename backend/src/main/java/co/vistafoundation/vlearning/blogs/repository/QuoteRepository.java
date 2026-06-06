package co.vistafoundation.vlearning.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.vistafoundation.vlearning.blogs.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

   
}