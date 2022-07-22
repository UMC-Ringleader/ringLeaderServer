package umc.spring.ringleader.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static umc.spring.ringleader.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})

public class BaseResponse<T> {
    @ApiModelProperty(example = "메시지")
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @ApiModelProperty(example = "메시지")
    private final String message;

    @ApiModelProperty(example = "상태코드")
    private final int code;

    @ApiModelProperty(example = "응답데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    //요청 성공
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    //요청 실패
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
