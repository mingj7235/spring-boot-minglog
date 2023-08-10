package com.minglog.api.config;

import com.minglog.api.config.data.UserSession;
import com.minglog.api.exception.Unauthorized;
import com.minglog.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private static final String KEY = "y1wG2au2rlAL6qRUX0sbwR7VuCuRYToE7Sv5ih3lyKI=";


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jws = webRequest.getHeader("Authorization");

        if(!StringUtils.hasText(jws)) {
            log.error("Token is null");
            throw new Unauthorized();
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(jws);

            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));

        }catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
