package co.vistafoundation.vlearning.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;

public interface ProductSampleRepository extends JpaRepository<ProductSampleVideo, Long>{
	
	public List<ProductSampleVideo> findByProduct(Product prod);
	
}
