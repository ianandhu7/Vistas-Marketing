package co.vistafoundation.vlearning.classes.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.classes.service.ClassStandardService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;

@Service
public class ClassStandardServiceImp implements ClassStandardService {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;
	
	@Autowired
	ProductRepository productRepository;

	public List<ClassStandard> findAllClass() {

		List<ClassStandard> classList = classRepository.findAll();
		/*
		 * classList = classList.stream().filter(e ->
		 * !(e.getClassStandadName().equals("NA"))) .collect(Collectors.toList());
		 */
		return (classList);
	}

	@Override
	public Document<List<ClassStandard>> getAllClassStandardProdutGroup() {

		Document<List<ClassStandard>> result = new Document<List<ClassStandard>>();
		try {
			List<ProductGroup> pgList = productGroupRepository.findAll();

			if (pgList.isEmpty())
				throw new NullPointerException("Product Group is empty!");

			Set<ClassStandard> tempClass = new HashSet<ClassStandard>();

			for (ProductGroup pg : pgList) {

				if (pg.getIdProductLine() != 5 && pg.getIdProductLine() != 6 && pg.getIdProductLine() != 7) {

					ClassStandard temp = classRepository.findByIdClassStandard(pg.getIdClassStandard());

					if (temp == null)
						throw new AppException("invalid IDclassStandard data.");
					tempClass.add(temp);

				}
			}

			List<ClassStandard> classList = tempClass.stream()
					.sorted((s1, s2) -> s1.getIdClassStandard().compareTo(s2.getIdClassStandard()))
					.collect(Collectors.toList());
			classList = classList.stream().filter(e -> !(e.getClassStandadName().equals("NA")))
					.collect(Collectors.toList());

			result.setData(classList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@Override
	public Document<List<ClassStandard>> getAllClassStandardAcademicProdutGroup() {
		Document<List<ClassStandard>> result = new Document<List<ClassStandard>>();
		try {
			List<ProductGroup> pgList = productGroupRepository.findAll();

			if (pgList.isEmpty())
				throw new NullPointerException("Product Group is empty!");

			Set<ClassStandard> tempClass = new HashSet<ClassStandard>();

			for (ProductGroup pg : pgList) {

				if (pg.getIdProductLine() == 5) {

					ClassStandard temp = classRepository.findByIdClassStandard(pg.getIdClassStandard());
					if (temp == null)
						throw new AppException("invalid IDclassStandard data.");
					tempClass.add(temp);

				}
			}

			List<ClassStandard> classList = tempClass.stream()
					.sorted((s1, s2) -> s1.getIdClassStandard().compareTo(s2.getIdClassStandard()))
					.collect(Collectors.toList());
			classList = classList.stream().filter(e -> !(e.getClassStandadName().equals("NA")))
					.collect(Collectors.toList());

			result.setData(classList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@Override
	public Document<List<ClassStandard>> getAllClassStandardByStateAndSyllabus(Long idState, Long IdSyllabus) {
		Document<List<ClassStandard>> result = new Document<List<ClassStandard>>();
		try {
			
			List<Long> uniqueClassList =  productRepository.getDistinctClassStandardsBasedOnProductLineIdSyllabusAndIdState(5L,IdSyllabus,idState,Boolean.TRUE);

			if (uniqueClassList.isEmpty())
				throw new NullPointerException("No Class standard found");

			List<ClassStandard> classList = classRepository.findByIdClassStandardIn(uniqueClassList);
			
			if (uniqueClassList.isEmpty())
				throw new NullPointerException("Invalid class standard found");

			result.setData(classList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

		return result;
	}

}
