package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoProjectionDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUserFromQueryDsl(Long todoId) {
        Todo result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoProjectionDto> searchFromProjection(Pageable pageable,
                                                        String keyword,
                                                        String managerNickname,
                                                        LocalDateTime startDate,
                                                        LocalDateTime endDate) {
        BooleanBuilder builder = new BooleanBuilder();

        //검색 키워드로 일정의 제목을 검색
        //제목은 부분적으로 일치해도 검색 가능
        if(keyword != null && !keyword.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(keyword));
        }

        //담당자의 닉네임으로도 검색
        //닉네임은 부분적으로 일치해도 검색가능
        QManager manager = QManager.manager;
        if (managerNickname != null && !managerNickname.isEmpty()) {
            builder.and(
                    JPAExpressions
                            .select(manager.id)
                            .from(manager)
                            .where(manager.managerUser.nickname.containsIgnoreCase(managerNickname))
                            .exists()  // 존재 여부 확인
            );
        }

        //일정의 생성일 범위
        if(startDate != null && endDate != null) {
            builder.and(todo.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(todo.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(todo.createdAt.loe(endDate));
        }

        List<TodoProjectionDto> todos = jpaQueryFactory
                .select(
                        Projections.constructor(
                                TodoProjectionDto.class,
                                todo.id,
                                todo.title,
                                // 담당자 수
                                select(manager.managerUser.count())
                                        .from(manager)
                                        .where(manager.todo.eq(todo)),
                                // 댓글 개수
                                select(comment.count())
                                        .from(comment)
                                        .where(comment.todo.eq(todo))
                                ))
                .from(todo)
                .join(todo.managers, manager)
                .where(builder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 데이터 개수 조회 (페이징 처리를 위해)
        long total = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(todos, pageable, total);
    }
}
