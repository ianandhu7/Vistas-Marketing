package co.vistafoundation.vlearning.product.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.product.model.ProductGroup;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

	public ProductGroup findByIdClassStandardAndIdProductLine(Long idClassStandard, Long idProductLine);

	public ProductGroup findByIdClassStandardAndIdProductLineAndIdSyllabus(Long idClassStandard, Long idProductLine,
			Long idSyllabus);

	public ProductGroup findByExtraCurrCategoryAndIdProductLine(String extraCurrCategory, Long idProductLine);

	public ProductGroup findByIdProductLine(Long idProductLine);

	public ProductGroup findByIdProductGroup(Long idProductGroup);

	public List<ProductGroup> getByIdProductLine(Long idProductLine);

	@Query(value = "From ProductGroup p where p.extraCurrCategory is not null", nativeQuery = false)
	List<ProductGroup> fetchextraActivities();

	public List<ProductGroup> findByIdClassStandardAndIdSyllabusAndIdProductLineLessThan(Long idClassStandard,
			Long idSyllabus, Long l);

	public List<ProductGroup> findByIdClassStandardAndIdSyllabusAndIdProductLineIn(Long idClassStandard,
			Long idSyllabus, Set<Long> tempIds);

	public ProductGroup findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(Long idClassStandard,
			Long idProductLine, Long idSyllabus, String level);

	public ProductGroup findByIdProductLineAndIdClassStandardAndExtraCurrCategoryAndIdSyllabus(Long idProductLine, 
			Long idClassStandard, String ExtraCurrCategory, Long idSyllabus);

	public List<ProductGroup> findByIdClassStandardAndIdSyllabusAndIdProductLine(Long idClassStandard, Long idSyllabus,
			long l);

}
