package com.chs.myhome.validator;

import com.chs.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
//스프링에서 지원하는 클래스 상속
public class BoardValidator implements Validator {
    @Override
    //어떤 값을 validate할 것인지 Board클래스 선정
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    //실제 값이 유효한지 체크하는 로직
    public void validate(Object obj, Errors errors) {
        //지원할 클래스 지정 형변환
        Board b = (Board) obj;
        //컨텐츠값이 비어있는지 확인
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content","key","내용을 입력해주세요");
        }
    }
}
