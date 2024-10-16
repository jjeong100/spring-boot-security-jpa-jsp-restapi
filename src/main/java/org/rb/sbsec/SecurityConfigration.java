package org.rb.sbsec;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigration {//implements WebMvcConfigurer{
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
    // 인가(접근권한) 설정
//    http.authorizeHttpRequests().antMatchers("/").permitAll();
//    http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); 
//    http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER");
//    http.authorizeHttpRequests().antMatchers("/user2/loginSuccess").hasAnyRole("3", "4", "5");
		
    // 사이트 위변조 요청 방지
    http.csrf().disable();

    // 로그인 설정
    http.formLogin()
    .loginPage("/user2/login")
    .defaultSuccessUrl("/user2/loginSuccess")
    .failureUrl("/user2/login?success=100)")
    .usernameParameter("uid")
    .passwordParameter("pass");
		
    // 로그아웃 설정
    http.logout()
    .invalidateHttpSession(true)
    .logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
    .logoutSuccessUrl("/user2/login?success=200");

    // 사용자 인증 처리 컴포넌트 서비스 등록
    http.userDetailsService(userDetailsService);

    return http.build();
}
//	이걸로 바꿔보세요. 아래 방법은 보안을 강화하기 위해 Password Encoder 빈을 스프링 컨테이너에 제공하는 것입니다.
//    이제 컨트롤러에서 다음과 같이 자동 연결을 할 수 있습니다.
//
//    @Autowired    
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Bean
    public PasswordEncoder PasswordEncoder () {
    	//return new MessageDigestPasswordEncoder("SHA-256");
    	return new BCryptPasswordEncoder();
    }
    
//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}"));
//    }
    
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//    	registry.addResourceHandler("/**")
//    	.addResourceLocations("classpath:/WEB-INF/")
//    	.setCacheControl(CacheControl.maxAge(10,TimeUnit.MINUTES));
//    }
}