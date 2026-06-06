package co.vistafoundation.vlearning.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.product.model.ProductLine;



public interface ProductLineRepository extends JpaRepository<ProductLine, Long>{
	
	public List<ProductLine> findByProductCategory(String productCategory);
	
	public ProductLine findByidProductLine(Long idProductLine);
	
	@Query("select pl from ProductLine pl where pl.productLineCd = 'EXTRA_CUR' or pl.productLineCd = 'ACADEMIC'")
	public List<ProductLine> getExtraAndAcademic();

	public List<ProductLine> findByIdProductLineIn(List<Long> finalProductLineIdList);
}
