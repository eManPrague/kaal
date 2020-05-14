Change Log
==========

## 0.7.0 (2020-05-14)

### Added
- `map` functions call parameter could be now a suspend. This allow to us call suspendable functions inside of mappers.

### Changed
- `Api Break:` [#13](https://github.com/eManPrague/kaal/issues/13) - `Base` prefix has been replaced by a new `Kaal` prefix (`BaseFragment` -> `KaalFragment`, ...)
- Kotlin Coroutines updated to [v1.3.5](https://github.com/Kotlin/kotlinx.coroutines/releases/tag/1.3.5)
- Kotlin updated to [v1.3.72](https://github.com/JetBrains/kotlin/releases/tag/v1.3.72)
- Gradle updated to [v6.2.2](https://docs.gradle.org/6.2.2/release-notes.html)

## 0.6.0 (2020-01-27)

### Changed
- Kotlin Coroutines updated to [v1.3.3](https://github.com/Kotlin/kotlinx.coroutines/releases/tag/1.3.3)
- Retrofit updated to [v2.7.1](https://github.com/square/retrofit/blob/master/CHANGELOG.md)
- Gradle updated to [v6.1.1](https://docs.gradle.org/6.1.1/release-notes.html)

### Added
- :sparkles: The `SingleLiveEvent` (A lifecycle-aware observable that sends only new updates after subscription, used for events like
navigation and Snackbar messages.) **is back** :)
- New extension function `ViewGroup.inflate` to allows call like: `viewGroup.inflate(R.layout.exchange_rates_view)`
- Gradle updated to [v6.1.1](https://docs.gradle.org/6.1.1/release-notes.html)

### Added
- :sparkles: The `SingleLiveEvent` (A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.) **is back** :)
- New extension function `ViewGroup.inflate` to allows call like: `viewGroup.inflate(R.layout.exchange_rates_view)`

### Removed
- The `Espresso` has been removed from Kaal (not used)

## 0.5.0 (2019-12-03)

### Added
- :sparkles: [#5](https://github.com/eManPrague/kaal/issues/5): You can define Fragment or Activity `layout id` by using a constructor
    ```kotlin
    class MainActivity : BaseActivity(R.layout.activity_main)

     // Fragment
    class MyFragment: BaseFragment(R.layout.fragment_my)
    ```
### Changed
- Koin updated to v1.3.61
- Koin updated to v2.0.1
- Gradle 5.6.4

### Removed
- `API Break:` The `ScopeAware` has been removed. Since Koin 2.0.1 you should use official Scope API by Koin.

## 0.4.0 (2019-10-07)

### Changed
- `Api Break:` `cz.eman.kaal.domain.Result` and subclasses has been moved into `cz.eman.kaal.domain.result.Result`.
This a new `Result` has a new functionality to remove boilerplate code. Also all classes which are using `Result` has been changed
- Dependencies update:
    - Kotlin 1.3.50
    - Kotlin Coroutines 1.3.2
    - Espresso 3.0.2

### Added
- Added a new function `fun <Dto, T> callResult(responseCall: suspend () -> Response<Dto>, errorMessage: String? = null, map: (Dto) -> T): Result<T>`
to remove a boilerplate code when you're calling a server by using a retrofit.

Old usage with `callSafe`
```kotlin
override suspend fun getPopularMovies() = callSafe(
            call = { fetchPopularMovies() },
            errorMessage = "Cannot fetch popular movies"
    )
```
and old way to call remote API
```kotlin
private suspend fun fetchPopularMovies(): Result<List<Movie>> {
        val response = movieApiService.getPopularMovies().awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(data = MoviesMapper.mapWrapperToMovie(body))
            } else {
                Result.Error(
                        ApiErrorResult(
                                code = response.code(),
                                errorMessage = response.message(),
                                apiThrowable = EmptyBodyException()
                        )
                )
            }
        } else {
            val errorResult =
                    ApiErrorResult(code = response.code(), errorMessage = response.message())

            logError { "Cannot fetch popular movies[$errorResult]" }
            Result.Error(errorResult)
        }
}
```
By using `callResult` you will remove boilerplate code above
```kotlin
override suspend fun getPopularMovies() = callResult(
        responseCall = { movieApiService.getPopularMovies()}
    ) {
        MoviesMapper.mapWrapperToMovie(it)
    }
```
You can also define your custom error message by a defining an attribute `errorMessage`:
```kotlin
override suspend fun getPopularMovies() = callResult(
        responseCall = { movieApiService.getPopularMovies()},
        errorMessage = "Cannot get a popular movies!"
    ) {
        MoviesMapper.mapWrapperToMovie(it)
    }
```
- A new way how to create the `Result.Error`. There is a couple of standard functions but if you want to create `ErrorResult` without
that you will define your own `MyCustomErrorResult` you can use this approach
```kotlin
Result.error(
    errorCode = MovieErrorCode.INVALID_USER_CREDENTIALS,
    message = "Invalid username or password"
)
```
which is similar to code below
```kotlin
Result.Error(
     ErrorResult(
        code = MovieErrorCode.INVALID_USER_CREDENTIALS,
        message = "Invalid username or password"
     )
)
```
- New `HttpStatusErrorCode` which define all HTTP error codes. You can use it by your own. In case of that you are using
`callSafe` or `callResult` - response will be parsed automatically for you with these error codes
## 0.3.0 (2019-09-13)

### Added
- Added view extension functions (`hide(), show(), invisible(), hideKeyboard()`
- Added a new view ext. function `TextView.textWatcher`. Byt this use can avoid to using a boilerplate
code when you need to be notified if text has been changed or before change action and so on
- Added `CalendarExtensions` functions to avoid boilerplate code when you are working with the `Calendar`
- `core`: Added `exhaustive` helper to force a when statement to assert all options are matched in a when statement.
```kotlin
 when(sealedObject) {
     is OneType -> //
     is AnotherType -> //
 }.exhaustive
```

## 0.2.0 (2019-08-25)

### Added
- News in the `cz.eman.kaal.domain.Result`:
    - New function `getOrNull()` which return data in case of that are available (from remote call or local call)
    , otherwise null is returned.
    and server request.
- Introducing a Fragment's Arguments:
    - You can use this feature to easily create and handle a fragments arguments
    ```kotlin
    class PropertyDelegateFragment : Fragment() {

    private var userName by argument<String>()
    private var email by argument<String>()
    private var booksCount by argument<Int>()

    companion object {

        fun create(userName: String, email: String, booksCount: Int = 0) = PropertyDelegateFragment().apply {
            this.userName = userName
            this.email = email
            this.booksCount = booksCount
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_arguments_demo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nameTextView.text = userName
        emailTextView.text = email
        booksCountTextView.text = booksCount.toString()

        addBookButton.setOnClickListener {
            booksCount++
            booksCountTextView.text = booksCount.toString()
        }
    }
}
    ```


### Changed
- The `Result.Error` has now output a generic parameter `T`
- Kotlin `1.3.40`

### Fixed
- Coroutines scope in a base classes

## 0.1.0 (2019-05-29)
- Initial version
