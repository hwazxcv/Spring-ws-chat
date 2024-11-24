package com.stomp.chat.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private static ResourceBundle validationsBundle;

    private static ResourceBundle errorsBundle;

    private static ResourceBundle commonsBundle;

    private final MessageSource messageSource;
    private final HttpServletRequest request;

    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        commonsBundle = ResourceBundle.getBundle("messages.commons");
    }

    public static String getMessage (String code , String bundleType){
        bundleType = Objects.requireNonNullElse(bundleType , "validation");
        ResourceBundle  bundle = null;

        if(bundleType.equals("commons")){
            bundle = commonsBundle;
        }else if (bundleType.equals("errors")){
            bundle = errorsBundle;
        }else {
            bundle = validationsBundle;
        }

        try{
            return bundle.getString(code);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getMessage(String code){
        return getMessage(code , null);
    }

    public Map<String , List<String>> getErrorMessages(Errors errors){
        Map<String , List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e ->getCodeMessages(e.getCodes()), (p1 , p2) ->p1));

        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

        if(!gMessages.isEmpty()){
            messages.put("global",gMessages);
        }
        return messages;
    }

    public List<String> getCodeMessages(String[] codes){
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try{
                        return ms.getMessage(c, null ,request.getLocale());
                    }catch (Exception e){
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .toList();
        ms.setUseCodeAsDefaultMessage(true);
        return messages;
    }
}
