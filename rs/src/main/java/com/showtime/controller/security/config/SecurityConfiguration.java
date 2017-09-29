package com.showtime.controller.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        Memory -
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .withUser("marissa").password("koala").roles("USER")
//                .and()
//                    .withUser("paul").password("emu").roles("USER")
//                .and()
//                    .withUser("admin").password("admin").roles("ADMIN");

//        JDBC -
        String usernameSql = "select username,password,enabled "
                + "from t_sys_user "
                + "where username=? ";

        String authoritySql = "select u.username, r.english_name "
                + "from t_sys_user as u, t_sys_role as r, t_sys_user_role as ur "
                + "where u.username=? and u.id = ur.sys_user_id and r.id = ur.sys_role_id";

        authenticationManagerBuilder.jdbcAuthentication()
                        .dataSource(dataSource)
                        .usersByUsernameQuery(usernameSql)
                        .authoritiesByUsernameQuery(authoritySql)
                        .rolePrefix("ROLE_")
                        .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        //        Memory -
//        http
//                .csrf().disable()
//                .anonymous().disable()
//                .authorizeRequests()
//                .antMatchers("/oauth/token").permitAll();

        http
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/admin/login/index.html").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/admin/login/index.html?authorization_error=true&access=denied")
                .and()
                // TODO: put CSRF protection back into this endpoint
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/**"))
                .disable()
                .logout()
                .logoutUrl("/torinosrc/logout")
                .logoutSuccessUrl("/admin/login/index.html")
                .and()
                .formLogin()
                .defaultSuccessUrl("/admin/index.html")
                .loginProcessingUrl("/torinosrc/login")
                .failureUrl("/admin/login/index.html?authentication_error=true&access=fail")
                .loginPage("/admin/login/index.html");
        // @formatter:on
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/admin/media/**", "/ueditor/**", "/api/**");
    }

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
}
