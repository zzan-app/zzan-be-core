# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**ZZAN(짠)** — 전통주 경험 공유 소셜 서비스. 사용자가 마신 전통주를 기록하고 공유하는 Spring Boot 백엔드 API.

## Commands

```bash
# 전체 빌드
./gradlew build

# 테스트 실행 (전체)
./gradlew test

# 특정 모듈 테스트
./gradlew :zzan-feed:test
./gradlew :zzan-liquor:test

# 단일 테스트 클래스 실행
./gradlew :zzan-feed:test --tests "com.zzan.feed.application.service.FeedServiceTest"

# 단일 테스트 메서드 실행
./gradlew :zzan-feed:test --tests "com.zzan.feed.application.service.FeedServiceTest.getDetail"

# 실행 가능한 JAR 빌드 (테스트 제외)
./gradlew :zzan-app:bootJar -x test

# 로컬 실행 (PostgreSQL, Redis 필요)
./gradlew :zzan-app:bootRun
```

## Architecture

헥사고날 아키텍처(Ports & Adapters)를 따르는 Kotlin/Spring Boot 멀티모듈 프로젝트.

### 모듈 구조

| 모듈 | 역할 |
|------|------|
| `zzan-core` | 공통 인프라 — Security, JWT, Exception, Response, Entity 기반 클래스, 도메인 이벤트 정의 |
| `zzan-user` | 사용자/인증 도메인 |
| `zzan-feed` | 피드(리뷰) 도메인 |
| `zzan-place` | 장소 도메인 (Kakao Map 연동, 지리 공간 쿼리) |
| `zzan-liquor` | 주류 도메인 |
| `zzan-infra` | 외부 서비스 어댑터 — Kakao Map API, AWS S3, 크로스 모듈 포트 구현 |
| `zzan-app` | 조립 레이어 — `bootJar` 생성, `application.properties`, SpringDoc OpenAPI |

의존 방향: `도메인 모듈 → zzan-core` / `zzan-infra → 도메인 모듈` / `zzan-app → 전체`

도메인 모듈 간 직접 의존은 없고, `zzan-infra`가 크로스 모듈 포트를 구현하는 브릿지 역할.

### 각 도메인 모듈 내부 구조

```
domain/                  # 순수 도메인 모델 (Spring 의존성 없음)
  vo/                    # Value Object — 생성 시점 유효성 검증
application/
  port/in/               # 유스케이스 인터페이스
  port/out/              # Repository, EventPublisher 인터페이스
  service/               # 유스케이스 구현체 (@Service)
adapter/
  in/                    # REST 컨트롤러, 스케줄러
  out/                   # JPA PersistenceAdapter, EventPublisher 구현체
    entity/              # JPA 엔티티 (도메인 모델과 분리)
    jpa/                 # Spring Data JPA Repository 인터페이스
  dto/request|response/  # API DTO
```

요청 흐름: `Controller → UseCase(port/in) → Service → Repository(port/out) → PersistenceAdapter → JpaRepository`

### 핵심 규칙

- **도메인 이벤트**: `FeedCreated`, `FeedDeleted`, `LiquorReviewCreated` 등은 `zzan-core/event/`에 정의. `ApplicationEventPublisher`로 발행하고 `@TransactionalEventListener + @Async`로 비동기 처리 (트랜잭션 커밋 후 실행).
- **Entity 기반 클래스**: `BaseEntity(id: String)` → `AuditableEntity` (createdAt, updatedAt, deletedAt). `AuditableEntity`에는 `@SQLRestriction("deleted_at IS NULL")`이 적용되어 소프트 삭제 자동 필터링.
- **ID**: ULID 사용 (`com.github.f4b6a3:ulid-creator`), DB 컬럼 length=26.
- **페이지네이션**: 커서 기반 (`CursorPageRequest` / `CursorPageResponse`). `size + 1` 개를 조회해서 다음 페이지 여부 판단.
- **응답 형식**: 모든 API는 `ApiResponse<T>` 래퍼 사용 (`ApiResponse.ok(data)` / `ApiResponse.error(message)`).
- **예외 처리**: `CustomException(HttpStatus, message)` throw → `GlobalExceptionHandler`에서 `ApiResponse.error`로 변환.
- **인증**: `@CurrentUser` 어노테이션으로 컨트롤러 파라미터에서 현재 사용자 ID 주입. JWT 기반 stateless.
- **이미지 URL**: `@ImageUrl` 어노테이션 + `ImageUrlSerializer`로 S3 base URL 자동 프리픽스 처리.

### 환경 변수

`application.properties`에서 필요한 환경 변수:
- `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`
- `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_S3_BUCKET` (기본값: `zzan-liquor-bucket`), `AWS_REGION` (기본값: `ap-northeast-2`)
- `JWT_SECRET_KEY`

## Commit Message Convention

브랜치 이름 패턴: `{type}/{issue-number}-{description}` (예: `feature/1-feed-view-count`)

```
{type}: {한국어 요약} (#{issue number})

- 상세 변경 내용 (한국어)
- 상세 변경 내용 (한국어)
```

- `type`은 소문자 영문: `feat`, `fix`, `chore`, `docs`, `refactor` 등
- 헤더 요약과 본문은 한국어, 기술 용어는 영어 유지
- 이슈 번호는 브랜치 이름에서 추출

## Testing

테스트는 MockK 기반 단위 테스트. `@ExtendWith(MockKExtension::class)` + `@MockK` / `@InjectMockKs` 사용.

```kotlin
@ExtendWith(MockKExtension::class)
class SomeServiceTest {
    @MockK private lateinit var someRepository: SomeRepository
    @InjectMockKs private lateinit var someService: SomeService
}
```

Value Object와 도메인 로직은 Spring 컨텍스트 없이 순수 단위 테스트.
