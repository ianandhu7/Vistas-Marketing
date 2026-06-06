package co.vistafoundation.vlearning.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.Language;

/**
 * 
 * @author NaveenKumar
 *
 */
public interface LanguageRepository extends JpaRepository<Language, Long> {

	public Language findByLanguage(String language);

	public Language findByIdLanguage(Long idSecondaryLanguage);

}
