package dev.lantt.itindr.core.constants

object Constants {
    const val BASE_URL = "http://itindr.mcenter.pro:8092/api/mobile/"

    const val ARG_PROFILE = "arg_profile"
    const val ARG_PROFILE_ID = "arg_profile_id"
    const val PERSON_REQUEST_KEY = "person_request_key"

    const val CONNECT_TIMEOUT_SEC = 20L
    const val WRITE_TIMEOUT_SEC = 20L
    const val READ_TIMEOUT_SEC = 20L

    const val NO_CONTENT_CODE = 204
    const val SUCCESS_CODE = 200
    const val UNAUTHORIZED_CODE = 401

    const val PEOPLE_PAGE_LIMIT = 20
    const val INITIAL_PAGE_SIZE = 80

    const val MESSAGES_PAGE_LIMIT = 20
}