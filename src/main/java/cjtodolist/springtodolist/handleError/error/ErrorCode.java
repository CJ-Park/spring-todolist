package cjtodolist.springtodolist.handleError.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ID_DUPLICATION(400, "USER-ERR-400", "ID DUPLICATED"),
    NOT_EXIST_ERROR(401, "NOT-EXIST-401", "NOT EXIST"),
    LOGIN_FAILED_ERROR(401, "LOGIN-FAILED-401", "WRONG PASSWORD"),
    NOT_FOUND_ERROR(404, "NOTFOUND-ERR-404", "PAGE NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "SERVER-ERR-500", "INTERNAL SERVER ERROR");


    private int status;
    private String errorCode;
    private String errorMsg;

}
