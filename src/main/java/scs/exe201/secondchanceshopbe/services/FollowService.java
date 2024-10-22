package scs.exe201.secondchanceshopbe.services;

import scs.exe201.secondchanceshopbe.models.dtos.response.FollowResponse;

import java.util.List;

public interface FollowService {
    FollowResponse actionFollow(long id);

    List<FollowResponse> getAllByShopId(long id);
}
