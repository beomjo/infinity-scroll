
package k.bs.infinityscroll.util.error

class CanceledByUserException(message: String ="사용자취소") : RuntimeException(message) {

    constructor(throwable: Throwable) : this(throwable.message!!)
}