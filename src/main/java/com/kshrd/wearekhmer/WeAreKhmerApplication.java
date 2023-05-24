package com.kshrd.wearekhmer;

import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.service.IHistoryService;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.user.service.AuthorService;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.repository.ICommentRepository;
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@SecurityScheme(
//        name = "basicAuth",
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
//        with jwt
        in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        info = @Info(title = "Sample api", version = "v1")
//        security = @SecurityRequirement(name = "bearerAuth")
)
@Slf4j
public class WeAreKhmerApplication implements CommandLineRunner {

    private final UserAppService userAppService;
    private final UserAppRepository userAppRepository;
    private final UserUtil userUtil;

    private final FileConfig fileConfig;

    private final OtpUtil otpUtil;

    private final OtpService otpService;

    private final EducationMapper educationMapper;

    private final WorkingExperienceMapper workingExperienceMapper;

    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final AuthorService authorService;

    private final IArticleService articleService;

    private final ICommentRepository ICommentRepository;

    private final IHistoryService iHistoryService;

    public static void main(String[] args) {
        SpringApplication.run(WeAreKhmerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        UserAppDTO userAppDTO = userUtil.toUserAppDTO(userAppService.findUserByEmail("victoria@gmail.com"));
//        System.out.println(userAppDTO);

//        UserApp userApp = userAppService.findUserByEmail("victoria@gmail.com");
//        System.out.println(userApp);


//        List<String> roles = userAppRepository.getUserRolesById("bcc760e8-f85d-43c7-8b41-ecc8748b027d");
//        System.out.println(roles);

//        System.out.println(EGender.FEMALE.name());


//        Date now = new Date(System.currentTimeMillis());
//        Date now2 = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
//        System.out.println(now);
//        System.out.println(now2);


//        System.out.println(otpUtil.getGeneratedUUid());
//        System.out.println(otpUtil.getCurrentDate());
//        Thread.sleep(5000);
//        System.out.println(otpUtil.getExpiredAt());


//        System.out.println(otpService.createVerificationToken("mtoken34",  new Timestamp(System.currentTimeMillis()), "ecab1f9a-c195-4adf-9b71-7e524e8aef12"));


//        log.info("finding token token: {}", otpService.findByToken("mtoken3"));

//        log.info("remove token: {}", otpService.removeByToken("mtoken2"));


//        WorkingExperience workingExperience = WorkingExperience.builder()
//                .workingExperienceName("Apple")
//                .workingExperienceId("64561088-6da3-45a9-ab16-cfc3f32377da")
//                .userId("e5058c06-b40a-41a8-98fd-46f1c7768268")
//                .build();


//        WorkingExperience workingExperienceMapper1
//                = workingExperienceMapper.insert(workingExperience);
//        System.out.println(workingExperienceMapper1);


//        WorkingExperience workingExperience1 = workingExperienceMapper.getById("4f341aeb-b77c-47db-beb3-2e8db058181d");
//        List<WorkingExperience> workingExperienceList =
//                workingExperienceMapper.getAll();
//        System.out.println(workingExperienceList);


//        WorkingExperience workingExperience2 = workingExperienceMapper.update(workingExperience);
//        System.out.println("update: "+ workingExperience2);


//        List<WorkingExperience> workingExperienceList = workingExperienceMapper.getAll();
//        System.out.println(workingExperienceList);


        List<UserComment> userComment = ICommentRepository.getUserCommentByArticleId("8256a9af-da04-4c25-837f-3b9ccebd443a");
        System.out.println(userComment);

//        List<History> historyList = iHistoryService.getAllHistoryByCurrentUser();




    }
}
