package ru.javawebinar.topjava.util.formatters;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalDateFormatResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(LocalDateFormat.class) !=null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LocalDateFormat attr = methodParameter.getParameterAnnotation(LocalDateFormat.class);
        return DateTimeUtil.parseLocalDate(nativeWebRequest.getParameter(attr.value()));
    }
}
