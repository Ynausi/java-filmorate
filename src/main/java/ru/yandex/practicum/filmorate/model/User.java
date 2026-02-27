package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class User {
    private Integer id;
    @NotBlank
    @Email(message = "Введите правильный email-адрес.")
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S+",message = "Не должен содержать пробелы")
    private String login;
    private String name;
    @PastOrPresent(message = "Неверно введена дата")
    private LocalDate birthday;
}
