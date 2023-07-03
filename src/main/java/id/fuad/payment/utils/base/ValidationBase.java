package id.fuad.payment.utils.base;

import id.fuad.payment.config.constant.IssueConstant;
import id.fuad.payment.dto.ErrorBagDto;
import id.fuad.payment.exception.UnprocessableContentException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public record ValidationBase(ArrayList<ErrorBagDto> errorBagDtos) {
    public void addErrorField(String field, IssueConstant issue) {
        ErrorBagDto error = ErrorBagDto.builder()
                .field(field)
                .issue(issue.name())
                .build();

        this.errorBagDtos.add(error);
    }

    public void addErrorPathVariable(String pathVariable, IssueConstant issue) {
        ErrorBagDto error = ErrorBagDto.builder()
                .pathVariable(":" + pathVariable)
                .issue(issue.name())
                .build();

        this.errorBagDtos.add(error);
    }

    public void raiseError() throws UnprocessableContentException {
        if (this.errorBagDtos.size() > 0) {
            throw new UnprocessableContentException(this.errorBagDtos);
        }
    }
}
