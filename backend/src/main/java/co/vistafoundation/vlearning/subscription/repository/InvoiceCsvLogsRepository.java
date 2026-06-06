package co.vistafoundation.vlearning.subscription.repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subscription.model.InvoiceCsvLogs;

public interface InvoiceCsvLogsRepository extends JpaRepository<InvoiceCsvLogs, Long> {

	public List<InvoiceCsvLogs> findByFromDateAndToDate(Instant fromDate, Instant toDate);

	public List<InvoiceCsvLogs> findByInvoiceName(String invoiceName);

	public Page<InvoiceCsvLogs> findAllByOrderByIdInvoiceCsvLogsDesc(Pageable paging);

	public Page<InvoiceCsvLogs> findAllByOrderByIdInvoiceCsvLogsAsc(Pageable paging);

	@Query(value = "select iv from InvoiceCsvLogs iv " + "where (:orderId is null or iv.orderId = :orderId ) and "
			+ "(:mobile is null or iv.mobile = :mobile) and "
			+ "(:status is null or iv.statusList = :status ) and "
			+ "(:email is null or iv.email = :email) and " + "(:from is null or DATE(iv.fromDate) >=:from) and "
			+ "(:to is null or DATE(iv.toDate) <=:to) and" + "(:invoiceName is null or iv.invoiceName = :invoiceName)"
			+ "order by iv.id Desc")
	public Page<InvoiceCsvLogs> getInvoiceCsvLogsBasedOnParamDesc(String orderId, String mobile, String email,
			String status, Date from, Date to, String invoiceName, Pageable paging);


@Query(value = "select iv from InvoiceCsvLogs iv " + "where (:orderId is null or iv.orderId = :orderId ) and "
			+ "(:mobile is null or iv.mobile = :mobile) and "
			+ "(:status is null or iv.statusList = :status ) and "
			+ "(:email is null or iv.email = :email) and " + "(:from is null or DATE(iv.fromDate) >=:from) and "
			+ "(:to is null or DATE(iv.toDate) <=:to) and" + "(:invoiceName is null or iv.invoiceName = :invoiceName)"
			+ "order by iv.id Asc")
	public Page<InvoiceCsvLogs> getInvoiceCsvLogsBasedOnParamAsc(String orderId, String mobile, String email,
			String status, Date from, Date to, String invoiceName, Pageable paging);

    public InvoiceCsvLogs findByIdInvoiceCsvLogs(Long idInvoiceCsvLogs);

}
