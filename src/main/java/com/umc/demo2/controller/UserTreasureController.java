package com.umc.demo2.controller;


import com.umc.demo2.domain.BaseEntity;
import com.umc.demo2.domain.UserTreasure;
import com.umc.demo2.dto.TreasureReq;
import com.umc.demo2.dto.TreasureRes;
import com.umc.demo2.global.BaseResponse;
import com.umc.demo2.repository.UserTreasureRepository;
import com.umc.demo2.service.UserService;
import com.umc.demo2.service.UserTreasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserTreasureController {

    private final UserTreasureService userTreasureService;
    private final UserService userService;

    @GetMapping("/comment/{treasureId}/comments")
    public BaseResponse<TreasureRes.UserTreasureListDto> getCommentList(@PathVariable (name = "treasureId") Long treasureId){
        List<UserTreasure> userTreasureList = userTreasureService.findAllByTreasureId(treasureId);
        return new BaseResponse<>(UserTreasureConverter.toUserTreasureListDto(userTreasureList));
    }


    @PostMapping("/comment")
    public BaseResponse<String> createUserTreasure(
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "request") TreasureReq.CreateUserTreasure request
    ) throws IOException {
        UserTreasure userTreasure = userTreasureService.create(files, request);
        return new BaseResponse<>("새로운 방명록 등록 완료");
    }
  

    @PatchMapping("/comment/{userId}/{treasureId}")
    public BaseResponse<String> updateUserTreasure(@PathVariable(name = "userId")Long userId, @PathVariable(name ="treasureId")Long treasureId, @RequestBody TreasureReq.UpdateUserTreasure request) {
        UserTreasure userTreasure = userTreasureService.update(userId, treasureId, request);
        return new BaseResponse<>("방명록 수정 완료");
    }


    @GetMapping("/user/{userId}/treasures")
    public BaseResponse<List<TreasureRes.UserTreasureRes>> getUserTreasureList(@PathVariable Long userId){
        return new BaseResponse<>(userTreasureService.getUserTreasureList(userId));
    }


    @GetMapping("/user/{userId}/treasures/count")
    public BaseResponse<TreasureRes.UserSuccessTreasureCount> countUserTreasureList(@PathVariable Long userId){
        return new BaseResponse<>(userTreasureService.countUserTreasureList(userId));
    }

    @GetMapping("/treasure/rank")
    public BaseResponse<List<UserTreasureRepository.TheMostMissions>> getTheMostMissions(){
        return new BaseResponse<>(userTreasureService.getTheMostMissions());
    }

    @PatchMapping("/{userId}/{treasureId}/approval")
    public BaseResponse<String> giveCommentApproval(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "treasureId") Long treasureId){
        userTreasureService.giveCommentApproval(userId, treasureId);

        return new BaseResponse<>("관리자 승인이 완료되었습니다");
    }

}
