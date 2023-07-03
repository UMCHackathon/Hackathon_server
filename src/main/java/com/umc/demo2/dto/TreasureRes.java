package com.umc.demo2.dto;

import lombok.*;

public class TreasureRes {


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateUserTreasure{
        private Long id;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateUserTreasure{

        private Long id;

    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserTreasureRes{
        //private String nickname;
        private String treasureTitle;
        private String comment;
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserSuccessTreasureCount{
        private String nickname;
        private Long totalTreasure;
        private Long totalPlant;
        private Long leftTreasure;
    }

}
