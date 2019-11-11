package softuni.residentevil.cofigurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // .csrf().csrfTokenRepository(csrfTokenRepository()).and()
                .authorizeRequests().antMatchers(Constants.INDEX_ACTION, Constants.LOGIN_FORM_ACTION, Constants.REGISTER_FORM_ACTION).anonymous()
                //.antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/js/**", "/css/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage(Constants.LOGIN_FORM_ACTION).permitAll().usernameParameter("username").passwordParameter("password").defaultSuccessUrl(Constants.HOME_ACTION).and().logout().logoutSuccessUrl(Constants.INDEX_ACTION).permitAll();

        //.and().exceptionHandling().accessDeniedPage("/unauthorized");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
