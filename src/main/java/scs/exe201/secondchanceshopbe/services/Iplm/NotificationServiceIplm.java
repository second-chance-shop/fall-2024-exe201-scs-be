package scs.exe201.secondchanceshopbe.services.Iplm;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.NotificationUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.NotificationResponse;
import scs.exe201.secondchanceshopbe.models.entities.NotificationEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.NotificationRepository;
import scs.exe201.secondchanceshopbe.services.NotificationService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

@Service
@RequiredArgsConstructor
public class NotificationServiceIplm implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public List<NotificationResponse> getAllNotification() {
        List<NotificationEntity> notificationEntities = notificationRepository.findAll();

        var notificationResponses = notificationEntities.stream().map(EntityToDTO::notificationEntityDTO).toList();
        return notificationResponses;
    }
    @Override
    public NotificationResponse createNotification(NotificationCreateDTO notificationCreateDTO) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setContent(notificationCreateDTO.getContent());
        notificationEntity.setTitle(notificationCreateDTO.getTitle());
        notificationEntity.setDateCreate(LocalDate.now());
        notificationRepository.save(notificationEntity);
        NotificationResponse notificationResponse = EntityToDTO.notificationEntityDTO(notificationEntity);

        return notificationResponse;
    }

    @Override
    public NotificationResponse updateNotification(NotificationUpdateDTO notificationUpdateDTO) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setContent(notificationUpdateDTO.getContent());
        notificationEntity.setTitle(notificationUpdateDTO.getTitle());
        notificationRepository.save(notificationEntity);
        NotificationResponse notificationResponse = EntityToDTO.notificationEntityDTO(notificationEntity);
        return notificationResponse;
    }

    @Override
    public NotificationResponse deleteNotification(long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Notification with id " + id + " not found")
        );
        notificationRepository.delete(notificationEntity);
        return null;
    }

}
