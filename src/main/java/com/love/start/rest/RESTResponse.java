package com.love.start.rest;

import java.util.ArrayList;
import java.util.HashMap;

public class RESTResponse {
    private HashMap<String, Object> response;

    private RESTResponse() {
        this.response = new HashMap<>();
    }

    public HashMap<String, Object> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String, Object> response) {
        this.response = response;
    }

    public void addResponse(String key, Object value) {
        this.response.put(key, value);
    }

    public static class Success {
        private int status;
        private String message;
        private Object data;

        public Success() {
            this.status = 1;
            this.message = "Success";
        }

        public Success setStatus(int status) {
            this.status = status;
            return this;
        }

        public Success setMessage(String message) {
            this.message = message;
            return this;
        }

        public Success setData(Object obj) {
            this.data = obj;
            return this;
        }

        public Success setData(Iterable listObj) {
            this.data = new ArrayList<>();
            this.data = listObj;
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("data", this.data);
            return restResponse.getResponse();
        }
    }
}

