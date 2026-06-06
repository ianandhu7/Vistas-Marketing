/**
 * 
 */
package co.vistafoundation.vlearning;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/")
public class WelcomeController {

	@Value("${app.version}")
	private String appVersion;

	@GetMapping
	public ModelAndView welcome(Model model) {
		ModelAndView mv = new ModelAndView("index");
		model.addAttribute("systemDateTime", new Date());
		model.addAttribute("version", appVersion);
		return mv;
	}

}
