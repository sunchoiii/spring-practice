package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoProjectionDto {
    //* 다음의 내용을 포함해서 검색 결과를 반환해주세요.
    //    * 일정에 대한 모든 정보가 아닌, 제목만 넣어주세요.
    //    * 해당 일정의 담당자 수를 넣어주세요.
    //    * 해당 일정의 총 댓글 개수를 넣어주세요.

    //제목, 담당자 수, 댓글 수
    private Long id;
    private String title;
    private Long managerCount; // 담당자 수
    private Long commentCount; // 댓글 개수

    public TodoProjectionDto(Long id, String title, Long managerCount, Long commentCount) {
        this.id = id;
        this.title = title;
        this.managerCount = managerCount;
        this.commentCount = commentCount;
    }
}
