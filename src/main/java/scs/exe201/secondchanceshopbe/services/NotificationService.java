package scs.exe201.secondchanceshopbe.services;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.NotificationResponse;

import java.util.List;

@Service
public interface NotificationService {
    List<NotificationResponse> getAllNotification();

    NotificationResponse createNotification(NotificationCreateDTO notificationCreateDTO);

    NotificationResponse updateNotification(NotificationUpdateDTO notificationUpdateDTO);

    NotificationResponse deleteNotification(long id);
}
