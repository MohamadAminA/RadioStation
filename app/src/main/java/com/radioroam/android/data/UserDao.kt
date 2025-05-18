package com.radioroam.android.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.radioroam.android.data.model.station.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}
