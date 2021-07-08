package com.stsboot.com.response;

import lombok.Data;

@Data
public class BaseResponseMessage {
	private StatusEnum status;
    private String message;
    private Object data;

    public BaseResponseMessage() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
