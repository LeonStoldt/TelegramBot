package de.stoldt.Telegrambot.errorHandling;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
class ErrorJSON {

    private Integer status;
    private String error;
    private String timestamp;
    private String trace;

    ErrorJSON(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.timestamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }
}