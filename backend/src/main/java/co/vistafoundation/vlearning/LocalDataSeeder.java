package co.vistafoundation.vlearning;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoCategoryRepository;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoRepository;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;

@Component
public class LocalDataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LocalDataSeeder.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DiscoverVideoCategoryRepository categoryRepository;

    @Autowired
    private DiscoverVideoRepository videoRepository;

    @Autowired
    private LiveClassRepository liveClassRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Checking local database tables for seeding...");

        // 1. Seed Teacher
        Teacher teacher = null;
        if (teacherRepository.count() == 0) {
            logger.info("No teachers found. Seeding a sample teacher...");
            User admin = userRepository.findByUserSurId(1L);
            if (admin != null) {
                teacher = new Teacher();
                teacher.setUser(admin);
                teacher.setActiveFlag(true);
                teacher.setTeacherName("Sarah Jenkins");
                teacher.setPrimarySubject("Mathematics");
                teacher.setCategory("ACADEMIC");
                teacher.setRating(5);
                teacher.setJoinedDate(new Date());
                teacher.setDisplayInHomepageFlag(true);
                teacher.setTeacherDesc("An expert mathematics teacher with 10 years of experience.");
                teacher = teacherRepository.save(teacher);
                logger.info("Sample teacher seeded: Sarah Jenkins");
            } else {
                logger.warn("Admin user (ID 1) not found. Cannot seed teacher.");
            }
        } else {
            teacher = teacherRepository.findAll().get(0);
        }

        // 2. Seed Discover Categories
        if (categoryRepository.count() == 0) {
            logger.info("No discover video categories found. Seeding categories...");
            categoryRepository.save(new DiscoverVideoCategory("Academics", "https://picsum.photos/300/200"));
            categoryRepository.save(new DiscoverVideoCategory("Building Legends", "https://picsum.photos/300/201"));
            logger.info("Categories seeded.");
        }

        // 3. Seed Discover Videos
        if (videoRepository.count() == 0) {
            logger.info("No discover videos found. Seeding sample videos...");
            
            DiscoverVideo v1 = new DiscoverVideo();
            v1.setVideoLink("wTV9PHlD-tU");
            v1.setLanguage("English");
            v1.setFeaturedFlag(true);
            v1.setIdDiscoverVideoCategory(1L);
            v1.setUploadedDate(new Date());
            v1.setTopic("Mathematics: Intro to Triangles");
            v1.setVideoOtp("dummy-otp-1");
            v1.setVideoDuration(600);
            v1.setPostarLoc("https://img.youtube.com/vi/wTV9PHlD-tU/0.jpg");
            v1.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
            v1.setVideoDescription("A comprehensive overview of geometric triangles and their theorems.");
            v1.setTotalViews(150L);
            videoRepository.save(v1);

            DiscoverVideo v2 = new DiscoverVideo();
            v2.setVideoLink("r6KNcnysgtY");
            v2.setLanguage("English");
            v2.setFeaturedFlag(true);
            v2.setIdDiscoverVideoCategory(1L);
            v2.setUploadedDate(new Date());
            v2.setTopic("Science: Basic Laws of Motion");
            v2.setVideoOtp("dummy-otp-2");
            v2.setVideoDuration(850);
            v2.setPostarLoc("https://img.youtube.com/vi/r6KNcnysgtY/0.jpg");
            v2.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
            v2.setVideoDescription("Understanding Newton's three laws of motion with everyday examples.");
            v2.setTotalViews(290L);
            videoRepository.save(v2);

            logger.info("Sample discover videos seeded.");
        }

        // 4. Seed Live Classes
        if (liveClassRepository.count() == 0 && teacher != null) {
            logger.info("No live classes found. Seeding sample live classes...");
            
            LiveClass lc1 = new LiveClass();
            lc1.setIdTeacher(teacher.getIdTeacher());
            lc1.setLiveClassHeading("Algebra Foundations");
            lc1.setLiveClassDesc("Learn about variables, equations, and expressions.");
            lc1.setLiveClassURL("https://www.youtube.com/embed/wTV9PHlD-tU");
            lc1.setIdLiveClassCategory(1L);
            lc1.setClassDate(LocalDate.now());
            lc1.setFromTime(LocalTime.of(10, 0));
            lc1.setToTime(LocalTime.of(11, 0));
            lc1.setIdClassStandard(2L); // Class 9
            lc1.setIdLanguage(7L); // English
            lc1.setIdSubject(1L); // Maths
            lc1.setIdSyllabus(1L);
            lc1.setIdState(3L);
            lc1.setActiveFlag(true);
            lc1.setClassType("premium");
            lc1.setLiveCompletionFlag(true);
            lc1.setThumbnailURL("https://img.youtube.com/vi/wTV9PHlD-tU/0.jpg");
            liveClassRepository.save(lc1);

            LiveClass lc2 = new LiveClass();
            lc2.setIdTeacher(teacher.getIdTeacher());
            lc2.setLiveClassHeading("Chemical Reactions & Equations");
            lc2.setLiveClassDesc("Balance equations and understand state changes.");
            lc2.setLiveClassURL("https://www.youtube.com/embed/r6KNcnysgtY");
            lc2.setIdLiveClassCategory(1L);
            lc2.setClassDate(LocalDate.now());
            lc2.setFromTime(LocalTime.of(14, 0));
            lc2.setToTime(LocalTime.of(15, 30));
            lc2.setIdClassStandard(2L); // Class 9
            lc2.setIdLanguage(7L); // English
            lc2.setIdSubject(2L); // Science
            lc2.setIdSyllabus(1L);
            lc2.setIdState(3L);
            lc2.setActiveFlag(true);
            lc2.setClassType("premium");
            lc2.setLiveCompletionFlag(true);
            lc2.setThumbnailURL("https://img.youtube.com/vi/r6KNcnysgtY/0.jpg");
            liveClassRepository.save(lc2);

            logger.info("Sample live classes seeded.");
        }
        
        logger.info("Seeding process completed successfully!");
    }
}
