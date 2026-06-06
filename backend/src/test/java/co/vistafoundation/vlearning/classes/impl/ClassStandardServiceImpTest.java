package co.vistafoundation.vlearning.classes.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.classes.service.ClassStandardService;

/**
 * 
 * @author Naveen Kumar
 *
 */
@SpringBootTest
class ClassStandardServiceImpTest {

	@Autowired
	ClassStandardService classStandardService;

	@MockBean
	ClassRepository classRepository;

	@BeforeEach
	public void setUp() throws ParseException {

		// Initialize ClassStandard data
		List<ClassStandard> classList = new ArrayList<ClassStandard>();
		ClassStandard class1 = new ClassStandard();
		class1.setClassStandadName("Class 1");
		class1.setIdClassStandard(1L);
		ClassStandard class2 = new ClassStandard();
		class2.setClassStandadName("Class 3");
		class2.setIdClassStandard(2L);
		ClassStandard class3 = new ClassStandard();
		class3.setClassStandadName("Class 2");
		class3.setIdClassStandard(3L);
		classList.add(class1);
		classList.add(class2);
		classList.add(class3);

		Mockito.when(classRepository.findAll()).thenReturn(classList);

	}

	@Test
	void testFindAllClass() {

		List<ClassStandard> classStandardList = classStandardService.findAllClass();

		assertEquals(false, classStandardList.isEmpty());
		assertEquals(3, classStandardList.size());
		assertEquals("Class 1", classStandardList.get(0).getClassStandadName());
		assertEquals(1L, classStandardList.get(0).getIdClassStandard());

		assertEquals("Class 2", classStandardList.get(2).getClassStandadName());
		assertEquals(3L, classStandardList.get(2).getIdClassStandard());

	}

}
