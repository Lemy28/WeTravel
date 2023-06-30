import com.app.wetravel.User
object UserManager {
    private var user: User? = null

    var isLoggedIn: Boolean = false
        private set

    fun login() {
        val phoneNumber = user?.phoneNumber
        // 登录逻辑，如果登录成功则将isLoggedIn属性设置为true
        isLoggedIn = true
    }

    fun logout() {
        // 登出逻辑，将isLoggedIn属性设置为false
        isLoggedIn = false
    }
    fun getUser(): User? {
        return user
    }

    fun setUser(user: User) {
        this.user = user
    }

}