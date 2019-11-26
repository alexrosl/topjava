package ru.javawebinar.topjava.util.formatters;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalTimeFormatResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(LocalTimeFormat.class)!=null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LocalTimeFormat attr = methodParameter.getParameterAnnotation(LocalTimeFormat.class);
        return DateTimeUtil.parseLocalTime(nativeWebRequest.getParameter(attr.value()));
    }
}
