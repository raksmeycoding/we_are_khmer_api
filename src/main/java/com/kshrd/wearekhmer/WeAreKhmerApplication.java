package com.kshrd.wearekhmer;

import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
public class WeAreKhmerApplication implements CommandLineRunner {

    private final UserAppService userAppService;
    private final UserAppRepository userAppRepository;
    private final UserUtil userUtil;

    private final FileConfig fileConfig;


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










    }



}
