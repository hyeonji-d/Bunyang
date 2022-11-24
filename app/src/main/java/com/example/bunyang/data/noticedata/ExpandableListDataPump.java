package com.example.bunyang.data.noticedata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> notice1 = new ArrayList<String>();
        notice1.add("2022년 12월 01일\n강아지 분양 어플이 출시되었습니다!\n많은 관심 부탁드립니다 :)");

        List<String> notice2 = new ArrayList<String>();
        notice2.add("2022년 11월 19일\n오류 문제로 잠시 수정하겠습니다!\n불편을 드려 죄송합니다.");


        List<String> notice3 = new ArrayList<String>();
        notice3.add("2022년 11월 14일\n안녕하세요~ 관리자입니다.\n문의는 현재 게시글에 적힌 이메일로 부탁드립니다.\n오늘 날이 많이 춥습니다. 다들 따뜻하게 입으세요!\n관리자 이메일 : hyeonji14kim@gmail.com");

        expandableListDetail.put("오늘의 공지사항!", notice1);
        expandableListDetail.put("오늘의 공지사항!!", notice2);
        expandableListDetail.put("오늘의 공지사항!!!", notice3);



        return expandableListDetail;
    }
}
