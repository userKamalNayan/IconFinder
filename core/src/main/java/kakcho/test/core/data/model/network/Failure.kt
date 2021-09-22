package kakcho.test.core.data.model.network

/**
 * Created by Kamal Nayan on 22-09-2021 at 19:50
 */

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ParsingError : Failure()
    object UnauthorizedError: Failure()
}