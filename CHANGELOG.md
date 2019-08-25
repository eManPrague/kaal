Change Log
==========

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