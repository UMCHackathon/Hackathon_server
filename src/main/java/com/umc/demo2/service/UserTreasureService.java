package com.umc.demo2.service;

import com.umc.demo2.domain.Treasure;
import com.umc.demo2.domain.User;
import com.umc.demo2.domain.UserTreasure;
import com.umc.demo2.dto.TreasureReq;
import com.umc.demo2.dto.TreasureRes;
import com.umc.demo2.repository.TreasureRepository;
import com.umc.demo2.repository.UserRepository;
import com.umc.demo2.repository.UserTreasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserTreasureService {

    private final UserTreasureRepository userTreasureRepository;

    private final UserRepository userRepository;

    private final TreasureRepository treasureRepository;



    @Transactional
    public UserTreasure create(List<MultipartFile> files, TreasureReq.CreateUserTreasure request) throws IOException {

        UserTreasure userTreasure = UserTreasure.builder()
                .userId(request.getUserId())
                .treasureId(request.getTreasureId())
                .comment(request.getComment())
                .files(FileService.fileUpload(files))
                .build();

        return userTreasureRepository.save(userTreasure);
    }

    @Transactional
    public UserTreasure update(Long userId, Long treasureId, TreasureReq.UpdateUserTreasure request){

        UserTreasure userTreasure = userTreasureRepository.findByUserIdAndTreasureId(userId, treasureId);
        userTreasure.update(request.getComment());
        return userTreasure;

    }

    public List<UserTreasure> findAllByTreasureId(Long treasureId){ return userTreasureRepository.findAllByTreasureId(treasureId);}

    public List<TreasureRes.UserTreasureRes> getUserTreasureList(Long userId){
        List<TreasureRes.UserTreasureRes> result = new ArrayList<>();
        List<UserTreasure> userTreasures = userTreasureRepository.findAllByUserId(userId);

        User user = userRepository.findUserByUserId(userId);

        for(UserTreasure u : userTreasures){
            Treasure treasure = treasureRepository.findByTreasureId(u.getTreasureId());

            TreasureRes.UserTreasureRes element =
                    new TreasureRes.UserTreasureRes( treasure.getTitle(), treasure.getContent());

            result.add(element);
        }

        return result;
    }

    public TreasureRes.UserSuccessTreasureCount countUserTreasureList(Long userId){
        //List<UserTreasure> userTreasures = userTreasureRepository.findAllByUserId(userId);

        Long all = userTreasureRepository.countAllByUserId(userId);
        User user = userRepository.findUserByUserId(userId);

        TreasureRes.UserSuccessTreasureCount result = new TreasureRes.UserSuccessTreasureCount(
                user.getNickname(),
                all,
                all/4,
                all%4
        );


        return result;
    }


    public List<UserTreasureRepository.TheMostMissions> getTheMostMissions(){
        List<UserTreasureRepository.TheMostMissions> result = userTreasureRepository.getTheMostMissionList();
        return result;
    }

    public void giveCommentApproval(Long userId, Long treasureId) {
        UserTreasure userTreasure = userTreasureRepository.findUserTreasureByUserIdAndTreasureId(userId, treasureId);

        userTreasure.setStatus(1);
        userTreasureRepository.save(userTreasure);
    }


}
