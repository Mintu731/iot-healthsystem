package com.healthcare.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private String status;
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(
            LocalDateTime timestamp,
            String status,
            int statusCode,
            String message,
            T data
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    // getters and setters
}
