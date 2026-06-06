package co.vistafoundation.vlearning.classes.service;

import java.util.List;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.common.response.Document;

public interface ClassStandardService {
	
	public List<ClassStandard>findAllClass();
	
	public Document<List<ClassStandard>> getAllClassStandardProdutGroup();
    
	public Document<List<ClassStandard>> getAllClassStandardAcademicProdutGroup();
	
	public Document<List<ClassStandard>> getAllClassStandardByStateAndSyllabus(Long idState,Long IdSyllabus);
}
