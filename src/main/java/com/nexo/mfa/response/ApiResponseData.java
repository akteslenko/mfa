package com.nexo.mfa.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
