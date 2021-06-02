package in.francl.urbandollop.domain.datatransfer.exception;

import com.fasterxml.jackson.annotation.JsonProperty;


public record RestResponseException(
        @JsonProperty("status_code") int statusCode,
        String message
) {
}
