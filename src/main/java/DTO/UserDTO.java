package DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.awt.print.Book;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)


public class UserDTO {
    private String userID;
    private String username;
    private Book[] books;
}
