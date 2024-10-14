package scs.exe201.secondchanceshopbe.models.dtos.response;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long notificationId;


    private String content;


    private LocalDate dateCreate;


    private String title;


    private long userId ;
}
