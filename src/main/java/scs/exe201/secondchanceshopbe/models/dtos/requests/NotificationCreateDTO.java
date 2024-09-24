package scs.exe201.secondchanceshopbe.models.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreateDTO {

    private String content;

    private String title;

}
