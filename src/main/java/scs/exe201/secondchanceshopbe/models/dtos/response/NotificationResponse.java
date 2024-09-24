package scs.exe201.secondchanceshopbe.models.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long notificationId;


    private String content;


    private LocalDate dateCreate;


    private String title;


    private long userId ;
}
