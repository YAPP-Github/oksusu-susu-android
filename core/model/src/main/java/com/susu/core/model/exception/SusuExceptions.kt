package com.susu.core.model.exception

enum class SusuServerError(val exception: Exception) {
    /** Common Error Code */
    BAD_REQUEST_ERROR(BadRequestException()),
    INVALID_INPUT_VALUE_ERROR(InvalidInputValueException()),
    INVALID_TYPE_VALUE_ERROR(InvalidTypeValueException()),
    METHOD_NOT_ALLOWED_ERROR(MethodNotAllowedException()),
    INVALID_MEDIA_TYPE_ERROR(InvalidMediaTypeException()),
    QUERY_DSL_NOT_EXISTS_ERROR(QueryDslNotExistsException()),
    COROUTINE_CANCELLATION_ERROR(CoroutineCancellationException()),
    NO_AUTHORITY_ERROR(NoAuthorityException()),

    /** Auth Error Code */
    FAIL_TO_VERIFY_TOKEN_ERROR(FailToVerifyTokenException()),
    NOT_ACCESS_TOKEN(NotAccessTokenException()),
    NOT_REFRESH_TOKEN(NotRefreshTokenException()),

    /** User Error Code */
    USER_NOT_FOUND_ERROR(UserNotFoundException()),
    ALREADY_REGISTERED_USER(AlreadyRegisteredUserException()),
    FAIL_TO_CREATE_USER_ERROR(FailToCreateUserException()),

    /** Ledger Error Code */
    LEDGER_INVALID_DUE_DATE_ERROR(LedgerInvalidDueDateException()),
    FAIL_TO_CREATE_LEDGER_ERROR(FailToCreateLedgerException()),
    NOT_FOUND_LEDGER_ERROR(NotFoundLedgerException()),

    /** Category Error Code */
    NOT_FOUND_CATEGORY_ERROR(NotFoundCategoryException()),

    /** Category Assignment Error Code */
    NOT_FOUND_CATEGORY_ASSIGNMENT_ERROR_CODE(NotFoundCategoryAssignmentException()),

    /** Relationship Error Code */
    NOT_FOUND_RELATIONSHIP_ERROR(NotFoundRelationshipException()),

    /** Friend Error Code */
    NOT_FOUND_FRIEND_ERROR(NotFoundFriendException()),
    ALREADY_REGISTERED_FRIEND_PHONE_NUMBER_ERROR(AlreadyRegisteredFriendPhoneNumberException()),
    FAIL_TO_CREATE_FRIEND_ERROR(FailToCreateFriendException()),

    /** Envelope Error Code */
    FAIL_TO_CREATE_ENVELOPE_ERROR(FailToCreateEnvelopeException()),
    NOT_FOUND_ENVELOPE_ERROR(NotFoundEnvelopeException()),

    /** Post Error Code */
    NOT_FOUND_POST_ERROR(NotFoundPostException()),
    INVALID_VOTE_OPTION_SEQUENCE(InvalidVoteOptionSequenceException()),
    FAIL_TO_CREATE_POST_ERROR(FailToCreatePostException()),
    NOT_FOUND_VOTE_ERROR(NotFoundVoteException()),
    DUPLICATED_VOTE_ERROR(DuplicatedVoteException()),

    /** Post Category Error Code */
    NOT_FOUND_POST_CATEGORY_ERROR(NotFoundPostCategoryException()),

    /** Term Error Code */
    NOT_FOUND_TERM_ERROR(NotFoundTermException()),

    /** Vote Error Code */
    ALREADY_VOTED_POST(AlreadyVotedPostException()),
    CANNOT_BLOCK_MYSELF(CannotBlockMyself()),
}

/** Common Exception Code */
class BadRequestException(
    override val message: String = "bad request",
) : RuntimeException()

class InvalidInputValueException(
    override val message: String = "input is invalid value",
) : RuntimeException()

class InvalidTypeValueException(
    override val message: String = "invalid type value",
) : RuntimeException()

class MethodNotAllowedException(
    override val message: String = "Method type is invalid",
) : RuntimeException()

class InvalidMediaTypeException(
    override val message: String = "invalid media type",
) : RuntimeException()

class QueryDslNotExistsException(
    override val message: String = "not found query dsl",
) : RuntimeException()

class CoroutineCancellationException(
    override val message: String = "coroutine cancellation Exception",
) : RuntimeException()

class NoAuthorityException(
    override val message: String = "수정 권한이 없습니다.",
) : RuntimeException()

/** Auth Exception Code */
class FailToVerifyTokenException(
    override val message: String = "fail to verify token",
) : RuntimeException()

class NotAccessTokenException(
    override val message: String = "엑세스 토큰이 아닙니다.",
) : RuntimeException()

class NotRefreshTokenException(
    override val message: String = "리프레시 토큰이 아닙니다.",
) : RuntimeException()

/** User Exception Code */
class UserNotFoundException(
    override val message: String = "유저 정보를 찾을 수 없습니다.",
) : RuntimeException()

class AlreadyRegisteredUserException(
    override val message: String = "이미 가입된 유저입니다.",
) : RuntimeException()

class FailToCreateUserException(
    override val message: String = "유저 생성을 실패했습니다.",
) : RuntimeException()

/** Ledger Exception Code */
class LedgerInvalidDueDateException(
    override val message: String = "잘못된 일정 등록 요청입니다.",
) : RuntimeException()

class FailToCreateLedgerException(
    override val message: String = "장부 생성을 실패했습니다.",
) : RuntimeException()

class NotFoundLedgerException(
    override val message: String = "장부 정보가 없습니다.",
) : RuntimeException()

/** Category Exception Code */
class NotFoundCategoryException(
    override val message: String = "카테고리 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Category Assignment Exception Code */
class NotFoundCategoryAssignmentException(
    override val message: String = "카테고리 매핑 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Relationship Exception Code */
class NotFoundRelationshipException(
    override val message: String = "관계 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Friend Exception Code */
class NotFoundFriendException(
    override val message: String = "친구 정보를 찾을 수 없습니다.",
) : RuntimeException()

class AlreadyRegisteredFriendPhoneNumberException(
    override val message: String = "이미 등록된 전화번호 입니다.",
) : RuntimeException()

class FailToCreateFriendException(
    override val message: String = "친구 생성을 실패했습니다.",
) : RuntimeException()

/** Envelope Exception Code */
class FailToCreateEnvelopeException(
    override val message: String = "봉투 생성을 실패했습니다.",
) : RuntimeException()

class NotFoundEnvelopeException(
    override val message: String = "봉투 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Post Exception Code */
class NotFoundPostException(
    override val message: String = "게시글 정보를 찾을 수 없습니다.",
) : RuntimeException()

class InvalidVoteOptionSequenceException(
    override val message: String = "투표 옵션 순서가 잘못되었습니다.",
) : RuntimeException()

class FailToCreatePostException(
    override val message: String = "게시글 생성을 실패했습니다.",
) : RuntimeException()

class NotFoundVoteException(
    override val message: String = "투표 정보를 찾을 수 없습니다.",
) : RuntimeException()

class DuplicatedVoteException(
    override val message: String = "중복 투표를 할 수 없습니다.",
) : RuntimeException()

/** Post Category Exception Code */
class NotFoundPostCategoryException(
    override val message: String = "카테고리 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Term Exception Code */
class NotFoundTermException(
    override val message: String = "약관 정보를 찾을 수 없습니다.",
) : RuntimeException()

/** Vote Exception Code */
class AlreadyVotedPostException(
    override val message: String = "이미 진행된 투표입니다.",
) : RuntimeException()

class CannotBlockMyself(
    override val message: String = "본인을 차단할 수 없습니다.",
) : RuntimeException()
