package kakcho.test.core.data.model.network

/**
 * Created by Kamal Nayan on 22-09-2021 at 19:42
 */

sealed class Result<out SomeType, out SomeFailure> {
    class Success<out SomeType>(val data: SomeType) : Result<SomeType, Nothing>()
    class Error<out SomeFailure>(val failure: SomeFailure, val message: ErrorResponse?) :
        Result<Nothing, SomeFailure>()

    fun successOrError(
        functionType: (SomeType) -> Any,
        functionFailure: (SomeFailure, ErrorResponse?) -> Any
    ): Any =
        when (this) {
            is Success -> functionType(data)
            is Error -> functionFailure(failure, message)
        }
}