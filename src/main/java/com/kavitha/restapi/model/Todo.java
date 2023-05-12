package com.kavitha.restapi.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;
    @NotNull(message = "name cannot be null")
    @Size(min = 4, max = 35, message = "enter the valid name")
    private String name;
    @NotNull(message = "email cannot be null")
    @Email(message = "invalid email format")
    private String email;
    @NotNull(message = "todo cannot be null")
    private String todo;
    @NotNull(message = "completed cannot be null")
    private boolean completed;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


}
