package me.abel.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 * @Description
 * @Author Abel.li
 * @Date 2021/10/25 下午18:35
 */
@Configuration
public class WebSecurityDbBaseConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // create default
        return new BCryptPasswordEncoder(); // default
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setJdbcTemplate(jdbcTemplate);
        repository.setCreateTableOnStartup(true);
        return repository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity hs) throws Exception {
        hs.exceptionHandling().accessDeniedPage("/noauth");
        hs.logout()  //开启注销登陆
            .logoutUrl("/logout")  //注销登陆请求url
            .logoutSuccessUrl("/login")
                .permitAll()
            .clearAuthentication(true)  //清除身份信息
            .invalidateHttpSession(true);  //session失效
        hs.formLogin()
                .loginPage("index.html")
                .loginProcessingUrl("login")
                .defaultSuccessUrl("index").permitAll()
                .and().authorizeRequests().antMatchers("/", "/access").permitAll().antMatchers("/auth").hasAuthority("admin").anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository()).rememberMeParameter("remember").tokenValiditySeconds(10).userDetailsService(userDetailsService)
                .and().csrf().disable(); //关闭CSRF防护
    }
}