package bg.softuni.mobilelele.config;

import bg.softuni.mobilelele.web.interceptor.StatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;
    private final StatsInterceptor statsInterceptor;

    public WebConfig(LocaleChangeInterceptor localeChangeInterceptor, StatsInterceptor statsInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
        this.statsInterceptor = statsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(statsInterceptor);
    }
}
