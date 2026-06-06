package co.vistafoundation.vlearning.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.ticket.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Integer>{

}
