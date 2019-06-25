Change Log
==========

## 0.2.0 (TBD)

### Added
- News in the `cz.eman.kaal.domain.Result`:
    - We added a new state `Running` to be inform about that your remote or local call is running
    - New function `getOrNull()` which return data in case of that are available (from remote call or locall call)
    , otherwise null is returned.
    - Introducing a new function in the `Result` class called `combineCall` for sequential call to local persistence 
    and server request.
  
### Changed
- The `Result.Error` has now output a generic parameter `T`

## 0.1.0 (2019-05-29)
- Initial version