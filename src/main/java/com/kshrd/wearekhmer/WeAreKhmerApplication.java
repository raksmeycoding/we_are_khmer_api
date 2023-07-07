package com.kshrd.wearekhmer;

import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.history.service.HistoryService;
import com.kshrd.wearekhmer.oneSignal.Onesignal;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.user.service.AuthorService;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.userArtivities.repository.ICommentRepository;
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.ServerUtil;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceHelperImpl;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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

    private final ArticleService articleService;

    private final ICommentRepository ICommentRepository;

    private final HistoryService iHistoryService;

    private final AuthorRepository authorRepository;

    private final ServiceClassHelper serviceClassHelper;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private final ServerUtil serverUtil;


    public static void main(String[] args) {
        SpringApplication.run(WeAreKhmerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



//
//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, """
//                {"app_id":"afc89b92-a380-4797-9c77-d9829ccb8f98","included_segments":["Subscribed Users"],"contents":{"en":"English or Any Language Message","es":"Spanish Message"},"name":"INTERNAL_CAMPAIGN_NAME"}
//                """);
//        Request request = new Request.Builder()
//                .url("https://onesignal.com/api/v1/notifications")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("Authorization", "Basic NjM0ZTAzY2YtMmIzYS00MTQxLTkwMjAtYzg0YjQzNmI5NGE5")
//                .addHeader("Content-type", "application/json; charset=utf-8")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body());
//        System.out.println(response.message());
//        System.out.println(response.cacheResponse());
//        response.close();
//        System.out.println(response);


//        String apiUrl = "https://onesignal.com/api/v1/players?app_id=" + Onesignal.builder().build().getAppId();
//        HttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpGet request = new HttpGet(apiUrl);
//        request.setHeader("Authorization", "Basic " + Onesignal.builder().build().getRestAPIKey());
//
//        try {
//            HttpResponse response = httpClient.execute(request);
//            HttpEntity entity = response.getEntity();
//            String responseBody = EntityUtils.toString(entity);
//
//            // Process the response body (user record data)
//            System.out.println(responseBody);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        System.out.println(serverUtil.getImageServerName());

    }
}
