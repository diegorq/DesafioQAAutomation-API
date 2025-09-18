package DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.awt.print.Book;
import java.time.OffsetDateTime;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)


public class TokenDTO {
    private String token;
    private String expires;
    private String status;
    private String result;
}
