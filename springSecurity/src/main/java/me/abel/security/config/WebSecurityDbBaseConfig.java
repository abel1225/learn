package me.abel.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description
 * @Author Abel.li
 * @Date 2021/10/25 下午18:35
 */
@Configuration
public class WebSecurityDbBaseConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity hs) throws Exception {
        hs.formLogin()
                .loginPage("index.html")
                .loginProcessingUrl("login")
                .defaultSuccessUrl("index").permitAll()
                .and().authorizeRequests()
                .antMatchers("/", "/access").permitAll()
                .antMatchers("/auth").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().csrf().disable(); //关闭CSRF防护
    }
}